package cms.logic.commands;

import static cms.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import cms.logic.Messages;
import cms.model.Model;
import cms.model.ModelManager;
import cms.model.UserPrefs;
import cms.testutil.TypicalPersons;

public class UnmaskCommandTest {

    @Test
    public void execute_setsMaskingDisabled() {
        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        model.setMasked(true);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setMasked(false);

        assertCommandSuccess(new UnmaskCommand(), model, UnmaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_unmaskWithIgnoredArgs_showsWarning() {
        String ignoredArgs = "username";
        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
        model.setMasked(true);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setMasked(false);
        String expectedMessage = UnmaskCommand.MESSAGE_SUCCESS + "\n"
                + String.format(Messages.MESSAGE_IGNORED_PARAMETERS, ignoredArgs);

        assertCommandSuccess(new UnmaskCommand(ignoredArgs), model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        UnmaskCommand unmaskCommand = new UnmaskCommand();
        UnmaskCommand unmaskCommandWithArgs = new UnmaskCommand("username");
        UnmaskCommand unmaskCommandWithSameArgs = new UnmaskCommand("username");
        UnmaskCommand unmaskCommandWithDifferentArgs = new UnmaskCommand("all");

        // same object -> returns true
        assertEquals(unmaskCommand, unmaskCommand);

        // same values -> returns true
        assertEquals(unmaskCommand, new UnmaskCommand());
        assertEquals(unmaskCommandWithArgs, unmaskCommandWithSameArgs);

        // different types -> returns false
        assertNotEquals(unmaskCommand, 1);

        // null -> returns false
        assertNotEquals(unmaskCommand, null);

        // different ignoredArgs -> returns false
        assertNotEquals(unmaskCommand, unmaskCommandWithArgs);
        assertNotEquals(unmaskCommandWithArgs, unmaskCommandWithDifferentArgs);
    }
}
