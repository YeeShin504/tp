package cms.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cms.logic.commands.ImportCommand.KeepPolicy;
import cms.model.Model;
import cms.model.ModelManager;
import cms.storage.JsonAddressBookStorage;
import cms.storage.JsonUserPrefsStorage;
import cms.storage.StorageManager;

public class ImportCommandTest {

    @TempDir
    public Path temporaryFolder;

    @Test
    public void execute_validPath_success() throws Exception {
        Path path = temporaryFolder.resolve("data").resolve("import.json");
        ImportCommand importCommand = new ImportCommand(path);

        Model sourceModel = new ModelManager();
        StorageManager storage = new StorageManager(
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json")),
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json")));
        storage.saveAddressBook(sourceModel.getAddressBook(), path);

        Model model = new ModelManager();
        CommandResult result = importCommand.execute(model, storage);

        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, path), result.getFeedbackToUser());
        assertEquals(sourceModel.getAddressBook(), model.getAddressBook());
    }

    @Test
    public void equals() {
        ImportCommand importFirstCommand = new ImportCommand(Path.of("data/first.json"),
                KeepPolicy.CURRENT);
        ImportCommand importFirstCommandCopy = new ImportCommand(Path.of("data/first.json"),
                KeepPolicy.CURRENT);
        ImportCommand importSecondCommand = new ImportCommand(Path.of("data/second.json"),
                KeepPolicy.CURRENT);
        ImportCommand importDifferentMode = new ImportCommand(Path.of("data/first.json"),
                KeepPolicy.INCOMING);

        assertTrue(importFirstCommand.equals(importFirstCommand));
        assertTrue(importFirstCommand.equals(importFirstCommandCopy));
        assertFalse(importFirstCommand.equals(importSecondCommand));
        assertFalse(importFirstCommand.equals(importDifferentMode));
        assertFalse(importFirstCommand.equals(1));
        assertFalse(importFirstCommand.equals(null));
    }
}
