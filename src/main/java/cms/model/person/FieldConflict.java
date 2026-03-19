package cms.model.person;

/**
 * Represents a field conflict between two persons.
 */
public class FieldConflict {
    private final String fieldName;
    private final Person person;

    /**
     * Constructs a FieldConflict with the specified field name and value.
     * @param otherPerson the person with the conflicting field
     * @param fieldName   the name of the conflicting field (e.g. "email", "SOC
     *                    username", "GitHub username")
     */
    public FieldConflict(Person otherPerson, String fieldName) {
        this.fieldName = fieldName;
        this.person = otherPerson;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Person getPerson() {
        return person;
    }
}
