package com.github.adambots.powerup2018.intake;

import org.usfirst.frc.team245.robot.Actuators;
import org.usfirst.frc.team245.robot.Constants;
import org.usfirst.frc.team245.robot.Sensors;

import com.github.adambots.powerup2018.controller.Gamepad;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Intake {

	// initial conditions
	public static void init() {
		//carriageLiftPositionGoal = Actuators.getCarriageLiftMotorPosition();
		//Intake.setCarriageLiftPID(Constants.CARRIAGE_LIFT_P, Constants.CARRIAGE_LIFT_I, Constants.CARRIAGE_LIFT_D,
				//Constants.CARRIAGE_LIFT_TIMEOUT);
	}
	//resets encoder if limit switch is pressed
	public static void resetEncoderOnLimitSwitch() {
		if (Sensors.getLimitSwitchValue()) {
			Sensors.resetCarriageEncoder();
		}
	}
	// set the speed of the intake wheels
	public static void setIntakeWheelsSpeed(double speed) {
		//speed > 0 is left trigger
		//speed > 0 is left trigger
		Actuators.setLeftIntakeMotor(-speed);
		Actuators.setRightIntakeMotor(speed);
	}

	// set both first arm pneumatics
	private static void setArmsOpenPneumatic(DoubleSolenoid.Value value) {
		Actuators.setLeftArmOpenPneumatic(value);
		Actuators.setRightArmOpenPneumatic(value);
	}

	// set both second arm pneumatics
	private static void setArmsMidPneumatic(DoubleSolenoid.Value value) {
		Actuators.setLeftArmMidPneumatic(value);
		Actuators.setRightArmMidPneumatic(value);
	}

	// set arm pneumatic position to position from Constants class
	public static void setArmsPosition(int constantPosition) {
		switch (constantPosition) {
		case 1:
			Intake.setArmsOpenPneumatic(Constants.PNEUMATIC_REVERSE);
			Intake.setArmsMidPneumatic(Constants.PNEUMATIC_REVERSE);
			break;
		case 2:
			Intake.setArmsOpenPneumatic(Constants.PNEUMATIC_REVERSE);
			Intake.setArmsMidPneumatic(Constants.PNEUMATIC_FORWARD);
			break;
		case 3:
			Intake.setArmsOpenPneumatic(Constants.PNEUMATIC_FORWARD);
			Intake.setArmsMidPneumatic(Constants.PNEUMATIC_FORWARD);
			break;
		}
	}

	// toggle left pneumatic arm between 3 states
	// TODO: Check behavior of pressing button before teleop
	public static void armsPosition(boolean inButton, boolean midButton, boolean outButton) {
		if (inButton) {
			Intake.setArmsPosition(Constants.ARMS_IN);
		} else if (midButton) {
			Intake.setArmsPosition(Constants.ARMS_MID);
		} else if (outButton) {
			Intake.setArmsPosition(Constants.ARMS_OUT);
		}		
	}

	// toggle the carriage wheels
	//TODO: add override button
	public static void toggleCarriageWheels(double carriageWheelsSpeed) {
		boolean isPhotoEyeOpen = Sensors.getPhotoEyeValue();
		double speed;
		if (intakeButton && (isPhotoEyeOpen || overrideButton)) {
			speed = Constants.CARRIAGE_MOTOR_INTAKE_SPEED;
		}
		else if (intakeButton && !isPhotoEyeOpen && !overrideButton) {
			speed = Constants.STOP_MOTOR_SPEED;
		}
			speed = Constants.CARRIAGE_MOTOR_OUTTAKE_SPEED;
		} /*
		else if (carriageWheelsSpeed > 0 && isPhotoEyeBlocked){
			speed = Constants.STOP_MOTOR_SPEED;
		}*/ else {
			speed = Constants.STOP_MOTOR_SPEED;
		}
		Actuators.setLeftCarriageMotor(speed);
		Actuators.setRightCarriageMotor(speed);
		System.out.println("carriage wheel speed = [" + speed + "]");
	}
	// set carriage lift PID
	public static void setCarriageLiftPID(double p, double i, double d, int timeout) {
		Actuators.getCarriageLiftMotor().config_kP(0, p, timeout);
		Actuators.getCarriageLiftMotor().config_kI(0, i, timeout);
		Actuators.getCarriageLiftMotor().config_kD(0, d, timeout);
	}

	// control the carriage lift
	public static void setCarriageLiftSpeed(double speed) {
		int carriageLiftPosition = Sensors.getCarriageLiftPosition();
	//bottom is 0, top is ~67,000
		boolean isLimitSwitchPressed = Sensors.getLimitSwitchValue();
		
		if (isLimitSwitchPressed && speed <= 0) {
			Actuators.setCarriageLiftMotorSpeed(Constants.STOP_MOTOR_SPEED);
		}
		else if (carriageLiftPosition >= 67000 && speed >= 0) {
			Actuators.setCarriageLiftMotorSpeed(Constants.STOP_MOTOR_SPEED);
		}
		else {
			Actuators.setCarriageLiftMotorSpeed(speed);
		}
		
		if (speed < -0.05 && carriageLiftPosition < 15000) {
			Intake.setArmsPosition(Constants.ARMS_OUT);
		}


	}

}
