package frc.robot.subsystems;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.LimelightConstants.*;

public class LimeLight extends SubsystemBase
{

    /**
        * Handler for the Limelight on the robot:
        * Capabilites include:
            * Getter methods for tx, anything else can be added
            * Updates all values in limelight
    **/

    private NetworkTable table; //A Network Table is like a regular table. Stores data and is routed to another location.
    private NetworkTableEntry tx, ty, ta, tv;
    private double x, y, area;
    
    public LimeLight()
    {
        table = NetworkTableInstance.getDefault().getTable("limelight"); //gets table "entries" for the limelight
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tv = table.getEntry("tv");
        //pipeline = table.getEntry("SingleZoom");
    }
    public void update()
    {
        //System.out.println(table);
        
        
        //read values periodically
        x = tx.getDouble(2.0);
        y = ty.getDouble(2.0);
        //pipeline.setDouble(1.0)
        area = ta.getDouble(2.0);
        //post to smart dashboard periodically
        SmartDashboard.putNumber("LimelightX", x);
        SmartDashboard.putNumber("LimelightY", y);
        SmartDashboard.putNumber("LimelightArea", area);
    }
    
    public double getTx()
    {
        update();
        return tx.getDouble(0.0);
    }

    public double getTv()
    {
        return tv.getDouble(0.0);
    }
    public double getDistanceToTarget()
    {
        update();
        //d = (h2-h1) / tan(a1+a2)
        double distance = 
        (groundToTarget - groundToLimelight) /
        Math.tan(Math.toRadians(limelightAngleDegress) + Math.toRadians(y));
        return distance;
    }
}