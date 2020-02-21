package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.TurretConstants.*;

public class Turret extends SubsystemBase
{
    public CANSparkMax turret = new CANSparkMax(kTurretMotor, MotorType.kBrushless);
    public CANEncoder encoder = new CANEncoder(turret);
    public CANPIDController turretController;
    

    public Turret () 
    {
        encoder.setPositionConversionFactor(kGearRatio);
        ZeroEncoder();
        pidInit();
    }

    public void pidInit()
    {
        turretController = turret.getPIDController();
        turretController.setP(0.001);
        turretController.setI(0.0001);
        turretController.setD(0.1);
        turretController.setOutputRange(-0.4, 0.4);
    }


    public void setSpeed(double speed)
    {
        turret.set(speed);
    }
    public double getMotorPosition()
    {
        return encoder.getPosition();
    }

    public void ZeroEncoder()
    {
        //Use the Hall Effect Sensor to find the position of the motor and set the encoder value appropriately
    }

    public void ZeroTurret()
    {
        turretController.setReference(0, ControlType.kPosition);
    }
}