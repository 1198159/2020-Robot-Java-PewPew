package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Turret;

public class CenterTargetTurret extends CommandBase
{


    private LimeLight lime;
    private Turret turret;

    private double offsetX;
    private double margin = 0.2;

    private double upperPhysicalBound =  0.35;
    private double lowerPhysicalBound = -0.35;

    private boolean SeesTargetCurrent = false;

    private int count = 0;
    private double offsetXPrev;

    private double minPower = 0.2; //In future we will set this to the min value that is provided by th encoder
    
    private double kp = 0.0075; //old is 0.0075
    //private double ki = 0.00015; //old is 0.0005,0.0001,0.00075 is good
    private double kd = 3; //old is 0.1  
    //good is 0.0075, 0.00015, 10 (for the center target ROBOT, turret remains to be seen)

    private double deltaD;

    private int noTargetCount = 0;

    private boolean centered = false;

    public CenterTargetTurret(Turret turret, LimeLight lime)
    {
        this.turret = turret;
        this.lime = lime;

        addRequirements(turret);
        addRequirements(lime);

        offsetX = lime.getTx(); //sets the error variable
        deltaD = 0; //sets the change in error variable

        if(lime.getTv() != 0) //checks if the robot can see the target
            SeesTargetCurrent = true;
    }
    public double PID(double err)
    {
        return (kp * err) + (minPower * getSign(err)) + (kd * deltaD); //gets the value that the motor should be set to
    }

    public int getSign(double x) //returns 1 if a number is positive and -1 if it is negative
    {
        if(x==0)
            return 0;
        else if(x > 0)
            return 1;
        else
            return -1;
    }


    /*
    public void updateI(double err) 
    {
        deltaI += err * ki;
    }
    */


    public void updateD(double err) 
    {
        deltaD = offsetX - offsetXPrev;
    }   
    

    @Override
    public void execute() 
    {
        if(lime.getTv() != 0)
            SeesTargetCurrent = true; //checks if the target can be seen
        
        
        if(turret.getMotorPosition() > upperPhysicalBound) //moves turret out of the danger zone if value is too high
        {
            turret.setSpeed(-0.1);
            updatePID(false);  //updates the PID values without setting the motor speeds
        }
        else if(turret.getMotorPosition() < lowerPhysicalBound)//moves turret out of the danger zone if value is too low
        {
            turret.setSpeed(0.1);
            updatePID(false);  //updates the PID values without setting the motor speeds
        }
        else
            updatePID(true);  //updates the PID values and sets the motor speeds
    }

    public void updatePID(boolean changeSpeed)
    {
        offsetX = lime.getTx(); //gets new error value
        if(Math.abs(offsetX) > margin) //checks if robot is within margin
        {
            count = 0;
            if(changeSpeed)
                turret.setSpeed(PID(offsetX)); //sets the motor speeds only if not in the danger zone
        }
        else
            count += 1; //counts up while within margin

        offsetXPrev = offsetX; //stores previous error to calculate change in error
        updateD(offsetX); //sets the new deltaD value
    }

    public void end()
    {
        turret.setSpeed(0); //makes sure that the motor does not continue to move after command is completed
        if(!centered)
            turret.ZeroTurret();
        //else   //Error is here vvvv because the sController has not been initialized in robot container yet
        //    RobotContainer.sController.setRumble(0.5,0.5); //rumbles the xbox controller if the turret has successfully centered on the target

    }

    @Override
    public boolean isFinished() 
    {
        if(SeesTargetCurrent) //Checks if robot can currently see the target
        {
            noTargetCount = 0; //resets the counter for not seeing the target to zero
            if (count > 10) //checks if the robot has been within the margin for 10 counts
                centered = true; //affirms that the robot has centered
            return count > 10; //returns true only if the robot has been within the margin for 10 counts
        }
        else if(noTargetCount > 20) //checks if the robot has not seen the target for 20 counts
            return true;
        else
        {
            noTargetCount++; //increases the count for how long the robot has not seen the target
            return false;
        }
    }
}