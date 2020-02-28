package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;

public class CenterTargetRobot extends CommandBase //this class is apart of the CommandBase 
{

    private LimeLight lime; 
    private DriveTrain driveTrain;

    //private double goal = 0.0;
    private double error;
    private double offsetX;
    private double margin = 0.2;

    private int finalCount = 0;

    private boolean SeesTarget = false;

    private int count = 0;
    private double offsetXPrev;

    private double OFFSET = -3;

    private double speed;
    //old is 0.0075
    private double kp = 0.0075   ;
    //old is 0.0005,0.0001,0.00075 is good
    private double ki = 0.008015;
    //old is 0.1    
    private double kd = 3;

    //good is 0.0075, 0.00015, 10


    private double deltaI;
    private double deltaD;

    public CenterTargetRobot(DriveTrain driveTrain, LimeLight lime)
    {
        this.driveTrain = driveTrain; // Using ‘this’ keyword to refer current class instance variables
        this.lime = lime;
        addRequirements(driveTrain);
        addRequirements(lime);
        offsetX = lime.getTx();
        deltaI = 0.2 * offsetX/Math.abs(offsetX);
        deltaD = 0;
        if(offsetX != 0)
        {
            SeesTarget = true;
        }
    }
    public double PID(double err)
    { 
        
        if(OFFSET == -3) {
            return deltaI;
        }

        return (kp * err) + (OFFSET * (err / Math.abs(err))) + (kd * deltaD);
    }
    public void updateI(double err) 
    {
        deltaI += err * ki;
    }
    public void updateD(double err) 
    {
        deltaD = offsetX - offsetXPrev;
    }   
    

    @Override
    public void execute() 
    {
        offsetX = lime.getTx();
        error = offsetX;
        finalCount++;
        if(Math.abs(offsetX) > margin)
        {
            count = 0;
            
            driveTrain.driveCartesian(PID(error), -PID(error));
        }
        else
        {
            count += 1;
        }
       
        
        offsetXPrev = offsetX;

        
        speed = Math.abs(driveTrain.getWheelSpeeds().leftMetersPerSecond);
        if(speed != 0)
        {
            if(OFFSET == -3)
            {
                OFFSET = Math.abs(PID(error));
            }
            deltaI = 0;
        }

        updateI(error);
        updateD(error);

    }
    @Override
    public boolean isFinished() {

        if (finalCount > 750) {
            finalCount = 0;
            return true;
        }
        else if(SeesTarget) {
            if (count > 10) {
                finalCount = 0;
                return true;
            }
            return count > 10;
        }
        else {
            finalCount = 0;
            return true;
        }
    }
    
}
