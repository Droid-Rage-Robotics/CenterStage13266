package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class FieldCentricMecanumTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Drive drive = new Drive(this);
        Arm arm = new Arm(this);
        Claw claw = new Claw(this);
        Climber climber= new Climber(this);
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            drive.teleOp();
            arm.teleOp();
            claw.teleOp();
            climber.teleOp();
            telemetry.update();
        }
    }
}