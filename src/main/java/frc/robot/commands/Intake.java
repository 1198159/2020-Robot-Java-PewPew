package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.IntakeConstants.*;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intaker;
public class Intake extends CommandBase {

    private Intaker intake;
    private Indexer indexer;
    private double kIntakeSpeed = 0.75;
    
    private boolean intakingBall = false;
    
    public Intake(Intaker intake, Indexer indexer) {
        this.intake = intake; // Using ‘this’ keyword to refer current class instance variables
        this.indexer = indexer;
        addRequirements(intake);
        addRequirements(indexer);
    }

    @Override
    public void initialize() 
    {
        intake.intakePistonsOut();
    }


    @Override
    public void execute() 
    {   
        if(indexer.beamBreakStartInput.get() && !intakingBall)//If the system was not intaking a ball before and is now, the ball count increases
            indexer.ballCount++;

        intakingBall = indexer.beamBreakStartInput.get(); //Checks to see if the system is currently intaking a ball
        
        if(intakingBall)   
            indexer.indexBalls();
        else
            indexer.stopIndexing();

        if (indexer.getBallCount() < 5) //if the indexer reads less than 5 balls, than the intake will continue to spin
            intake.setSpeed(kIntakeSpeed);
        else
            intake.stopIntake(); //stops intake if ball count is 5
    }

    @Override
    public void end(boolean interrupted) 
    {
        intake.stopIntake(); //If anything happens, such as a manual overide, then Intake is stopped
        intake.intakePistonsIn();
        indexer.stopIndexing();
    }

    @Override
    public boolean isFinished() 
    {
        return indexer.getBallCount() >= 5;
    }
}