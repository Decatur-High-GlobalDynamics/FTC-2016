package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.*;
import android.content.Context;

public class PlaybackAuto extends OpMode {


    DcMotor right;
    DcMotor left;
    Servo flipper;
    int i = 0;
    ArrayList<double[]> values =new ArrayList<double[]>();
    public PlaybackAuto()
    {

    }

    @Override
    public void init()
    {
        right = hardwareMap.dcMotor.get("left_motor");
        left = hardwareMap.dcMotor.get("right_motor");
        flipper=hardwareMap.servo.get("flipper");
        left.setDirection(DcMotor.Direction.REVERSE);
        flipper.setPosition(1);
        String fileString = readFileAsString();
        String[] lineArray = fileString.split("/n");
        for (int i=0; i<lineArray.length; i++)
        {
            String[] valStrings = lineArray[i].split(",");
            double[] vals = new double[4];
            for (int j=0; j<valStrings.length; j++)
            {
                vals[i]=Double.parseDouble(valStrings[i]);
            }
            values.add(vals);
        }
    }

    @Override
    public void loop()
    {
        double[] actuators=values.get(i);
        if (getRuntime()>=actuators[0])
        {
            teleop(actuators[1], actuators[2], actuators[3]);
            i++;
        }
    }

    public void teleop(double leftVal, double rightVal, double flipVal)
    {
        left.setPower(leftVal);
        right.setPower(rightVal);
        flipper.setPosition(flipVal);
    }

    @Override
    public void stop()
    {

    }
    double smooth(double input)
    {
        return Math.pow(input, 3);
    }

    public String readFileAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(new File("/FIRST/AutoValues.txt")));
            while ((line = in.readLine()) != null) stringBuilder.append(line);

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        return stringBuilder.toString();
    }
}