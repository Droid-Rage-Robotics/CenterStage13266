package org.firstinspires.ftc.teamcode.subsystem;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Lift extends Subsystem {
    public static class Constants {
        public static Hardware hardware;
        public static Controller controller;
        public static Position position;
        public static Speed speed;

        public static class Hardware {
            public static DcMotorSimple.Direction LEFT_DIRECTION = DcMotorSimple.Direction.REVERSE;
            public static DcMotorSimple.Direction RIGHT_DIRECTION = DcMotorSimple.Direction.FORWARD;
            public static double
                    RPM = 1150,
                    CPR = 145.090909;

        }
        public static class Controller {
            public static double
                    P = 2,
                    I = 0,
                    D = 0,
                    F = 0;
            public static int TOLERANCE = 8;
        }
        public static class Position {
            public static int
                    HIGH = 2300,
                    MID = 1611,
                    LOW = 959,
                    GROUND_JUNCTION = 200,
                    INITIAL = 0,
                    FLIPPED_CONE = 470,
                    MAX_POSITION = 3000,
                    MIN_POSITION = -70,
                    AUTO_5CONE = 250,
                    AUTO_4CONE = 190,
                    AUTO_3CONE = 100,
                    AUTO_2CONE = 35;
        }
        public static class Speed {
            public static double NORMAL        = 10;   // ill use this but im testing something
            public static int MANUAL_MOVE_SPEED = 6;

        }
    }
    private final DcMotorEx leftLift;
    private final DcMotorEx rightLift;
    private int motorPosition;
    private boolean isMovingManually;




    public Lift(@NonNull OpMode opMode) {
        super(opMode);
        leftLift = opMode.hardwareMap.get(DcMotorEx.class, "leftLift");
        rightLift = opMode.hardwareMap.get(DcMotorEx.class, "rightLift");

        rightLift.setDirection(Constants.Hardware.RIGHT_DIRECTION);
        leftLift.setDirection(Constants.Hardware.LEFT_DIRECTION);

        // TODO: Find out what these constants are by default
//        leftLift.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, new PIDFCoefficients(Constants.Controller.P, Constants.Controller.I, Constants.Controller.D, Constants.Controller.F));
//        leftLift.setTargetPositionTolerance(Constants.Controller.TOLERANCE);

        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftLift.setTargetPosition(0);
        rightLift.setTargetPosition(0);

        leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);



    }

    @Override
    protected void manualControl() {
        if (opMode.gamepad2.dpad_down) moveInitial();
        else if(opMode.gamepad2.dpad_left) moveLow();
        else if(opMode.gamepad2.dpad_right) moveMid();
        else if (opMode.gamepad2.dpad_up) moveHigh();

        if (opMode.gamepad2.right_stick_y < -0.2 || opMode.gamepad2.right_stick_y > 0.2) {
            moveMotors((int)(motorPosition + Constants.Speed.MANUAL_MOVE_SPEED * -opMode.gamepad2.right_stick_y));
            isMovingManually = true;
        } else {
            isMovingManually = false;
        }

        opMode.telemetry.addData("Slide position", motorPosition);
        opMode.telemetry.addData("Slide P", leftLift.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).p);
        opMode.telemetry.addData("Slide I", leftLift.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).i);
        opMode.telemetry.addData("Slide D", leftLift.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).d);
        opMode.telemetry.addData("Slide F", leftLift.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION).f);
        opMode.telemetry.addData("Slide Tolerance", leftLift.getTargetPositionTolerance());
    }

    public void moveMotors(int position) {
        if (position > Constants.Position.MAX_POSITION || position < Constants.Position.MIN_POSITION) return;

        this.motorPosition = position;

        leftLift.setTargetPosition(position);
        rightLift.setTargetPosition(position);

        leftLift.setPower(1);
        rightLift.setPower(1);
    }

    public void moveInitial() {
        if (isMovingManually) {
            Constants.Position.INITIAL = motorPosition;
        }
        moveMotors(Constants.Position.INITIAL);
    }

    public void moveHigh() {
        if (isMovingManually) {
            Constants.Position.HIGH = motorPosition;
        }
        moveMotors(Constants.Position.HIGH);
    }

    public void moveMid() {
        if (isMovingManually) {
            Constants.Position.MID = motorPosition;
        }
        moveMotors(Constants.Position.MID);
    }

    public void moveLow() {
        if (isMovingManually) {
            Constants.Position.LOW = motorPosition;
        }
        moveMotors(Constants.Position.LOW);
    }

    public void moveGroundJunction() {
        moveMotors(Constants.Position.GROUND_JUNCTION);
    }

    public void moveCone5() {
        moveMotors(Constants.Position.AUTO_5CONE);
    }

    public void moveCone4() {
        moveMotors(Constants.Position.AUTO_4CONE);
    }

    public void moveCone3() {
        moveMotors(Constants.Position.AUTO_3CONE);
    }

    public void moveCone2() {
        moveMotors(Constants.Position.AUTO_2CONE);
    }

    public void moveToPickUpFlippedCone() {
        moveMotors(Constants.Position.FLIPPED_CONE);
    }

    public boolean isDown() {
        return motorPosition <= Constants.Position.INITIAL;
    }
}