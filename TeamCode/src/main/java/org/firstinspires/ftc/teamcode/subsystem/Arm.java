package org.firstinspires.ftc.teamcode.subsystem;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Arm  {
   private final DcMotor armMotor;
    private final Gamepad gamepad2;
    private final Telemetry telemetry;
    private final Servo tClaw;


    // Position of the arm when it's lifted
    private static final int armUpPosition = 700;
    // Position of the arm when it's down
    private static final int armDownPosition = 0;
    //the conversion rate between motor steps and servo rotation
    private static final double conversionRate = 0.0006857143;
    //the relative rotation of the claw
    private double tClawOffset = 0.1;


    //creates a timer to use for delaying things without pausing the whole program
    private final ElapsedTime delayTimer = new ElapsedTime();

    private double delayTime = 0;

    State state = State.DOWN;
    public Arm(OpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;

        //initialize the claw
        tClaw = (Servo) hardwareMap.get("tClaw");
        tClaw.setDirection(Servo.Direction.FORWARD);
        tClaw.setPosition(0 * conversionRate + tClawOffset);


        // Find a motor in the hardware map named "Arm Motor
        telemetry = opMode.telemetry;
        armMotor = hardwareMap.dcMotor.get("Arm Motor");


        // Reset the motor encoder so that it reads zero ticks
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Sets the starting position of the arm to the down position
        armMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armMotor.setTargetPosition(armDownPosition);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(0.5);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

       // waitForStart();
        gamepad2 = opMode.gamepad2;


    }

    private enum State {
        DOWN,
        MOVING_UP,
        UP,
    }


    void teleOp(){
        double time = delayTimer.seconds();

        double tClawPosition = tClaw.getPosition();
        double armMotorPosition = armMotor.getCurrentPosition();
        telemetry.addData("tClaw", tClawPosition);
        telemetry.addData("armMotor", armMotorPosition);
        telemetry.addData("time", time);

        tClaw.setPosition(armMotorPosition * conversionRate + tClawOffset);

        switch (state) {
            case DOWN:
                if (gamepad2.y) {
                    armMotor.setTargetPosition(armUpPosition);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armMotor.setPower(0.25);
                    delayTime = 2;
                    delayTimer.reset();
                    state = State.MOVING_UP;
                }
                break;
            case MOVING_UP:
                if (time > delayTime) {
                    tClawOffset = -0.1;
                    state = State.UP;
                }
                break;
            case UP:
                if (gamepad2.x) {
                    tClawOffset = 0.1;
                    armMotor.setTargetPosition(armDownPosition);
                    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    armMotor.setPower(0.5);
                    state = State.DOWN;
                }
                break;


        }


        // Get the current position of the armMotor
        double position = armMotor.getCurrentPosition();

        // Get the target position of the armMotor
        double desiredPosition = armMotor.getTargetPosition();

        // Show the position of the armMotor on telemetry
        telemetry.addData("Encoder Position", position);

        // Show the target position of the armMotor on telemetry
        telemetry.addData("Desired Position", desiredPosition);

    }

    void setArmPosition(int position, double power) {

    }

}