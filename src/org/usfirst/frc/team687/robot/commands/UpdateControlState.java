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
	private boolean m_newState;

    public UpdateControlState() {
    }

    /**
     * Get the current state and update new state
     */
    protected void initialize() {
    	m_currentState = SmartDashboard.getBoolean("Closed Loop");
    	m_newState = !m_currentState;
    }

    /**
     * Update state
     */
    protected void execute() {
    	Robot.elevator.setClosedLoop(m_newState);
    	SmartDashboard.putBoolean("Closed Loop", m_newState);
    }

    /**
     * Is finished when update has been confirmed
     */
    protected boolean isFinished() {
        return Robot.elevator.isClosedLoop() == m_newState;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
