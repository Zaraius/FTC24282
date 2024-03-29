package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/*
 * This is a simple routine to test translational drive capabilities.
 */
@Config
@Autonomous(group = "drive")
public class shortAuto extends LinearOpMode {

    public static double frontDISTANCE = 65;
    public static double backDistance = 10;

    @Override
    public void runOpMode() throws InterruptedException {
//        DcMotor intakeMotor = hardwareMap.dcMotor.get("intake");

        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory trajectory1 = drive.trajectoryBuilder(new Pose2d())
                .forward(frontDISTANCE)
                .build();
        Trajectory trajectory2 = drive.trajectoryBuilder(new Pose2d())
                .back(backDistance)
                .build();


        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectory(trajectory1);
//        intakeMotor.setPower(-1);
        drive.followTrajectory(trajectory2);
        Thread.sleep(2000);
//        intakeMotor.setPower(0);

        Pose2d poseEstimate = drive.getPoseEstimate();
        telemetry.addData("finalX", poseEstimate.getX());
        telemetry.addData("finalY", poseEstimate.getY());
        telemetry.addData("finalHeading", poseEstimate.getHeading());
        telemetry.update();

        while (!isStopRequested() && opModeIsActive()) ;
    }
}