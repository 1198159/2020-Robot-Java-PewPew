package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class ShootBall extends SequentialCommandGroup{

    private double speed;
    private Shooter shooter;
    private Hopper hopper;
    private Indexer indexer;

    public ShootBall(double speed, Shooter shooter, Hopper hopper, Indexer indexer) {
        this.speed = speed;
        this.shooter = shooter;
        this.hopper = hopper;
        this.indexer = indexer;
        //addRequirements(speed, shooter, hopper, indexer);
    }

}