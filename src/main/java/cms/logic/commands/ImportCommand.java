package cms.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;

import cms.model.Model;

/**
 * Imports address book data from a user-specified JSON file path into the application.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports address book data from a JSON file.\n"
            + "Parameters: FILE_PATH (must be a .json file) [keep/POLICY]\n"
            + "When current data is non-empty, keep/ is required.\n"
            + "POLICY: current | incoming\n"
            + "Examples: " + COMMAND_WORD + " data/addressbook.json\n"
            + "          " + COMMAND_WORD + " data/addressbook.json keep/incoming\n"
            + "          " + COMMAND_WORD + " data/addressbook.json keep/current";

    public static final String MESSAGE_SUCCESS = "Address book imported from: %s";
    public static final String MESSAGE_KEEP_CURRENT_SUCCESS = "Current data kept; incoming data ignored from: %s";

    /** Resolution policy when importing into a non-empty address book. */
    public enum KeepPolicy {
        CURRENT,
        INCOMING
    }

    private final Path importFilePath;
    private final KeepPolicy keepPolicy;

    /**
     * Creates an ImportCommand to import address book data from the specified file path.
     *
     * @param importFilePath the path to the JSON file to import from
     */
    public ImportCommand(Path importFilePath) {
        this(importFilePath, null);
    }

    /**
     * Creates an ImportCommand with optional mode settings.
     */
    public ImportCommand(Path importFilePath, KeepPolicy keepPolicy) {
        requireNonNull(importFilePath);
        this.importFilePath = importFilePath;
        this.keepPolicy = keepPolicy;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, importFilePath));
    }

    public Path getImportFilePath() {
        return importFilePath;
    }

    public KeepPolicy getKeepPolicy() {
        return keepPolicy;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherImportCommand = (ImportCommand) other;
        return importFilePath.equals(otherImportCommand.importFilePath)
                && java.util.Objects.equals(keepPolicy, otherImportCommand.keepPolicy);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(importFilePath, keepPolicy);
    }
}
