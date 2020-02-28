package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Hopper extends SubsystemBase {

    private CANSparkMax hopperMotor;
    
    public Hopper() {
        hopperMotor = new CANSparkMax(44, MotorType.kBrushless);
        new CANEncoder(hopperMotor);
        hopperMotor.restoreFactoryDefaults();
        hopperMotor.setIdleMode(IdleMode.kCoast);
        hopperMotor.setSmartCurrentLimit(80);
        hopperMotor.enableVoltageCompensation(12);
        hopperMotor.burnFlash();
    }

    public void setSpeed(double speed) 
    {
        hopperMotor.set(speed);
        SmartDashboard.putNumber("kickupCurrent", hopperMotor.getOutputCurrent());
        SmartDashboard.putNumber("heatOfHopper", hopperMotor.getMotorTemperature());
    }

    public void stopIntake() {
        this.setSpeed(0);
    }


}