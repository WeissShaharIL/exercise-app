# Known Issues

This file documents known bugs and tasks in the project.

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
- **Status**: 🔴 Open

### Bug ID: BUG-002
- **Description**: When adding multiple activity entries, the "User Database Button" is pushed downwards and eventually out of the visible screen area. This occurs because the entries are not confined to a scrollable area, causing them to expand and take up the entire vertical space.
- **Steps to Reproduce**:
  1. Launch the app and navigate to the exercise log screen.
  2. Add multiple activity entries (e.g., 10 or more).
  3. Observe the "User Database Button" being displaced further down with each new entry.
- **Impact**: UI Problem - button should be in a fixed place; entries should scroll in their own area instead of pushing the button.
- **Workaround**: None.
- **Status**: 🟢 Closed

### Bug ID: BUG-003
- **Description**: Slider for height precision issue causes the stored value in the database to have floating-point errors. For example, setting the slider to `1.81` may result in a stored value of `1.809999942779541`.
- **Steps to Reproduce**:
  1. Open the user dialog or init screen.
  2. Adjust the height slider to `1.81`.
  3. Save the data and inspect the database entry.
- **Impact**: Precision error in the database for height values.
- **Workaround**: Round the value in queries or UI before displaying it.
- **Status**: 🔴 Open

### Bug ID: BUG-004
- **Description**: Rotating the phone causes all data on the screen to disappear.
- **Steps to Reproduce**:
  1. Open the app and navigate to any screen with data.
  2. Rotate the phone to switch orientations.
  3. Observe that all data disappears.
- **Impact**: Major usability issue - users lose their current session data.
- **Workaround**: None.
- **Status**: 🔴 Open

### Task ID: TASK-001
- **Description**: Update `ExerciseLogScreen.kt` to use the `ViewModel` for filtering days instead of holding the logic within the composable.
- **Steps to Complete**:
  1. Refactor the `ExerciseLogScreen` composable to remove filtering logic.
  2. Ensure filtering is managed by the `ViewModel` and data is updated accordingly.
  3. Test the refactored screen to ensure proper functionality.
- **Impact**: Improves code maintainability and adheres to MVVM architecture principles.
- **Status**: 🟡 In Progress

  ### Bug ID: BUG-005
- **Description**: Clicking on the calendar button opens the dialog. If the user selects a date and presses "OK," it works as expected. However, if the user dismisses the calendar window or cancels it, the calendar button stops responding on subsequent clicks.
- **Steps to Reproduce**:
  1. Launch the app and navigate to the screen with the calendar button.
  2. Click the calendar button to open the date picker dialog.
  3. Dismiss or cancel the calendar window without selecting a date.
  4. Attempt to click the calendar button again.
- **Impact**: Prevents users from reopening the date picker, causing usability issues.
- **Workaround**: Restarting the app allows the calendar button to work again.
- **Status**: 🔴 Open

