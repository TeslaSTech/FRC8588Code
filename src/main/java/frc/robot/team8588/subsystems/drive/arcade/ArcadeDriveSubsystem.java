/*
Name: Abigail Prowse
Program: ArcadeDriveSubsytem
This is the starting of the ArcadeDriveSubsytem.  Does not make complete sense yet and definitely needs work
Check out ArcadeDriveChassis and other subsystems.
Date: 3/29/2021
 */

// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.team8588.subsystems.drive.arcade;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.team8588.subsystems.drive.DriveDirection;
import frc.robot.team8588.subsystems.drive.DriveSubsystem;


public class ArcadeDriveSubsystem implements DriveSubsystem {
    private ArcadeDriveChassis chassis;
    private ArcadeDriveInputs inputs;

    public ArcadeDriveSubsystem(ArcadeDriveChassis chassis, ArcadeDriveInputs inputs) {
        this.chassis = chassis;
        this.inputs = inputs;
    }

    private void setMotors(double left, double right) {
        chassis.getBackLeft().set(left);
        chassis.getFrontLeft().set(left);

        chassis.getBackRight().set(-(right));
        chassis.getFrontRight().set(-(right));
    }

    @Override
    public void drive(double power, DriveDirection direction) {
        switch (direction)
        {
            case FORWARD:
                setMotors(-power, -power);
                break;

            case BACKWARD:
                setMotors(power, power);
                break;

            case RIGHT:
                setMotors(-power, power);
                break;

            case LEFT:
                setMotors(power, -power);
                break;
        }
    }

    @Override
    public void drive(double leftX, double leftY, double rightX, double rightY) {
        /*
        double direction;
        // calculates the direction off of left joystick values
        if ((Math.abs(leftX) < 0.1 && Math.abs(leftY) < 0.1) {
            // center
            direction = 0;
            // sets direction to 0, meaning there is no movement.
        }
        else if (leftX > 0 && leftY > 0) {
            // first quadrant
            direction = Math.atan(leftY/leftX);
            // uses the arctangent of the triangle made by the x and y values to find the angle to drive in.
        }
        else if (leftX < 0 && leftY > 0) {
            direction = Math.atan(leftY/leftX) + Math.PI * 3 / 2;
            // uses the arctangent of the triangle made by the x and y values to find the angle to drive in. + 270 degrees.
        }
        else if (leftX < 0 && leftY < 0) {
            // third quadrant
            direction = Math.atan(leftY/leftX) + Math.PI;
            // uses the arctangent of the triangle made by the x and y values to find the angle to drive in. + 180 degrees.
        }
        else if (leftX > 0 && leftY < 0) {
            direction = Math.atan(leftY/leftX) + Math.PI / 2;
            // uses the arctangent of the triangle made by the x and y values to find the angle to drive in. + 90 degrees
        }

        double speed = Math.sqrt(leftX * leftX + leftY * leftY);
        // calculates the speed by the hypotenuse of the said triangle.
        */

//        chassis.getLeft.setLeft(leftX + leftY);
//        chassis.getRight.setRight(leftX - leftY);
    }

    @Override
    public void setPowers() {
        double forward = inputs.yStick.get();
        double turn = inputs.xStick.get();
        double lTrig = inputs.lTrigger.get();
        double rTrig = inputs.rTrigger.get();

        double triggerThreshold = 0.3;

        // Vary power limits based on state of a trigger 
        if (lTrig > triggerThreshold) { // 25% power
            forward /= 4;
            turn /= 4;
            SmartDashboard.putNumber("Power", 25);
        } else if (rTrig > triggerThreshold) { // 100% power
            forward /= 1;
            turn /= 1;
            SmartDashboard.putNumber("Power", 100);
        } else { // default
            forward /= 2;
            turn /= 2;
            SmartDashboard.putNumber("Power", 50);
        }

        //forward = forward * forward * forward;
        //turn = turn * turn * turn;
        // curve movement

        chassis.getBackLeft().set(forward - turn);
        chassis.getFrontLeft().set(forward - turn);

        chassis.getBackRight().set(-(forward + turn));
        chassis.getFrontRight().set(-(forward + turn));

        /* SmartDashboard.putNumber("Front Right ESC: ", chassis.getFrontRight().getMotorTemperature());
        SmartDashboard.putNumber("Front Left ESC: ", chassis.getFrontLeft().getMotorTemperature());
        SmartDashboard.putNumber("Back Right ESC: ", chassis.getBackRight().getMotorTemperature());
        SmartDashboard.putNumber("Back Left ESC: ", chassis.getBackLeft().getMotorTemperature()); */

        SmartDashboard.putNumber("Total Current Draw: ", returnCurrentDraw());
    }

    // method needs to take in x and y of one joystick.  Also needs to take in power.
    // then needs to change direction according to that.

    // Return the power draw of all four motors.
    public double returnCurrentDraw() {
        return chassis.getBackLeft().getOutputCurrent() + chassis.getBackRight().getOutputCurrent() + chassis.getFrontLeft().getOutputCurrent() + chassis.getFrontRight().getOutputCurrent();
    }
}

