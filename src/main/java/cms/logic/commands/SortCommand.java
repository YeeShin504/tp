package cms.logic.commands;

import static java.util.Objects.requireNonNull;

import cms.model.Model;

/**
 * Sorts all persons in the address book by tutorial group in ascending order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by tutorial group";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortPersonsByTutorialGroup();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
