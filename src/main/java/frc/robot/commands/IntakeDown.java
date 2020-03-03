package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intaker;

public class IntakeDown extends InstantCommand{

    private Intaker intake;

    public IntakeDown(Intaker intake) {
        this.intake = intake;
        addRequirements(intake);
        
    }

    @Override
    public void initialize() {
        intake.togglePistons();
    }


}