package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intaker;

public class FireSolenoids extends CommandBase {

    private Intaker intake;

    public FireSolenoids(Intaker intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.intakePistonsOut();
    }

    @Override
    public void execute() {
        intake.intakePistonsOut();
        System.out.println(intake.intakePistonsGet());
    }

    @Override
    public void end(boolean interrupted) {
        //intake.intakePistonsIn();
    }

}