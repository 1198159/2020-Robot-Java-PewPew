package frc.robot.subsystems;

//imports for the SparkMax's
import com.revrobotics.SparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//import for the Digital sensor aka beam break sensor 
import edu.wpi.first.wpilibj.InterruptableSensorBase;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANError;


import static frc.robot.Constants.IndexerConstants.*;

public class Indexer extends SubsystemBase {

    public int ballCount = 3;  //starting ball count is 3
    public CANSparkMax midMotor = new CANSparkMax(kMidIndexMotor, null); //Arbitrary Arguments, just for now.
    public CANSparkMax extMotor = new CANSparkMax(kExtIndexMotor, null);

    //Digital Sensor port for Beambreak
    //beambreakStart is the sensor that is closest to the turret
    public DigitalInput beamBreakStartInput = new DigitalInput(kBeamBreakInInput); //apparently beamBreak can count as a digital input, but with each input there needs to be an output.
    public DigitalOutput beamBreakStartOutput = new DigitalOutput(kBeamBreakInOutput);

    //beambreakEnd is the sensor that is closest to the turret
    public DigitalInput beamBreakEndInput = new DigitalInput(kBeamBreakOutInput); 
    public DigitalOutput beamBreakEndOutput = new DigitalOutput(kBeamBreakOutOutput);


    public Indexer() 
    {
        //instantiates the motors
        midMotor.restoreFactoryDefaults();
        extMotor.restoreFactoryDefaults();

        midMotor.follow(extMotor);
    }

    public void indexBalls()
    {
        extMotor.set(kIndexerSpeed);
    }
    public void stopIndexing()
    {
        extMotor.set(0);
    }

    public int getBallCount() 
    {
       return ballCount;
    }
}