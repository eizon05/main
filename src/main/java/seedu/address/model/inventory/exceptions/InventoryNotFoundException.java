package seedu.address.model.inventory.exceptions;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Exception thrown when a trip is not found in an operation.
 */
public class InventoryNotFoundException extends CommandException {

    public InventoryNotFoundException(String message) {
        super(message);
    }
}
