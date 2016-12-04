package org.usfirst.frc.team687.robot;

import org.usfirst.frc.team687.robot.commands.ClosedLoop;
import org.usfirst.frc.team687.robot.commands.UpdateControlState;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * @author tedfoodlin
 * 
 */

public class OI {
	
	public Joystick articJoy;
	public JoystickButton closedLoopControl_1;
	public JoystickButton updateControlState_2;

	/**
	 * Initializes the joystick objects 
	 */
	public OI()
	{
		articJoy = new Joystick(RobotMap.JoystickPort);
		
		closedLoopControl_1 = new JoystickButton(articJoy, 1);
		closedLoopControl_1.whenPressed(new ClosedLoop());
		updateControlState_2 = new JoystickButton(articJoy, 2);
		updateControlState_2.toggleWhenPressed(new UpdateControlState());
	}
	
	/**
	 * @return artic joystick y value
	 */
	public double getArticJoystickY() 
	{
		double input = articJoy.getY();
		if(Math.abs(input) < 0.05)
			return 0.0;
		else
			return input;
	}
}

