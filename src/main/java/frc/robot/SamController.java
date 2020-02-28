//Desperately "borrowed" from team 319 during week 6 of build season
//credit to "Bob", original name was BobController, I renamed it
package frc.robot;


import edu.wpi.first.wpilibj.Joystick;

public class SamController extends Joystick {

	public SamController(int port) {
		super(port);
	}

	public SamButton xButton = new SamButton(this, SamButtons.X);
	public SamButton yButton = new SamButton(this, SamButtons.Y);
	public SamButton aButton = new SamButton(this, SamButtons.A);
	public SamButton bButton = new SamButton(this, SamButtons.B);
	public SamButton rightBumper = new SamButton(this, SamButtons.RIGHT_BUMPER);
	public SamButton leftBumper = new SamButton(this, SamButtons.LEFT_BUMPER);
	public SamButton startButton = new SamButton(this, SamButtons.START);
	public SamButton selectButton = new SamButton(this, SamButtons.SELECT);

	static enum SamButtons {

		A(1), B(2), X(3), Y(4), LEFT_BUMPER(5), RIGHT_BUMPER(6), SELECT(7), START(8), LEFT_STICK(9), RIGHT_STICK(10);

		final int value;

		SamButtons(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public void setRumble(double leftValue, double rightValue) {
		setRumble(RumbleType.kLeftRumble, leftValue);
		setRumble(RumbleType.kRightRumble, rightValue);
	}

}