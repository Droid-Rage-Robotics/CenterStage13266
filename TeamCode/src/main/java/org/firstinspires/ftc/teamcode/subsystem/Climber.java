package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;



public class Climber {
    private final DcMotor climberMotor;
    private final DcMotor climbMotorSeries;
    private final Gamepad gamepad2;


    public Climber(OpMode opMode) {
       HardwareMap hardwareMap = opMode.hardwareMap;

        // Find a motor in the hardware map named "Arm Motor
        climberMotor = hardwareMap.dcMotor.get("climb motor");
        climbMotorSeries = hardwareMap.dcMotor.get("climber motor");
        climberMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        climbMotorSeries.setDirection(DcMotorSimple.Direction.REVERSE);
        climberMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        climbMotorSeries.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        // Reset the motor encoder so that it reads zero ticks
        climberMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        climbMotorSeries.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        climberMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        climbMotorSeries.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        gamepad2 = opMode.gamepad2;

    }


    void teleOp() {
        double rightstick = -gamepad2.right_stick_y;

        if (rightstick < 0.1 || rightstick > -0.1) {

            climberMotor.setPower(rightstick);
            climbMotorSeries.setPower(rightstick);
        }
        else {
            climberMotor.setPower(0);
            climbMotorSeries.setPower(0);
        }
    }
}

