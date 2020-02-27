package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.ShooterConstants.*;

public class Shooter extends SubsystemBase
{
    /*
    //Shooter PID Values
    private double kp;
    private double ki;
    private double kd;

    //Keep track of sum error and change in error
    private double sumError;
    private double changeError;
    */

    /*
    Creates a new Shooter
    */

    private WPI_TalonFX shooterSlave = new WPI_TalonFX(kLShooterMotor);
    private WPI_TalonFX shooterMaster = new WPI_TalonFX(kRShooterMotor);

    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(0.0811, 0.121, 0);
    private PIDController pidController = new PIDController(kPShooter, kIShooter, kDShooter);

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
        
        compressor.setClosedLoopControl(false);

        //Sets up PID for the right shooter configuration
		shooterMaster.config_kP(0, 0.28);
        shooterMaster.config_kI(0, 0.00006);
        shooterMaster.config_kD(0, 10);

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
        //shooterMaster.setVoltage((feedforward.calculate(velocity)) + 
        //pidController.calculate(shooterMaster.getSelectedSensorVelocity(), velocity));
        //shooterMaster.config_kF(1, shooterMaster.getMotorOutputVoltage()*1023.0/12/shooterMaster.getSelectedSensorVelocity());
        shooterMaster.set(ControlMode.Velocity, velocity/600*2048);
        //shooterMaster.setVoltage(feedforward.calculate(velocity) + pidController.calculate(shooterMaster.getSelectedSensorVelocity(), velocity));

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
        return true; //(hood.get() == DoubleSolenoid.Value.kForward);
    }

    protected void useOutput(double output, double setpoint) {
        shooterMaster.setVoltage(output + feedforward.calculate(setpoint));

    }

    /*@Override
    protected double getMeasurement() {
        return rightShooter.getSelectedSensorVelocity();
    }

    /*/

}
