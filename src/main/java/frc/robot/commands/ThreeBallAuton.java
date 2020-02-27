package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Indexer;
public class ThreeBallAuton extends SequentialCommandGroup 
{
  public ThreeBallAuton(DriveTrain driveTrain, LimeLight lime, Shooter shooter, Hopper hopper, Indexer indexer) 
  {
    addCommands(new CenterTargetRobot(driveTrain, lime), new ShootBall(0.8, shooter, hopper, indexer));
  }
}