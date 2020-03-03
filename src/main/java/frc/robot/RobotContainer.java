/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.List;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AutoDriveCommand;
import frc.robot.commands.AutonCommand;
import frc.robot.commands.CenterTargetRobot;
import frc.robot.commands.Climb;
import frc.robot.commands.ClimbReverse;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.FireSolenoids;
import frc.robot.commands.FeedToShooter;
import frc.robot.commands.SpinUp;
import frc.robot.commands.Intake;
import frc.robot.commands.IntakeDown;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intaker;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;
import static frc.robot.Constants.ChassisConstants.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  public final DriveTrain driveTrain = new DriveTrain();
  public final Shooter shooter = new Shooter();
  public final LimeLight lime = new LimeLight();
  public final Turret turret = new Turret();
  public final Climber climber = new Climber();
  public final Intaker intake = new Intaker();
  public final Indexer indexer = new Indexer();
  public final Hopper hopper = new Hopper();

  public Joystick driverLeft = new Joystick(0);
  public Joystick driverRight = new Joystick(1);

  public JoystickButton leftTrigger = new JoystickButton(driverLeft, 1);

  public JoystickButton throwawayButton = new JoystickButton(driverLeft, 7);
  public JoystickButton climberUP = new JoystickButton(driverLeft, 5);
  public JoystickButton climberDOWN = new JoystickButton(driverRight, 6);

  public SamController sController = new SamController(2);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  public double getLeft() {
    return driverLeft.getRawAxis(1);

  }

  public double getRight() {
    return driverRight.getRawAxis(1);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating ajwpilibj.Joystick} or {@link XboxController}), and
   * then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // ADD OR CHANGE BASED ON JAMES
    // driverRightTrigger.whileHeld(new CenterTargetRobot(driveTrain, lime));
    // driverLeftTrigger.whileHeld(new Climb(climber));
    // sController.aButton.whileHeld(new Intake(intake, indexer, 0.4));
    // sController.yButton.whileHeld(new Intake(intake, indexer, -0.4));
    //sController.rightBumper.whileHeld(new ShootBall(0.4, shooter, hopper, indexer));
    //sController.aButton.whileHeld(new Intake(intake, indexer, 0.4));
    //sController.yButton.whileHeld(new Intake(intake, indexer, -0.4));

    sController.aButton.whileHeld(new Intake(intake, indexer, -0.4));
    sController.yButton.whileHeld(new Intake(intake, indexer, 0.4));

    //sController.leftBumper.whileHeld(new FireSolenoids(intake));


    leftTrigger.whileHeld(new CenterTargetRobot(driveTrain, lime));
    sController.rightBumper.whileActiveOnce(new SequentialCommandGroup(new SpinUp(shooter).withTimeout(1), new FeedToShooter(-0.4, shooter, hopper, indexer)));

    //sController.xButton.whenPressed(new IntakeDown(intake));

    // sController.leftBumper.whileHeld(new Intake(intake, indexer, 0.4));
    // sController.xButton.whileHeld(new Climb(climber));

     climberDOWN.whileHeld(new ClimbReverse(climber));
     climberUP.whileHeld(new Climb(climber));
     

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {

    return (new SequentialCommandGroup(new SpinUp(shooter).withTimeout(1), new AutonCommand(lime, shooter, hopper, indexer).withTimeout(12), new AutoDriveCommand(-0.35, -0.35, driveTrain).withTimeout(2)));//(new SequentialCommandGroup(
      //new ParallelCommandGroup(new ShootBall(-0.4, shooter, hopper, indexer), new Intake(intake, indexer, -0.4)).withTimeout(0.5),
      //new AutonCommand(driveTrain, lime, shooter, hopper, indexer).withTimeout(4),
      //new WaitCommand(2)));
  
  }

  /*public Command getAlternateAutonomousCommand() {
    // Create a voltage constraint to ensure we don't accelerate too fast
    var autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(kSChassis, kVChassis, kAChassis),
            kDriveKinematics,
            10);

    // Create config for trajectory
    TrajectoryConfig config =
        new TrajectoryConfig(0.5,
                             0.1)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(kDriveKinematics)
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

    // An example trajectory to follow.  All units in meters.
    Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        // Start at the origin facing the +X direction
        new Pose2d(0, 0, new Rotation2d(0)),
        // Pass through these two interior waypoints, making an 's' curve path
        List.of(
            new Translation2d(1, 1),
            new Translation2d(2, -1)
        ),
        // End 3 meters straight ahead of where we started, facing forward
        new Pose2d(3, 0, new Rotation2d(0)),
        // Pass config
        config
    );

    RamseteCommand ramseteCommand = new RamseteCommand(
        exampleTrajectory,
        driveTrain::getPose,
        new RamseteController(kRamseteB, kRamseteZ),
        new SimpleMotorFeedforward(kSChassis,
                                   kVChassis,
                                   kAChassis),
        kDriveKinematics,
        driveTrain::getWheelSpeeds,
        new PIDController(kPDriveVel, 0, 0),
        new PIDController(kPDriveVel, 0, 0),
        // RamseteCommand passes volts to the callback
        driveTrain::tankDriveVolts,
        driveTrain
    );

    // Run path following command, then stop at the end.
    return ramseteCommand.andThen(() -> driveTrain.tankDriveVolts(0, 0));
  }*/
}
