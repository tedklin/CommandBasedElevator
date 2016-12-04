package org.usfirst.frc.team687.robot.subsystems;

import org.usfirst.frc.team687.robot.utilities.NerdyMath;
import org.usfirst.frc.team687.robot.RobotMap;
import org.usfirst.frc.team687.robot.commands.OpenLoop;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Elevator subsystem
 * 
 * @author tedfoodlin
 * 
 */

public class Elevator extends Subsystem {
   
    private Encoder m_encoder;
    private CANTalon m_elevator;
	
	private boolean closedLoop;
	
	public Elevator() {
    	m_encoder = new Encoder(RobotMap.EncoderPort1, RobotMap.EncoderPort2);
    	m_elevator = new CANTalon(RobotMap.ElevatorPort);
    	m_elevator.changeControlMode(TalonControlMode.PercentVbus);
    	
    	closedLoop = false;
	}
	
    public void initDefaultCommand() {
	    setDefaultCommand(new OpenLoop());
    }
    
    /**
     * @param true for elevator to enter closed-loop control
     */
    public void setClosedLoop(boolean state) {
    	closedLoop = state;
    }
    
    /**
     * 
     * @return true if elevator is in closed-loop control
     */
    public boolean isClosedLoop() {
    	return closedLoop;
    }
    
    /**
     * @return raw unscaled encoder value
     */
    public double getCurrentPosition() {
    	return m_encoder.getRaw();
    }
    
    /**
     * @return velocity in ticks / 10 ms (clock speed)
     */
    public double getCurrentVelocity() {
    	return NerdyMath.scaleVelocity(m_encoder.getRate());
    }
    
    /**
     * Set elevator motor power to a value (between -1.0 to 1.0 in PercentVBus TalonControlMode)
     * 
     * @param power
     */
    public void setElevatorPower(double pow) {
    	m_elevator.set(pow);
    }
}
