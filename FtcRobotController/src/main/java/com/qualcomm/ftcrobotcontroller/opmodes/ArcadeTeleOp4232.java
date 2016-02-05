package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


public class ArcadeTeleOp4232 extends OpMode {
//this is just in Ken's branch

    DcMotor driveRight;
    DcMotor driveLeft;
    DcMotor driveLeft2;
    DcMotor driveRight2;
    DcMotor manLeft;
    DcMotor manRight;
    int i = 0;
    int x = 0;

    final static double LEFT_ARM_MIN = 0;
    final static double LEFT_ARM_MAX = .8;
    final static double RIGHT_ARM_MIN = 0;
    final static double RIGHT_ARM_MAX = .8;
    final static double ARM_DELAY = 0.250; //ms to wait for next arm press

    // position of the arm servo.
    double leftArmPosition;
    double rightArmPosition;

    double leftArmWaitUntil = 0;
    double rightArmWaitUntil = 0;

    // amount to change the arm servo position.
    double armDelta = 0.1;

    /*
    float currentLeftPower;
    float currentRightPower;
    float powerStep;
    long startTime;
    long currentTime;
    boolean lastGamePad1Y;
    Steerer currentSteerer;
    */

    Servo servoMRight;
    Servo servoMLeft;
    Servo servoFLeft;
    Servo servoFRight;


    /**
     * Constructor
     */
    public ArcadeTeleOp4232() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {


		/*
         * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */

        driveLeft = hardwareMap.dcMotor.get("left_back");
        driveLeft2 = hardwareMap.dcMotor.get("left_front");
        driveLeft.setDirection(DcMotor.Direction.REVERSE);
        driveLeft2.setDirection(DcMotor.Direction.REVERSE);

        driveRight = hardwareMap.dcMotor.get("right_back");
        driveRight2 = hardwareMap.dcMotor.get("right_front");
        driveRight.setDirection(DcMotor.Direction.FORWARD);
        driveRight2.setDirection(DcMotor.Direction.FORWARD);
        //resetArmPositions();
    }

    private void resetArmPositions() {
    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {


        double leftThrottle = gamepad1.left_stick_y;
        double rightThrottle = gamepad1.right_stick_y;


        // clip the right/left values so that the values never exceed +/- 1
        leftThrottle = Range.clip(leftThrottle, -1, 1);
        rightThrottle = Range.clip(rightThrottle, -1, 1);

        //scale the throttle so it's easier to control at low speeds
        //this is UNTESTED on the actual robot as of 12/4/2015, so just comment out if it's acting up
        leftThrottle = scaleInput(leftThrottle);
        rightThrottle = scaleInput(rightThrottle);

        driveRight.setPower(rightThrottle);
        driveRight2.setPower(rightThrottle);
        driveLeft.setPower(leftThrottle);
        driveLeft2.setPower(leftThrottle);


        boolean moveLeftArm = false;
        boolean moveRightArm = false;


/*
         * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */
        //telemetry.addData("Text", "*** Robot Data***");
        //telemetry.addData("arm", "arm:  " + String.format("%.2f", armPosition));
        //telemetry.addData("claw", "claw:  " + String.format("%.2f", clawPosition));
        telemetry.addData("left pwr", "left  pwr: " + String.format("%.2f", leftThrottle));
        telemetry.addData("right pwr", "right pwr: " + String.format("%.2f", rightThrottle));
        telemetry.addData("left arm", leftArmPosition);
        telemetry.addData("right arm", rightArmPosition);
    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {
        resetArmPositions();
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

