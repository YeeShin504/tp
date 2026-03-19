package cms.model.person.exceptions;

import cms.model.person.FieldConflict;

/**
 * Signals that the operation will result in two persons sharing a field that
 * must be globally unique.
 */
public class DuplicatePersonFieldException extends RuntimeException {

    /**
     * Constructs a DuplicatePersonFieldException for the given field conflict.
     * @param conflict the field conflict causing the exception
     */
    public DuplicatePersonFieldException(FieldConflict conflict) {
        super(String.format("A person with the same [%s] already exists in the system.\n%1$s: %2$s",
                conflict.getFieldName(), conflict.getPerson()));
    }
}
