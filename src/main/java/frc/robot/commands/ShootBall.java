/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class ShootBall extends CommandBase {
    /**
     * Creates a new DriveCommands.
     */
    private double speed;

    private final Shooter shooter;
    private final Hopper hopper;
    private final Indexer indexer;

    public ShootBall(double speed, Shooter shooter, Hopper hopper, Indexer indexer) 
    {
        this.hopper = hopper;
        this.shooter = shooter;
        this.indexer = indexer;
        this.speed = speed;

        addRequirements(shooter);
        addRequirements(hopper);
        addRequirements(indexer);
    }

    public double calculateNeededSpeed () { //method to figure the speed of the shooter
        //double distance = Robot.m_robotContainer.lime.getDistanceToTarget();
        //there is literally no way to do that without testing it using the actual shooter
        return speed;
    }

	// Called when the command is initially scheduled.
    @Override
    public void initialize() {
        //raise the hood here        
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() 
    {
        shooter.setPIDSpeed(6000);
        indexer.setSpeed(speed);
        hopper.setSpeed(0.95);
    }

    // Called once the command ends orlkkk is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooter.setSpeed(0);
        indexer.stopIndexing();
        hopper.setSpeed(0);
        //Lower the hood here
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return false;
    }
}

