package com.github.adambots.powerup2018.drive;

import org.usfirst.frc.team245.robot.Actuators;

import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class Drive {

	private static MecanumDrive robotDrive;

	// precondition: run Actuators.init()
	public static void init() {
		robotDrive = new MecanumDrive(Actuators.getLeftFrontMotor(), Actuators.getLeftRearMotor(),
				Actuators.getRightFrontMotor(), Actuators.getRightRearMotor());
	}

	// driving with Mecanum (input squared for smoother control)
	public static void mecDrive(double rightSpeed, double forwardSpeed, double zRotation) {
		robotDrive.driveCartesian(Actuators.sgnPow(rightSpeed, 2), Actuators.sgnPow(forwardSpeed, 2),
				Actuators.sgnPow(zRotation, 2));
	}

	// driving with field-centric Mecanum (input squared for smoother control)
	public static void mecDrive(double rightSpeed, double forwardSpeed, double zRotation, double gyroAngle) {
		robotDrive.driveCartesian(Actuators.sgnPow(rightSpeed, 2), Actuators.sgnPow(forwardSpeed, 2),
				Actuators.sgnPow(zRotation, 2), gyroAngle);
	}
	
	public static void autonDrive(double rightSpeed,double forwardSpeed, double zRotation, double AValue, double BValue, double elevatorValue){
		
		//*note* Cartesian was a possibility for auton but the teleop drive method is not cartesian, please resolve
		mecDrive(rightSpeed, forwardSpeed, zRotation);
		Actuators.setCarriageLiftMotor(elevatorValue);
		
		//Intake and out take respectively
		if(AValue != 0){
		Actuators.setLeftCarriageMotor(- AValue);
		Actuators.setRightCarriageMotor(AValue);
		}
		else{
			
		}
		if(BValue != 0){
			Actuators.setLeftCarriageMotor(BValue);
			Actuators.setRightCarriageMotor(- BValue);
	}
		else{
			}
		}
}
