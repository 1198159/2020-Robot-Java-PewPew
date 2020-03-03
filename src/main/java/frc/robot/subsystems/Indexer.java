package frc.robot.subsystems;

//imports for the SparkMax's
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//import for the Digital sensor aka beam break sensor 
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;


import static frc.robot.Constants.IndexerConstants.*;

public class Indexer extends SubsystemBase {

    private int ballCount = 3;  //starting ball count is 3
    private CANSparkMax midMotor = new CANSparkMax(kMidIndexMotor, MotorType.kBrushless); //Arbitrary Arguments, just for now.
    private CANSparkMax extMotor = new CANSparkMax(kExtIndexMotor, MotorType.kBrushless);
    private boolean indexStarted;
    private boolean indexHappening;
    private boolean indexDone;
    private int state;

    //Digital Sensor port for Beambreak
    //beambreakStart is the sensor that is closest to the turret
    private DigitalInput beamBreakStartInput = new DigitalInput(kBeamBreakInInput); //apparently beamBreak can count as a digital input, but with each input there needs to be an output.
    private DigitalOutput beamBreakStartOutput = new DigitalOutput(kBeamBreakInOutput);

    //beambreakEnd is the sensor that is closest to the turret
    //private DigitalInput beamBreakEndInput = new DigitalInput(kBeamBreakOutInput); 
    //private DigitalOutput beamBreakEndOutput = new DigitalOutput(kBeamBreakOutOutput);


    public Indexer() 
    {
        //instantiates the motors
        midMotor.restoreFactoryDefaults();
        midMotor.setIdleMode(IdleMode.kBrake);
        extMotor.restoreFactoryDefaults();
        extMotor.setIdleMode(IdleMode.kBrake);
        extMotor.setSmartCurrentLimit(50);
        midMotor.setSmartCurrentLimit(50);
        midMotor.burnFlash();
        extMotor.burnFlash();
        midMotor.enableVoltageCompensation(12);
        extMotor.enableVoltageCompensation(12);

        //midMotor.setClosedLoopRampRate(2);
        //midMotor.setOpenLoopRampRate(2);
        //extMotor.setClosedLoopRampRate(2);
        //extMotor.setOpenLoopRampRate(2);

        indexStarted = false;
        indexHappening = false;
        indexDone = false;
        //midMotor.follow(extMotor);
    }

    public void getBeam() {
        SmartDashboard.putBoolean("beamBreak", beamBreakStartOutput.get());
    }

    public void setSpeed(double speed1, double speed2)
    {
        extMotor.set(speed1);
        midMotor.set(speed2);
        SmartDashboard.putNumber("heatOfMid", midMotor.getMotorTemperature());
        SmartDashboard.putNumber("heatOfExt", extMotor.getMotorTemperature());
        SmartDashboard.putNumber("currentOfMid", midMotor.getOutputCurrent());
        SmartDashboard.putNumber("currentOfExt", extMotor.getOutputCurrent());

    }

    public void setSpeed(double d) {
        extMotor.set(d*2.25);
        midMotor.set(d);
	}

    public void stopIndexing()
    {
        extMotor.set(0);
        midMotor.set(0);
    }

    public boolean getStartInput() {
        //return false;
        return beamBreakStartOutput.get();
    }

    public boolean getEndInput() {
        return false;
        //return beamBreakEndInput.get();
    }

    public int getBallCount() 
    {
       return ballCount;
    }

    public void incrementBallCount() {
        ballCount++;
    }

    public void decrementBallCount() {
        ballCount--;
    }

    //ALL STATE MACHINE STUFF

    public boolean getIndexingStarted() {
        return indexStarted;
    }

    public void setIndexingStarted() {
        indexStarted = !indexStarted;
    }

    public boolean getIndexHappening() {
        return indexStarted;
    }

    public void setIndexHappening() {
        indexHappening = !indexHappening;
    }

    public boolean getIndexDone() {
        return indexDone;
    }

    public void setIndexDone() {
        indexDone = false;
        indexHappening = false;
        indexStarted = false;
    }

    public void indexStart() {
        indexStarted = true;
        indexHappening = false;
        indexDone = false;
    }

    public void indexHappen() {
        indexStarted = false;
        indexHappening = true;
        indexDone = false;
    }

    public void indexDone() {
        indexStarted = false;
        indexHappening = false;
        indexDone = true;
    }

    public int getState() {
        
        if(!indexStarted && !indexHappening && !indexDone && getStartInput())
            state = 0;
        else if (indexStarted && !indexHappening && !indexDone && !getStartInput())
            state = 1;
        else if (!indexStarted && indexHappening && !indexDone && getStartInput())
            state = 2;
        else 
            state = 3;

        return state;
    }

}