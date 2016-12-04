package org.usfirst.frc.team687.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team687.robot.subsystems.Elevator;

/**
 * BaeMax Elevator in Command Based
 * 
 * @author tedfoodlin
 * 
 */

public class Robot extends IterativeRobot {

	public static Elevator elevator;
	public static OI oi;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
		elevator = new Elevator();
    }
	
    public void disabledInit(){
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called once when operator control starts
     */
    public void teleopInit() {
    	SmartDashboard.putNumber("Current Position", elevator.getCurrentPosition());
    	SmartDashboard.putNumber("Setpoint", 0);
    	SmartDashboard.putNumber("Second Tape Position", Constants.kSecondTapeMarkerPosition);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    	SmartDashboard.putNumber("Current Position", elevator.getCurrentPosition());
    }
    
    public void testPeriodic() {
        LiveWindow.run();
    }
}
