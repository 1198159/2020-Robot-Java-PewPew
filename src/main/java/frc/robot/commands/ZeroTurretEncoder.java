package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;
import static frc.robot.Constants.TurretConstants.*;

public class ZeroTurretEncoder extends CommandBase
{
    public boolean reachedHall = false;
    public boolean unreachedHall = false;
    public double leadingEdge = 0;
    public double trailingEdge = 0;
    public double hallCenter = 0;

    private final Turret turret;
    public ZeroTurretEncoder(Turret turret)
    {
        this.turret = turret;
        addRequirements(turret);
    }

    public void initialize()
    {
        turret.setSpeed(0.01);
    }

    public void execute()
    {
        if(turret.HallEffect.get() && !reachedHall)
        {
            leadingEdge = turret.getMotorPosition();
            reachedHall = true;
        }
        if(reachedHall && !turret.HallEffect.get())
        {
            trailingEdge = turret.getMotorPosition();
            unreachedHall = true;
        }
    }
    public void end()
    {
        hallCenter = (leadingEdge + trailingEdge) / 2;

        turret.encoder.setPosition(kHallOffset + (turret.getMotorPosition() - hallCenter));
        turret.ZeroTurret();

    }

    public boolean isFinished()
    {
        return reachedHall && unreachedHall;
    }
}