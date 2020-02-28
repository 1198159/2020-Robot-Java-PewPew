package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.SamController.SamButtons;

public class SamButton extends JoystickButton {

	public SamButton(GenericHID joystick, int buttonNumber) {
		super(joystick, buttonNumber);
	}

	public SamButton(XboxController joystick, SamButtons button) {
		super(joystick, button.value);
	}

	public SamButton(SamController joystick, SamButtons button) {
		super(joystick, button.value);
	}

}