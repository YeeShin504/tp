---
layout: page
title: User Guide
---

Course Management System (CMS) is a **desktop app for managing student credentials**, optimized for fast keyboard-first usage through a Command Line Interface (CLI).

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure Java `17` is installed.
   * Check with: `java --version`
   * **Mac users:** Use the JDK setup guide [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from your project release page.

1. Create or choose a folder as your CMS home folder (example: `C:\Users\<you>\Documents\cms` on Windows).

1. Copy the downloaded jar into that folder.

1. Open a terminal, `cd` into that folder, and run:
   * `java -jar cms.jar`

1. Type commands in the command box and press Enter.

1. CMS stores data under the home folder in `data/CMS.json`.
   * To transfer data to another computer, copy the old `data/CMS.json` into the new home folder (overwrite the new empty file after first launch).

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format
--------|------------------
**Help** | `help`
**List** | `list`
**Add** | `add n/NAME id/NUS_ID role/ROLE soc/SOC_USERNAME gh/GITHUB_USERNAME e/EMAIL p/PHONE t/TUTORIAL_GROUP [tag/TAG]...`
**Edit** | `edit INDEX [n/NAME] [id/NUS_ID] [role/ROLE] [soc/SOC_USERNAME] [gh/GITHUB_USERNAME] [e/EMAIL] [p/PHONE] [t/TUTORIAL_GROUP] [tag/TAG]...`
**Find** | `find n/KEYWORD [MORE_NAME_KEYWORDS]...` / `find id/NUS_ID [MORE_NUS_IDS]...`
**Delete** | `delete INDEX` / `delete INDEX [MORE_INDEXES]...` / `delete id/NUS_ID`
**Purge all records** | `clear`
**Exit** | `exit`

--------------------------------------------------------------------------------------------------------------------

## Features

### For CLI beginners

A command has a command word plus fields.

* Command word: `add`, `edit`, `find`, ...
* Prefixes identify each field, e.g. `n/`, `id/`, `e/`.
* Example full command:
  * `add n/John Doe id/A0234567B role/tutor soc/johndoe gh/johndoe e/johndoe@u.nus.edu p/91234567 t/T01 tag/mentor`

### Notes about command format

* Words in `UPPER_CASE` are values to provide.
* Items in square brackets are optional.
* `...` means the field can be repeated.
* Parameters can be in any order.
* For commands without parameters (`help`, `list`, `exit`, `clear`), extra text is ignored.

### Viewing help : `help`

Shows a message explaining how to access the help page.

Format: `help`

### Listing all student and tutor records : `list`

Shows all records currently stored in CMS.

Format: `list`

### Adding a student / tutor : `add`

Adds a student or tutor record to CMS.

Format: `add n/NAME id/NUS_ID role/ROLE soc/SOC_USERNAME gh/GITHUB_USERNAME e/EMAIL p/PHONE t/TUTORIAL_GROUP [tag/TAG]...`

All required fields in `add` must be valid (See [Fields and accepted formats](#fields-and-accepted-formats)).

Duplicate handling:
* Add is rejected if unique fields conflict with an existing person (e.g. same NUS ID / SoC username / GitHub username / email).

Examples:
* `add n/David Tan id/A0211111C role/student soc/david1 gh/davidtan99 e/david@u.nus.edu p/97654321 t/T05`
* `add n/John Doe id/A0234567B role/tutor soc/johndoe gh/johndoe e/johndoe@u.nus.edu p/91234567 t/T01 tag/python-experienced`

### Editing a student / tutor : `edit`

Edits an existing student or tutor record in CMS.

Format: `edit INDEX [n/NAME] [id/NUS_ID] [role/ROLE] [soc/SOC_USERNAME] [gh/GITHUB_USERNAME] [e/EMAIL] [p/PHONE] [t/TUTORIAL_GROUP] [tag/TAG]...`

* Edits the person at the specified `INDEX`.
* `INDEX` must be a positive integer (1, 2, 3, ...).
* At least one optional field must be provided.
* Existing values are replaced by the input values.
* When `tag/` is used, existing tags are replaced (not cumulative).
* You can clear all tags by using `tag/` with no value.
* Edited values must satisfy the same field rules as `add` (see [Fields and accepted formats](#fields-and-accepted-formats)).

Examples:
* `edit 1 p/91234567 e/johndoe@example.com`
* `edit 2 n/Betsy Crower tag/`
* `edit 3 id/A0654321B role/student soc/betsy3 gh/betsycrowe t/T07`

### Finding student / tutors : `find`

Finds persons whose names or NUS IDs contain any of the given keywords.

Format:
* `find n/KEYWORD [MORE_NAME_KEYWORDS]...`
* `find id/NUS_ID [MORE_NUS_IDS]...`

* Prefix is required (`n/` for name search, `id/` for NUS ID search).
* Search is case-insensitive for names. e.g. `n/hans` will match `Hans`.
* Order of keywords does not matter for name search. e.g. `find n/Hans n/Bo` will match `find n/Bo n/Hans`.
* Full words are matched for names. e.g. `find n/Han` will not match `Hans`.
* A person matching at least one keyword is returned (OR search) for name search.

Examples:
* `find n/jane n/eunice`
* `find n/jane eunice`
* `find id/A0123456B A1234567C`
* `find id/A0123456B id/A1234567C`

### Deleting a student / tutor : `delete`

Deletes one or more persons by displayed index, or by NUS ID.

Format:
* `delete INDEX`
* `delete INDEX [MORE_INDEXES]...`
* `delete id/NUS_ID`

* For index-based delete, each index refers to the displayed list and must be a positive integer.
* For NUS ID delete, the NUS ID must follow the same format as [`id/NUS_ID`](#field-nus-id).

Examples:
* `delete 2`
* `delete 1 3 5`
* `delete id/A0234567B`

### Purging all records : `clear`

Deletes **all** records from CMS.

Format: `clear`

Warning: this is irreversible from within the app.

### Exiting the program : `exit`

Exits CMS.

Format: `exit`

### Saving data

CMS saves data automatically after commands that modify data.

### Editing the data file

CMS data is stored in `[CMS home folder]/data/CMS.json`.

Warning:
* Invalid manual edits can cause CMS to discard corrupted data on next launch.
* Back up `CMS.json` before editing manually.

--------------------------------------------------------------------------------------------------------------------

## Fields and accepted formats

Use this section as a quick checklist for `add` and `edit`.

### `n/NAME` {#field-name}
* Letters, numbers, and spaces only.
* Cannot be blank.
* Case sensitivity: case-sensitive (stored as entered).
* Valid: `n/John Doe`
* Invalid: `n/John@Doe`

### `id/NUS_ID` {#field-nus-id}
* Must be `A` + 7 digits + uppercase letter (e.g. `A0234567B`).
* Must be unique in CMS.
* Case sensitivity: case-sensitive (`A` and trailing letter must be uppercase).
* Valid: `id/A0234567B`
* Invalid: `id/a0234567b`

### `role/ROLE` {#field-role}
* Must be exactly `student` or `tutor`.
* Case sensitivity: case-sensitive (lowercase only).
* Valid: `role/student`
* Invalid: `role/Student`

### `soc/SOC_USERNAME` {#field-soc-username}
* Either a SoC-style username or a valid NUS ID format.
* SoC-style username rules:
  * 5 to 8 characters.
  * Lowercase letters, digits, and hyphens only.
  * Cannot start or end with a hyphen.
  * No spaces.
* Must be unique in CMS.
* Case sensitivity: case-sensitive.
* Valid: `soc/john1`
* Invalid: `soc/-john`

### `gh/GITHUB_USERNAME` {#field-github-username}
* 1-39 characters.
* Letters, digits, and hyphens only.
* Cannot start/end with `-`.
* Must be unique in CMS.
* Case sensitivity: case-sensitive.
* Valid: `gh/jane-lim123`
* Invalid: `gh/-jane`

### `e/EMAIL` {#field-email}
* Must be a valid email format.
* Case sensitivity: case-sensitive.
* Valid: `e/johndoe@u.nus.edu`
* Invalid: `e/johndoe@u`

### `p/PHONE` {#field-phone}
* Digits only.
* At least 3 digits.
* Case sensitivity: not applicable (numeric only).
* Valid: `p/91234567`
* Invalid: `p/+6591234567`

### `t/TUTORIAL_GROUP` {#field-tutorial-group}
* Must be `T01` to `T99`.
* Case sensitivity: case-sensitive (`T` must be uppercase).
* Valid: `t/T01`
* Invalid: `t/t01`

### `tag/TAG` {#field-tag}
* Optional, repeatable.
* Use letters, digits, and hyphens.
* No spaces inside a tag.
* Cannot start or end with a hyphen.
* Repeated tags for the same person are kept only once.
* Case sensitivity: case-sensitive.
* Valid: `tag/python-experienced`
* Invalid: `tag/needs help`

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the app to a secondary screen and later switch to one screen, the GUI may open off-screen. Delete `preferences.json` before starting again.
2. **If the Help Window is minimized**, triggering help again may keep it minimized, and requires you to manually restore it.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?  
**A**: Install CMS on the other computer, launch once, then replace the new `data/CMS.json` with your old one.
