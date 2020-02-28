package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.ShooterConstants.*;

public class Shooter extends SubsystemBase
{

    private WPI_TalonFX shooterSlave = new WPI_TalonFX(kLShooterMotor);
    private WPI_TalonFX shooterMaster = new WPI_TalonFX(kRShooterMotor);

    //instantiate the hood here
    private Solenoid hood = new Solenoid(6);
    private Compressor compressor = new Compressor();

    public Shooter()
    {
        shooterMaster.configFactoryDefault();
        shooterSlave.configFactoryDefault();

        shooterSlave.set(ControlMode.Follower, shooterMaster.getDeviceID());

        shooterMaster.configVoltageCompSaturation(12);
        shooterMaster.enableVoltageCompensation(true);
        
        compressor.setClosedLoopControl(true);

        //Sets up PID for the right shooter configuration
		shooterMaster.config_kP(0, kPShooter);
        shooterMaster.config_kI(0, kIShooter);
        shooterMaster.config_kD(0, kDShooter);

        //determines if the values are inverted or not 
        shooterSlave.setInverted(true);
        shooterMaster.setInverted(false);

        //gives whatever chosen feedback to the user 
        shooterSlave.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor); 
        shooterMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

        //sets the shooters in a neutral state
        shooterSlave.setNeutralMode(NeutralMode.Coast); 
        shooterMaster.setNeutralMode(NeutralMode.Coast);

    }

    public void setPIDSpeed(double velocity) 
    {
        shooterMaster.set(ControlMode.Velocity, velocity/600*2048);

        SmartDashboard.putNumber("VelocityMaster: ", shooterMaster.getSelectedSensorVelocity() * 600 / 2048);
        SmartDashboard.putNumber("VelocitySlave: ", shooterSlave.getSelectedSensorVelocity());
        SmartDashboard.putNumber("CurrentMaster: ", shooterMaster.getSupplyCurrent());
        SmartDashboard.putNumber("CurrentSlave: ", shooterSlave.getSupplyCurrent());
    }

    public void setSpeed(double speed) {
        shooterMaster.set(speed);
        SmartDashboard.putNumber("VelocityMaster: ", shooterMaster.getSelectedSensorVelocity() * 600 / 2048);
        SmartDashboard.putNumber("VelocitySlave: ", shooterSlave.getSelectedSensorVelocity());
        SmartDashboard.putNumber("CurrentMaster: ", shooterMaster.getSupplyCurrent());
        SmartDashboard.putNumber("CurrentSlave: ", shooterSlave.getSupplyCurrent());
        SmartDashboard.putNumber("Voltage", shooterMaster.getMotorOutputVoltage());
    }


    public double getRPM() 
    {
        return shooterMaster.getSelectedSensorVelocity();
    }

    public void hoodUp() {
        //hood.set(Value.kForward);
    }

    public void hoodDown() {
        //hood.set(Value.kReverse);
    }

    public boolean getPistonState() {
        return (hood.get());
    }


}
