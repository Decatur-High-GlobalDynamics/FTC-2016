package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class MoronicAuto extends OpMode {


    DcMotor driveRight;
    DcMotor driveLeft;
    DcMotor driveLeft2;
    DcMotor driveRight2;
    /**
     * Constructor
     */
    public MoronicAuto() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
        driveLeft = hardwareMap.dcMotor.get("left_back");
        driveLeft2 = hardwareMap.dcMotor.get("left_front");
        driveLeft.setDirection(DcMotor.Direction.REVERSE);
        driveLeft2.setDirection(DcMotor.Direction.REVERSE);

        driveRight = hardwareMap.dcMotor.get("right_back");
        driveRight2 = hardwareMap.dcMotor.get("right_front");
        driveRight.setDirection(DcMotor.Direction.FORWARD);
        driveRight2.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void loop() {
        if (getRuntime()>30)
        {
            stop();
        }
        else if (getRuntime()>28)
        {
            driveRight.setPower(0.5);
            driveLeft.setPower(-0.5);
            driveLeft2.setPower(-0.5);
            driveRight2.setPower(0.5);
        }
    }

    @Override
    public void stop() {
        telemetry.addData("stop", "");
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