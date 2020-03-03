package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IntakeConstants.*;

public class Intaker extends SubsystemBase {

    private CANSparkMax intakeMotor = new CANSparkMax(kIntakeMotor, MotorType.kBrushless);
    private CANEncoder encoder = new CANEncoder(intakeMotor);
    //private Solenoid intakePiston = new Solenoid(kIntakePiston);
    //private Solenoid shooterPiston = new Solenoid(kShooterPiston);

    public Intaker() {
        intakeMotor.restoreFactoryDefaults();
        intakeMotor.setIdleMode(IdleMode.kBrake);
        intakeMotor.enableVoltageCompensation(12);
        intakeMotor.setSmartCurrentLimit(60);
        intakeMotor.burnFlash();
    }

    public void togglePistons() {
        //intakePiston.set(!intakePiston.get());
    }

    public void intakePistonsIn() {
        //intakePiston.set(false);

    }

    public void intakePistonsOut() {
        //intakePiston.set(true);

    }

    public void setSpeed(double speed) {
        intakeMotor.set(speed);
    }

    public void stopIntake() {
        setSpeed(0);
    }

	public double getSpeed() {
		return encoder.getVelocity();
	}

	public double getCurrent() {
		return intakeMotor.getOutputCurrent();
	}

	public boolean intakePistonsGet() {
		return false;//intakePiston.get();
	}


}