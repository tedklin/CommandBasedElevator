package org.usfirst.frc.team687.robot;

/**
 * Constants
 * 
 * @author tedfoodlin
 *
 */

public class Constants {
	
	public final static double kClk = 0.020;
	public final static double kElevatorMaxVelocity = 85;
	public final static double kElevatorMaxAccel = 0;
	public final static double kElevatorMaxDecel = 0;
	
	// feedforward terms
	public final static double kA = 0; // acceleration constant that tells your controller to add a little extra power to accelerate, and a little less to decelerate
	public final static double kV = 0.5 / kElevatorMaxVelocity; // a unit conversion between real-world velocities and motor power (tune this first)
	
	// feedback gains
	public final static double kP = 0; // can be really high because error is already small
	public final static double kI = 0; // add if needed (usually for end)
	public final static double kD = 0; // add if needed
	
	public final static double kSecondTapeMarkerPosition = 5627.0;
	
	public static double desiredPosition;
	
	public final static int updatePositionButton = 1;
	
}
