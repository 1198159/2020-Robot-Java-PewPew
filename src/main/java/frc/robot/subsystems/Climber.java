package frc.robot.subsystems;

import static frc.robot.Constants.ClimberConstants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase
{
    private WPI_TalonFX climberMaster = new WPI_TalonFX(kClimberL); //creates object for the TalonFx to instantiate as an object
    private WPI_TalonFX climberSlave = new WPI_TalonFX(kClimberR);

    public Climber () {
        climberMaster.configFactoryDefault(); //sets the talons to factory default configuration
        climberMaster.setNeutralMode(NeutralMode.Brake); //neutral throttle
        climberSlave.configFactoryDefault();
        climberSlave.setNeutralMode(NeutralMode.Brake);

        climberSlave.set(ControlMode.Follower, climberMaster.getDeviceID());
        //do nothing
    }

    public void setSpeed(double speed) //sets the speed for the climber, has to be tested
    {
        climberMaster.set(speed);
    }
}