package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.IntakeConstants.*;

public class Intaker extends SubsystemBase {

    private CANSparkMax intakeMotor = new CANSparkMax(kIntakeMotor, MotorType.kBrushless);
    private CANEncoder encoder = new CANEncoder(intakeMotor);
    //private DoubleSolenoid intakePistons = new DoubleSolenoid(kLIntakePiston, kRIntakePiston);

    public Intaker() {
        intakeMotor.restoreFactoryDefaults();
        intakeMotor.setIdleMode(IdleMode.kBrake);
        intakeMotor.enableVoltageCompensation(12);
        intakeMotor.setSmartCurrentLimit(50);
        intakeMotor.burnFlash();

    }

    public void intakePistonsIn() {
        //intakePistons.set(Value.kReverse);
    }

    public void intakePistonsOut() {
        //intakePistons.set(Value.kForward);
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


}