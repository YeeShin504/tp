package cms.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cms.model.Model;
import cms.model.ModelManager;
import cms.storage.JsonAddressBookStorage;
import cms.storage.JsonUserPrefsStorage;
import cms.storage.StorageManager;

public class ExportCommandTest {

    @TempDir
    public Path temporaryFolder;

    @Test
    public void execute_validPath_success() throws Exception {
        Path path = temporaryFolder.resolve("data").resolve("export.json");
        ExportCommand exportCommand = new ExportCommand(path);

        Model model = new ModelManager();
        StorageManager storage = new StorageManager(
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json")),
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json")));

        CommandResult result = exportCommand.execute(model, storage);
        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, path), result.getFeedbackToUser());
        assertTrue(Files.exists(path));
    }
}
