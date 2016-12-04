package org.usfirst.frc.team687.robot.utilities;

import java.util.ArrayList;

import org.usfirst.frc.team687.robot.Constants;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Trapezoidal motion profile generator
 * 
 * @author tedfoodlin
 *
 */

public class MotionProfileGenerator {
    
	private double maxVelocity;
	private double maxAccel;
	private double maxDecel;
	
	private ArrayList<Double> time_data = new ArrayList<Double>();
	private ArrayList<Double> velocity_data = new ArrayList<Double>();
	private ArrayList<Double> distance_data = new ArrayList<Double>();
	private ArrayList<Double> acceleration_data = new ArrayList<Double>();
	
    public MotionProfileGenerator(double vmax, double amax, double dmax) { 
    	maxVelocity = vmax;
    	maxAccel = amax;
    	maxDecel = dmax;
    }

	/**
	 * Generate a trapezoidal motion profile using basic kinematic equations
	 * 
	 * @param distance desired
	 */
	public void generateProfile(double distance) {
		double time;
		double x;
		double v = 0;
		
		SmartDashboard.putNumber("Calculated Desired Distance", distance);
		double accelTime = maxVelocity/maxAccel;
		SmartDashboard.putNumber("Calculated Acceleration Time", accelTime);
		double accelAndCruiseTime = distance/maxVelocity;
		SmartDashboard.putNumber("Calculated Acceleration + Cruise Time", accelAndCruiseTime);
		double decelTime = -maxVelocity/maxDecel;
		SmartDashboard.putNumber("Calculated Deceleration Time", decelTime);
		double end = accelAndCruiseTime + decelTime;
		SmartDashboard.putNumber("Calculated Expected End Time", end);
		
		boolean triangular = isTriangular(distance);
		SmartDashboard.putBoolean("Is triangular", triangular);
		for (time = 0; time < accelTime; time += Constants.kClk){
			x = (0.5 * maxAccel * Math.pow(time, (double)2));
			v = maxAccel * time;
			addData(time, v, x, maxAccel);
		}
		if (triangular == false){
			for (time = accelTime; time < accelAndCruiseTime; time += Constants.kClk){
				x = (0.5 * (Math.pow(maxVelocity, 2) / maxAccel)) + (maxVelocity * (time - (maxVelocity/maxAccel)));
				v = (maxVelocity);
				addData(time, v, x, 0);
			}
		}
		for (time = accelAndCruiseTime; time <= end; time += Constants.kClk){
			x = (double)(distance + 0.5 * maxDecel * Math.pow((time-end), 2));
			v = maxVelocity + maxDecel * (time - accelAndCruiseTime);
			if (v < 0.0001) {
				v = 0;
			}
			addData(time, v, x, maxDecel);
		}
		SmartDashboard.putNumber("Calculated Acutal End Time", time);
	}
	
	/**
	 * Add data to array lists
	 * 
	 * @param time index
	 * @param velocity
	 * @param distance
	 * @param acceleration
	 */
	private void addData(double time, double v, double x, double acceleration) {
		time_data.add(time);
		velocity_data.add(v);
		distance_data.add(x);
		acceleration_data.add(acceleration);
	}
	
	/**
	 * Check if the maximum velocity can actually be reached
	 * If the maximum velocity can't be reached, adjust it to the final velocity that it can reach with some tolerance
	 */
	private boolean isTriangular(double distance) {
		double mid = distance/2;
		double vFinal = Math.pow(2 * maxAccel * mid, 0.5);
		if (vFinal < maxVelocity){
			maxVelocity = vFinal - (maxVelocity/20);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param time index
	 * @return goal velocity
	 */
	public double readVelocity(double time) {
		return velocity_data.get((int)(time/Constants.kClk));
	}
	
	/**
	 * @param time index
	 * @return goal distance
	 */
	public double readDistance(double time) {
		return distance_data.get((int)(time/Constants.kClk));
	}
	
	/**
	 * @param time index
	 * @return goal acceleration
	 */
	public double readAcceleration(double time) {
		return acceleration_data.get((int)(time/Constants.kClk));
	}
}
