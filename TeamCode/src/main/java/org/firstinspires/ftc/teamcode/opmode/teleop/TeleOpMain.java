package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.command.FlipConeWithGamepad;
import org.firstinspires.ftc.teamcode.command.JunctionDetection;
import org.firstinspires.ftc.teamcode.command.OptimizeStuff;
import org.firstinspires.ftc.teamcode.command.Sensor;
import org.firstinspires.ftc.teamcode.command.Sussy;
import org.firstinspires.ftc.teamcode.subsystem.ConeFlipper;
import org.firstinspires.ftc.teamcode.subsystem.Drive;
import org.firstinspires.ftc.teamcode.subsystem.Gripper;
import org.firstinspires.ftc.teamcode.subsystem.Lift;
import org.firstinspires.ftc.teamcode.util.PoseStorage;

@TeleOp
public class TeleOpMain extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Lift lift = new Lift(this);
        Gripper gripper = new Gripper(this);
        ConeFlipper coneFlipper = new ConeFlipper(this);
        // TODO: get this to false
        Drive drive = new Drive(this,true);

        new OptimizeStuff(this);
        new Sussy(this);

        Sensor sensor = new Sensor(this, gripper, lift);
        //JunctionDetection junction = new JunctionDetection(this, gripper, lift);
        FlipConeWithGamepad flipConeWithGamepad = new FlipConeWithGamepad(this, coneFlipper, lift, gripper);

        drive.setPoseEstimate(PoseStorage.currentPose);
        gripper.open();
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            lift.runIteratively();
            sensor.runIteratively();
            //junction.runIteratively();
            gripper.runIteratively();

            flipConeWithGamepad.runIteratively();

            drive.runIteratively();
            telemetry.update();
        }

    }
}
