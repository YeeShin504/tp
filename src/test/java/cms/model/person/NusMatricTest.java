package cms.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NusMatricTest {
    @Test
    public void canonicalisation_trimsAndUppercases() {
        // trims and uppercases
        NusMatric n = new NusMatric("  a0234567x  ");
        assertEquals("A0234567X", n.value);
    }

    @Test
    public void canonicalisation_of_null() {
        assertEquals(null, NusMatric.canonicalise(null));
    }

    @Test
    public void isValidNusMatric_canonicalInput() {
        // null input
        assertFalse(NusMatric.isValidNusMatric(null));

        // invalid canonical forms
        assertFalse(NusMatric.isValidNusMatric("A0234567")); // missing last letter
        assertFalse(NusMatric.isValidNusMatric("A0234567BB")); // extra trailing letter
        assertFalse(NusMatric.isValidNusMatric("B0234567B")); // invalid prefix
        assertFalse(NusMatric.isValidNusMatric("A0234567$")); // invalid trailing character
        assertFalse(NusMatric.isValidNusMatric("A0234567B")); // wrong checksum
        assertFalse(NusMatric.isValidNusMatric("U0234567B")); // legacy U format should have 6 digits only
        assertFalse(NusMatric.isValidNusMatric("U0906931")); // raw legacy U7 input is not accepted
        assertFalse(NusMatric.isValidNusMatric("U023456X")); // wrong checksum
        assertFalse(NusMatric.isValidNusMatric("U012345A")); // wrong checksum for canonical U-prefix form

        // valid canonical forms
        assertTrue(NusMatric.isValidNusMatric("A0234567X")); // standard A-prefix form with valid checksum
        assertTrue(NusMatric.isValidNusMatric("U023456W")); // canonical U-prefix form with valid checksum
    }

    @Test
    public void isValidNusMatric_nonCanonicalInput() {
        assertTrue(NusMatric.isValidNusMatric("  a0234567x  "));
    }

    @Test
    public void constructor_acceptsValidInputs() {
        // valid nus ids
        assertDoesNotThrow(() -> new NusMatric("A0234567X")); // exact canonical form
        assertDoesNotThrow(() -> new NusMatric("a0234567x")); // lowercase accepted via canonicalisation
        assertDoesNotThrow(() -> new NusMatric("a0234567X")); // mixed case accepted via canonicalisation
        assertDoesNotThrow(() -> new NusMatric("A0234567x")); // mixed case accepted via canonicalisation
        assertDoesNotThrow(() -> new NusMatric("U023456W")); // canonical U-prefix form
        assertDoesNotThrow(() -> new NusMatric("u023456w")); // mixed case U-prefix accepted
    }

    @Test
    public void constructor_rejectsInvalidInputs() {
        // invalid nus ids
        IllegalArgumentException missingLastLetter =
            assertThrows(IllegalArgumentException.class, () -> new NusMatric("A0234567"));
        assertEquals(NusMatric.MESSAGE_FORMAT_CONSTRAINTS, missingLastLetter.getMessage()); // missing last letter

        IllegalArgumentException extraTrailingLetter =
            assertThrows(IllegalArgumentException.class, () -> new NusMatric("A0234567BB"));
        assertEquals(NusMatric.MESSAGE_FORMAT_CONSTRAINTS, extraTrailingLetter.getMessage()); // extra trailing letter

        IllegalArgumentException invalidPrefix =
            assertThrows(IllegalArgumentException.class, () -> new NusMatric("B0234567B"));
        assertEquals(NusMatric.MESSAGE_FORMAT_CONSTRAINTS, invalidPrefix.getMessage()); // must start with A or U

        IllegalArgumentException internalSpaces =
            assertThrows(IllegalArgumentException.class, () -> new NusMatric("A0234567  B"));
        assertEquals(NusMatric.MESSAGE_FORMAT_CONSTRAINTS, internalSpaces.getMessage()); // spaces not allowed

        IllegalArgumentException invalidTrailingCharacter =
            assertThrows(IllegalArgumentException.class, () -> new NusMatric("A0234567$"));
        assertEquals(NusMatric.MESSAGE_FORMAT_CONSTRAINTS, invalidTrailingCharacter.getMessage());

        IllegalArgumentException wrongChecksum =
            assertThrows(IllegalArgumentException.class, () -> new NusMatric("A0234567B"));
        assertEquals(NusMatric.MESSAGE_CHECKSUM_CONSTRAINTS, wrongChecksum.getMessage());

        // legacy U format should have 6 digits
        IllegalArgumentException invalidU7Format =
            assertThrows(IllegalArgumentException.class, () -> new NusMatric("U0234567B"));
        assertEquals(NusMatric.MESSAGE_FORMAT_CONSTRAINTS, invalidU7Format.getMessage());

        IllegalArgumentException legacyUFormat =
            assertThrows(IllegalArgumentException.class, () -> new NusMatric("U0906931"));
        assertEquals(NusMatric.MESSAGE_FORMAT_CONSTRAINTS, legacyUFormat.getMessage());
    }
}
