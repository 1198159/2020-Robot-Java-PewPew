package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intaker;
public class Intake extends CommandBase {

    private Intaker intake;
    private Indexer indexer;
    private double kIntakeSpeed = 0.8;
        
    public Intake(Intaker intake, Indexer indexer) {
        this.intake = intake; // Using ‘this’ keyword to refer current class instance variables
        this.indexer = indexer;
        addRequirements(intake);
        addRequirements(indexer);
    }

    @Override
    public void initialize() 
    {
    }


    @Override
    public void execute() 
    {   

        intake.setSpeed(kIntakeSpeed);
        SmartDashboard.putNumber("intakeCurrent", intake.getCurrent());

        indexer.getBeam();

        switch(indexer.getState()) {
        
        case 0:
            indexer.setSpeed(0);
            if (indexer.getStartInput())
                indexer.indexStart();
            SmartDashboard.putString("IndexingState", "0");

        case 1:
            indexer.setSpeed(kIntakeSpeed);
            if (indexer.getStartInput())
             indexer.indexHappen();
            SmartDashboard.putString("IndexingState", "1");
        case 2:
            indexer.setSpeed(kIntakeSpeed);
            if (indexer.getStartInput())
              indexer.indexStart();
            SmartDashboard.putString("IndexingState", "2");
        default: 
            indexer.setSpeed(0);
            indexer.setIndexDone();
    
        } 

        /*
        if(indexer.getStartInput() && !intakingBall)//If the system was not intaking a ball before and is now, the ball count increases
            indexer.incrementBallCount();
        intakingBall = indexer.getStartInput(); //Checks to see if the system is currently intaking a ball
        
        if(intakingBall)   
            indexer.indexBalls(0.5);
        else
            indexer.stopIndexing();

        if (indexer.getBallCount() < 5) //if the indexer reads less than 5 balls, than the intake will continue to spin
            intake.setSpeed(kIntakeSpeed);
        else
            intake.stopIntake(); //stops intake if ball count is 5*/
        
        SmartDashboard.putNumber("intakeSpeed", intake.getSpeed());
    }

    @Override
    public void end(boolean interrupted) 
    {
        intake.stopIntake(); //If anything happens, such as a manual overide, then Intake is stopped
        //intake.intakePistonsIn();
        //indexer.stopIndexing();
    }

    @Override
    public boolean isFinished() 
    {
        return false;
        //((!indexer.getStartInput()) || (indexer.getBallCount() >= 5));
    }
}