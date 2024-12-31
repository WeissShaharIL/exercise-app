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
- **Impact**: UI Problem - button should be in a fixed place; entries should scroll in their own area instead of pushing the button.
- **Workaround**: None.
- **Status**: Open.

### Bug ID: BUG-003
- **Description**: Slider for height precision issue causes the stored value in the database to have floating-point errors. For example, setting the slider to `1.81` may result in a stored value of `1.809999942779541`.
- **Steps to Reproduce**:
  1. Open the user dialog or init screen.
  2. Adjust the height slider to `1.81`.
  3. Save the data and inspect the database entry.
- **Impact**: Precision error in the database for height values.
- **Workaround**: Round the value in queries or UI before displaying it.
- **Status**: Open.
