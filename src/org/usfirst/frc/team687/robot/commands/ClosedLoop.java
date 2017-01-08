package org.usfirst.frc.team687.robot.commands;

import org.usfirst.frc.team687.robot.Constants;
import org.usfirst.frc.team687.robot.Robot;
import org.usfirst.frc.team687.robot.utilities.MotionProfileGenerator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Closed loop control with feedforward from motion profiles and feedback
 * 
 * @author tedlin
 *
 */

public class ClosedLoop extends Command {
	
	private double m_startPos;
	private double m_setpoint;
	private double m_distance;
	
	private double m_startingTime;
	
	private MotionProfileGenerator m_motionProfileGenerator;
	private double m_vmax = Constants.kElevatorMaxVelocity;
	private double m_amax = Constants.kElevatorMaxAccel;
	private double m_dmax = Constants.kElevatorMaxDecel;
	
	private double m_error;
	private double m_lastError;
	
    public ClosedLoop(){
        // Use requires() here to declare subsystem dependencies
        requires(Robot.elevator);
    }

    /**
     * Set starting position, starting time, and generate motion profile
     */
    protected void initialize() {
    	m_startPos = Robot.elevator.getCurrentPosition();
    	m_setpoint = SmartDashboard.getNumber("Setpoint");
    	m_distance = m_setpoint - m_startPos;
    	
    	m_motionProfileGenerator = new MotionProfileGenerator(m_vmax, m_amax, m_dmax);
    	m_motionProfileGenerator.generateProfile(m_distance);
    	
    	m_startingTime = Timer.getFPGATimestamp();
    	m_error = 0;
    }

    /**
     * Calculate power based on motion profile
     */
    protected void execute() {
    	m_lastError = m_error;
    	double currentTime = Timer.getFPGATimestamp() - m_startingTime;
		double goalVelocity = m_motionProfileGenerator.readVelocity(currentTime);
		double goalAcceleration = m_motionProfileGenerator.readAcceleration(currentTime);
		double setpointPos = m_motionProfileGenerator.readDistance(currentTime);
		double actualPos = Robot.elevator.getCurrentPosition();
		m_error = setpointPos - actualPos;
		double pow = Constants.kP * m_error 
				+ Constants.kD * ((m_error - m_lastError) / currentTime - goalVelocity)
				+ Constants.kV * goalVelocity 
				+ Constants.kA * goalAcceleration;
		Robot.elevator.setElevatorPower(pow);
    }

    /**
     * Is finished when control changes to open-loop or when the elevator reaches setpoint (with tolerance)
     */
    protected boolean isFinished() {
    	if (!SmartDashboard.getBoolean("Closed Loop") || Math.abs(m_setpoint - Robot.elevator.getCurrentPosition()) < 5) {
    		return true;
    	} else {
    		return false;
    	}
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.elevator.setElevatorPower(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
}