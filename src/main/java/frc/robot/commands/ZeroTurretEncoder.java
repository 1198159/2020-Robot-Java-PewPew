package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;
import static frc.robot.Constants.TurretConstants.*;

public class ZeroTurretEncoder extends CommandBase
{
    private boolean reachedHall = false;
    private boolean unreachedHall = false;
    private double leadingEdge = 0;
    private double trailingEdge = 0;
    private double hallCenter = 0;

    private Turret turret;
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
        if(turret.getHESensorVal() && !reachedHall)
        {
            leadingEdge = turret.getMotorPosition();
            reachedHall = true;
        }
        if(reachedHall && !turret.getHESensorVal())
        {
            trailingEdge = turret.getMotorPosition();
            unreachedHall = true;
        }
    }
    public void end()
    {
        hallCenter = (leadingEdge + trailingEdge) / 2;

        turret.setEncoderPosition(kHallOffset + (turret.getMotorPosition() - hallCenter));
        turret.ZeroTurret();

    }

    public boolean isFinished()
    {
        return reachedHall && unreachedHall;
    }
}