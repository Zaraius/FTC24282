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
public class blueLongAuto extends LinearOpMode {

    public static double frontDISTANCE = 70;
    public static double leftDISTANCE = 60;
    public static double rightDistance = 50;
    public static double backDistance = 4;

    @Override
    public void runOpMode() throws InterruptedException {
//        DcMotor intakeMotor = hardwareMap.dcMotor.get("intake");
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory trajectory1 = drive.trajectoryBuilder(new Pose2d())
                .strafeLeft(frontDISTANCE)
                .build();

        Trajectory trajectory2 = drive.trajectoryBuilder(new Pose2d())
                .forward(leftDISTANCE)
                .build();

        Trajectory trajectory3 = drive.trajectoryBuilder(new Pose2d())
                .strafeRight(rightDistance)
                .build();
        Trajectory trajectory4 = drive.trajectoryBuilder(new Pose2d())
                .forward(leftDISTANCE)
                .build();
        Trajectory trajectory5 = drive.trajectoryBuilder(new Pose2d())
                .back(backDistance)
                .build();

        waitForStart();

        if (isStopRequested()) return;

        drive.followTrajectory(trajectory1);
        drive.followTrajectory(trajectory2);
        drive.followTrajectory(trajectory3);
        drive.followTrajectory(trajectory4);
//        intakeMotor.setPower(-1);
        drive.followTrajectory(trajectory5);
        Thread.sleep(1000);
//        intakeMotor.setPower(0);

        Pose2d poseEstimate = drive.getPoseEstimate();
        telemetry.addData("finalX", poseEstimate.getX());
        telemetry.addData("finalY", poseEstimate.getY());
        telemetry.addData("finalHeading", poseEstimate.getHeading());
        telemetry.update();

        while (!isStopRequested() && opModeIsActive()) ;
    }
}