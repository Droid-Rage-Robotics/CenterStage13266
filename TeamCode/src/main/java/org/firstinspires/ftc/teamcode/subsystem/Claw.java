package org.firstinspires.ftc.teamcode.subsystem;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {
    private final Servo oClaw;
    private final Gamepad gamepad2;

    private final Telemetry telemetry;
    public Claw(OpMode opMode) {
        HardwareMap hardwareMap = opMode.hardwareMap;
        this.gamepad2 = opMode.gamepad2;
        oClaw = (Servo) hardwareMap.get("oClaw");
        telemetry = opMode.telemetry;

        oClaw.setDirection(Servo.Direction.FORWARD);
        oClaw.setPosition(0);
    }
    public void teleOp() {
        //TODO Change position left trigger outtake
        double leftStickY = gamepad2.left_stick_y;
        double oclawPos = oClaw.getPosition();

        telemetry.addData("oClawPosition", oclawPos);

        if ( leftStickY < 0.1 || leftStickY > -0.1) {

            oClaw.setPosition(leftStickY/500 + oclawPos);
        }

        if (gamepad2.left_bumper) oClaw.setPosition(0.2);

        if (gamepad2.right_bumper)oClaw.setPosition (0);
    }


}

