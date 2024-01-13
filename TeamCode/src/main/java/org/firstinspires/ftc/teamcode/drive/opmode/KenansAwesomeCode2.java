
package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class KenansAwesomeCode2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backRight");
//        DcMotor intakeMotor = hardwareMap.dcMotor.get("intake");
//        DcMotor armMotor = hardwareMap.dcMotor.get("arm");
        Servo planeServo = hardwareMap.get(Servo.class, "plane");
        Servo hang1Servo = hardwareMap.get(Servo.class, "hang1");
        Servo hang2Servo = hardwareMap.get(Servo.class, "hang2");


        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        hang1Servo.setDirection(Servo.Direction.FORWARD);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            telemetry.update();
            double y = -gamepad1.left_stick_y * gamepad1.left_stick_y * gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * gamepad1.left_stick_x * gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x * gamepad1.right_stick_x * gamepad1.right_stick_x;
            boolean rb = gamepad1.right_bumper;
            boolean lb = gamepad1.left_bumper;
            boolean up = gamepad1.dpad_up;
            boolean down = gamepad1.dpad_down;
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
//            if (rb) {
//                intakeMotor.setPower(-1);
//            } else if (lb) {
//                intakeMotor.setPower(1);
//            } else {
//                intakeMotor.setPower(0);
//            }

//            if (up) {
//                armMotor.setPower(0.5);
//                // telemetry.
//            } else if (down) {
//                armMotor.setPower(-0.5);
//            } else {
//                armMotor.setPower(0);
//            }
//
            if (gamepad2.a) {
                telemetry.addData("YO", "pressing Y");
                planeServo.setPosition(0.65);
            } else if (gamepad2.y) {
                planeServo.setPosition(1);
            }
            if (gamepad2.dpad_down) {
                hang1Servo.setPosition(1);
                hang2Servo.setPosition(0.0);
            } else if (gamepad2.dpad_up) {
                hang1Servo.setPosition(0);
                hang2Servo.setPosition(1);
            }
        }
    }
}
