# Known Issues

This file documents known bugs in the project.

## Bug List

### Bug ID: BUG-001
- **Description**: The slider doesn't save its default value (`1.50m`) if the user doesn't interact with it.
- **Steps to Reproduce**:
    1. Open the user dialog.
    2. Leave the slider untouched.
    3. Enter a valid weight.
    4. Attempt to save the data.
- **Impact**: Prevents saving default height without user interaction.
- **Workaround**: Adjust the slider slightly before saving.
- **Status**: Open.

### Bug ID: BUG-002
- **Description**: When adding multiple activity entries, the "User Database Button" is pushed downwards and eventually out of the visible screen area. This occurs because the entries are not confined to a scrollable area, causing them to expand and take up the entire vertical space.
- **Steps to Reproduce**:
  1. Launch the app and navigate to the exercise log screen.
  2. Add multiple activity entries (e.g., 10 or more).
  3. Observe the "User Database Button" being displaced further down with each new entry.

- **Impact**: UI Porblem - button should be fixed place, also all the entries should be in their own area and not push the button
- **Workaround**: None
- **Status**: Open.
