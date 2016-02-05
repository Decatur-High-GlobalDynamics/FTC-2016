package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class MoronicAuto extends OpMode {


    DcMotor right;
    DcMotor left;
    DcMotor winch;
    DcMotor tape;
    Servo tapeAngle;
    Servo flipper;
    int winchCount=0;
    int i = 0;
    int x = 0;
    double tapeMatchWinchSpeed=0.213;

    public MoronicAuto() {

    }

    @Override
    public void init() {

        right = hardwareMap.dcMotor.get("left_motor");
        left = hardwareMap.dcMotor.get("right_motor");
        winch = hardwareMap.dcMotor.get("winch_motor");
        tape = hardwareMap.dcMotor.get("tape_motor");
        tapeAngle=hardwareMap.servo.get("tapeAngle");
        flipper=hardwareMap.servo.get("flipper");
        left.setDirection(DcMotor.Direction.REVERSE);
        tape.setDirection(DcMotor.Direction.REVERSE);
        flipper.setPosition(1);
        tapeAngle.setPosition(0.1);
    }

    @Override
    public void loop() {
        if (getRuntime()>30)
        {
            stop();
        }
        else
        {
            if (getRuntime()>28)
            {
                flipper.setPosition(0.5);
            }
/*
            if (getRuntime() > 10 && getRuntime() < 15)
            {
                left.setPower(1);
                right.setPower(1);
            }
            else
            {
                left.setPower(0);
                right.setPower(0);
            }
            if (getRuntime() > 15 && getRuntime() < 17)
            {
                flipper.setPosition(0);
            }
            if (getRuntime()>17)
            {
                flipper.setPosition(1);
            }
*/
        }
    }

    @Override
    public void stop() {

    }

    /*
      * This method scales the joystick input so for low joystick values, the
      * scaled value is less than linear.  This is to make it easier to drive
      * the robot more precisely at slower speeds.
 */
    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;


    }
}