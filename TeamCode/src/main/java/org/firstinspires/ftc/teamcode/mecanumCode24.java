package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
@TeleOp (name = "bigD", group = "Basic Drive")
public class mecanumCode24 extends LinearOpMode
{
    // Call motors into existence
    private IMU imu;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor armMotor;
    private CRServo intake;
    private Servo wrist;
    private DcMotor armL;
    private DcMotor armR;

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

    // A number in degrees that the triggers can adjust the arm position by
    final double fudgeFactor = 15 * armTicksPerDegree;

    double armPosition = (int)armCollapsedIntoRobot;
    double armPositionFudgeFactor;


    @Override
    public void runOpMode() throws InterruptedException
    {
        double left;
        double right;
        double forward;
        double rotate;
        double max;

        // Link motors to the name on the control hub
        frontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        frontRight = hardwareMap.dcMotor.get("motorFrontRight");
        backLeft = hardwareMap.dcMotor.get("motorBackLeft");
        backRight = hardwareMap.dcMotor.get("motorBackRight");
        armMotor = hardwareMap.dcMotor.get("arm");
        armL = hardwareMap.dcMotor.get("armLeft");
        armR = hardwareMap.dcMotor.get("armRight");
        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters myIMUparameters;

        myIMUparameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );


        waitForStart();

        // Set left motors going same direction as right motors
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Allow brake function at zero power
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Maximum current until flagged
        ((DcMotorEx) armMotor).setCurrentAlert(5,CurrentUnit.AMPS);

        // Resetting arm motor encoder to zero
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        armL.setTargetPosition(0);
        armR.setTargetPosition(0);
        armL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        intake = hardwareMap.crservo.get("intake");
        wrist = hardwareMap.servo.get("wrist");

        // Setting intake and wrist at beginning position
        intake.setPower(intakeOff);
        wrist.setPosition(wristFoldedIn);

        // Update telemetry
        telemetry.addLine("Robot Ready.");
        telemetry.update();

        waitForStart();

        while(opModeIsActive())
        {

            double y = -gamepad1.left_stick_y; // y value is reversed
            double rx = gamepad1.right_stick_x; // right stick x-value
            double x = gamepad1.left_stick_x; // left stick x-value

            double fL = y + rx + x;
            double fR = y - rx - x;
            double bL = y + rx - x;
            double bR = y - rx + x;

            frontLeft.setPower(fL);
            frontRight.setPower(fR);
            backLeft.setPower(bL);
            backRight.setPower(bR);

            // Code for double slides
            if (gamepad1.dpad_up)
            {
                armL.setTargetPosition(-2500);
                armR.setTargetPosition(2500);
            }
            else if (gamepad1.dpad_down)
            {
                armL.setTargetPosition(-1);
                armR.setTargetPosition(1);
            }
            // armL.setPower((gamepad1.left_trigger - gamepad1.right_trigger));
            // armR.setPower((gamepad1.right_trigger - gamepad1.left_trigger));

            if (gamepad2.a)
            {
                // intake collect
                intake.setPower(intakeCollect);
            }
            else if (gamepad2.x)
            {
                // intake stop
                intake.setPower(intakeOff);
            }
            else if (gamepad2.b)
            {
                // intake deposit
                intake.setPower(intakeDeposit);
            }

            if(gamepad2.right_bumper)
            {
                // This is intaking + collecting arm position
                armPosition = armCollect;
                wrist.setPosition(wristFoldedOut);
                intake.setPower(intakeCollect);
            }
            else if (gamepad2.left_bumper)
            {
                // 20 degrees up to clear barrier / js one click
                armPosition = armClearBarrier;
            }

            else if (gamepad2.y)
            {
                // score in Low Basket
                armPosition = armScoreSampleInLow;
            }

            else if (gamepad2.dpad_left)
            {
                // turns into starting location
                armPosition = armCollapsedIntoRobot;
                intake.setPower(intakeOff);
                wrist.setPosition(wristFoldedIn);
            }

            else if (gamepad2.dpad_right)
            {
                // for score specimen in high chamber
                armPosition = armScoreSpecimen;
                wrist.setPosition(wristFoldedIn);
            }

            else if (gamepad2.dpad_up)
            {
                // hook onto low rung
                armPosition = armAttachHangingHook;
                intake.setPower(intakeOff);
                wrist.setPosition(wristFoldedIn);
            }

            else if (gamepad2.dpad_down)
            {
                // lift robot once it is hooked
                armPosition = armWinchRobot;
                intake.setPower(intakeOff);
                wrist.setPosition(wristFoldedIn);
            }

            // mitigates slight tappage
            armPositionFudgeFactor = fudgeFactor * (gamepad2.right_trigger + (-gamepad2.left_trigger));

            /* Here we set the target position of our arm to match the variable that was selected
            by the driver.
            We also set the target velocity (speed) the motor runs at, and use setMode to run it.*/
            armMotor.setTargetPosition((int) (armPosition + armPositionFudgeFactor));
            ((DcMotorEx) armMotor).setVelocity(2100);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            ((DcMotorEx) armL).setVelocity(2100);
            armL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ((DcMotorEx) armR).setVelocity(2100);
            armR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Check to see if our arm is over the current limit, and report via telemetry.
            if (((DcMotorEx) armMotor).isOverCurrent())
            {
                telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!");
            }


            // send telemetry to the driver of the arm's current position and target position
            telemetry.addData("armTarget: ", armMotor.getTargetPosition());
            telemetry.addData("arm Encoder: ", armMotor.getCurrentPosition());
            telemetry.update();

        }

        idle();
    }
}
