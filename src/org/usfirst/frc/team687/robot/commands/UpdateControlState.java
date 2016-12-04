package org.usfirst.frc.team687.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team687.robot.Robot;

/**
 * Updates control state (open or closed loop control)
 * 
 * @author tedfoodlin
 *
 */

public class UpdateControlState extends Command {
	
	private boolean m_currentState;

    public UpdateControlState() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_currentState = SmartDashboard.getBoolean("Closed Loop");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.elevator.setClosedLoop(!m_currentState);
    	SmartDashboard.putBoolean("Closed Loop", !m_currentState);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.elevator.isClosedLoop() == !m_currentState;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
