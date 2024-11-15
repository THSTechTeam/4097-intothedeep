package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Auto_Right")
public class autoKeishiR24 extends LinearOpMode
{
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    final private static double SPEED = 0.5;
    private DcMotor armMotor;
    private CRServo intake;
    private Servo wrist;
    private ElapsedTime elapsedTime;

    final double armTicksPerDegree = 28 * (250047.0 / 4913.0) * (100.0 / 20.0) * (1 / 360.0);

    // Predetermined positions for arm
    final double armCollapsedIntoRobot = 0;
    final double armCollect = 250 * armTicksPerDegree;
    final double armClearBarrier = 230 * armTicksPerDegree;
    final double armScoreSpecimen = 160 * armTicksPerDegree;
    final double armScoreSampleInLow = 160 * armTicksPerDegree;
    final double armAttachHangingHook = 120 * armTicksPerDegree;
    final double armWinchRobot = 15 * armTicksPerDegree;

    // Value for intrake servo
    final double intakeCollect = -1.0;
    final double intakeOff = 0.0;
    final double intakeDeposit = 0.5;

    // Value for wrist servo
    final double wristFoldedIn = 0.9;
    final double wristFoldedOut  = 0.55;

    double armPosition = (int)armCollapsedIntoRobot;

    @Override
    public void runOpMode() throws InterruptedException
    {
        frontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        frontRight = hardwareMap.dcMotor.get("motorFrontRight");
        backLeft = hardwareMap.dcMotor.get("motorBackLeft");
        backRight = hardwareMap.dcMotor.get("motorBackRight");
        armMotor = hardwareMap.dcMotor.get("arm");
        intake = hardwareMap.crservo.get("intake");
        wrist = hardwareMap.servo.get("wrist");
        elapsedTime = new ElapsedTime();

        // Set left motors going same direction as right motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Allow brake function at zero power
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Setting intake and wrist at beginning position
        intake.setPower(intakeOff);
        wrist.setPosition(wristFoldedIn);

        // Update telemetry
        telemetry.addLine("Robot Ready.");
        telemetry.update();

        // The Robots final pathing
        armStart();
        moveForward(0.05);
        moveRight(2.1);



    }

    private void armStart() { armPosition = armWinchRobot; }
    private void armUp() { armPosition = armAttachHangingHook; }
    private void armDown() { armPosition = armScoreSpecimen; }
    private void moveForward(double timeout)
    {
        elapsedTime.reset();
        frontLeft.setPower(SPEED);
        frontRight.setPower(SPEED);
        backLeft.setPower(SPEED);
        backRight.setPower(SPEED);
        while (elapsedTime.seconds() < timeout && opModeIsActive()) {
            idle();
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    private void moveLeft(double timeout)
    {
        elapsedTime.reset();
        frontLeft.setPower(-SPEED);
        frontRight.setPower(SPEED);
        backLeft.setPower(SPEED);
        backRight.setPower(-SPEED);
        while (elapsedTime.seconds() < timeout && opModeIsActive()) {
            idle();
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    private void moveBack(double timeout)
    {
        elapsedTime.reset();
        frontLeft.setPower(-SPEED);
        frontRight.setPower(-SPEED);
        backLeft.setPower(-SPEED);
        backRight.setPower(-SPEED);
        while (elapsedTime.seconds() < timeout && opModeIsActive()) {
            idle();
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    private void moveRight(double timeout)
    {
        elapsedTime.reset();
        frontLeft.setPower(SPEED);
        frontRight.setPower(-SPEED);
        backLeft.setPower(-SPEED);
        backRight.setPower(SPEED);
        while (elapsedTime.seconds() < timeout && opModeIsActive()) {
            idle();
        }
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}
