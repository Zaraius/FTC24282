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
public class VisionAuto extends LinearOpMode {
    public static double DISTANCE = 60; // in

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor intakeMotor = hardwareMap.dcMotor.get("intake");
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        opencv vision = new opencv();
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Trajectory forward = drive.trajectoryBuilder(new Pose2d())
                .forward(20)
                .build();
        Trajectory back = drive.trajectoryBuilder(new Pose2d())
                .back(12)
                .build();
        Trajectory left = drive.trajectoryBuilder(new Pose2d() )
                .strafeLeft(25)
                .build();
        Trajectory shortback = drive.trajectoryBuilder(new Pose2d() )
                .back(5)
                .build();
        Trajectory longleft = drive.trajectoryBuilder(new Pose2d())
                .strafeLeft(50)
                .build();
        vision.initOpenCV(hardwareMap);
        waitForStart();

        telemetry.addData("cX", vision.getXDirection());
        if(vision.getDirection() == opencv.autoDirection.LEFT){
            telemetry.addData("where we going?", "LEFT");
            drive.followTrajectory(back);
            drive.followTrajectory(left);
            intakeMotor.setPower(-1);
        } else if (vision.getDirection() == opencv.autoDirection.RIGHT) {
            telemetry.addData("where we going?","RIGHT" );
            drive.followTrajectory(left);
        } else if (vision.getDirection() == opencv.autoDirection.MIDDLE) {
            telemetry.addData("where we going?","MiDDlE");
            drive.followTrajectory(shortback);
            drive.followTrajectory(longleft);
        }




        if (isStopRequested()) return;


        Pose2d poseEstimate = drive.getPoseEstimate();
        telemetry.addData("finalX", poseEstimate.getX());
        telemetry.addData("finalY", poseEstimate.getY());
        telemetry.addData("finalHeading", poseEstimate.getHeading());
        telemetry.update();



        while (!isStopRequested() && opModeIsActive()) ;
    }
}
