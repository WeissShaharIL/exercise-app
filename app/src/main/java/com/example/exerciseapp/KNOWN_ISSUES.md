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
