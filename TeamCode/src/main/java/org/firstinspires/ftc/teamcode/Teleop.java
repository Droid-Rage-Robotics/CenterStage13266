package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp
public class Teleop extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor leftFront =(DcMotor) hardwareMap.get("leftFront");
        DcMotor rightFront =(DcMotor) hardwareMap.get("rightFront");
        DcMotor leftRear =(DcMotor) hardwareMap.get("leftRear");
        DcMotor rightRear =(DcMotor) hardwareMap.get("rightRear");
        DcMotor topFront =(DcMotor) hardwareMap.get("topMotor");

       double multiplier =1;
        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        topFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        int pos = topFront.getCurrentPosition();

        //int desiredPosition = 0; // The position (in ticks) that you want the motor to move to
        //arm.setTargetPosition(desiredPosition); // Tells the motor that the position it should go to is desiredPosition
        //arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);


                waitForStart();
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            if (gamepad1.right_bumper) {
                multiplier = 0.6;

            }else multiplier = 1;
            leftFront.setPower(frontLeftPower *multiplier );
            leftRear.setPower(backLeftPower *multiplier);
            rightFront.setPower(frontRightPower *multiplier);
            rightRear.setPower(backRightPower *multiplier);
            topFront.setPower(backRightPower *multiplier);

        }
    }
}
