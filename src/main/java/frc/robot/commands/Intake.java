package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intaker;
public class Intake extends CommandBase {

    private Intaker intake;
    private Indexer indexer;
    private double kIntakeSpeed = 0.9;
    private double kIndexSpeed;
    private boolean reverse = false;
        
    public Intake(Intaker intake, Indexer indexer) {
        this.intake = intake; // Using ‘this’ keyword to refer current class instance variables
        this.indexer = indexer;
        addRequirements(intake);
        addRequirements(indexer);
    }

    public Intake(Intaker intake, Indexer indexer, double speed) {
        this(intake, indexer);
        kIndexSpeed = speed;        
	}

	@Override
    public void initialize() 
    {
        kIndexSpeed = reverse ? -kIndexSpeed : kIndexSpeed;
        //intake.intakePistonsOut();
    }


    @Override
    public void execute() 
    {   

        intake.setSpeed(0.9);
        SmartDashboard.putNumber("intakeCurrent", intake.getCurrent());

        /*indexer.getBeam();

        switch(indexer.getState()) {
        
        case 0:
            indexer.setSpeed(0);
            if (indexer.getStartInput())
                indexer.indexStart();
            SmartDashboard.putString("IndexingState", "0");

        case 1:
            indexer.setSpeed(kIndexSpeed);
            if (indexer.getStartInput())
             indexer.indexHappen();
            SmartDashboard.putString("IndexingState", "1");
        case 2:
            indexer.setSpeed(kIndexSpeed);
            if (indexer.getStartInput())
              indexer.indexStart();
            SmartDashboard.putString("IndexingState", "2");
        default: 
            indexer.setSpeed(0);
            indexer.setIndexDone();
    
        } */

        indexer.setSpeed(kIndexSpeed);

        SmartDashboard.putNumber("intakeSpeed", intake.getSpeed());
    }

    @Override
    public void end(boolean interrupted) 
    {
        intake.stopIntake(); //If anything happens, such as a manual overide, then Intake is stopped
        //intake.intakePistonsIn();
        indexer.stopIndexing();
    }

    @Override
    public boolean isFinished() 
    {
        return false;
        //((!indexer.getStartInput()) || (indexer.getBallCount() >= 5));
    }
}