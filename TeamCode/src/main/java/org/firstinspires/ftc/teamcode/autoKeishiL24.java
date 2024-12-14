package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Autonomous(name = "Auto_Left")
public class autoKeishiL24 extends LinearOpMode
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
    private IMU imu;

    final double armTicksPerDegree = 28 * (250047.0 / 4913.0) * (100.0 / 20.0) * (1 / 360.0);

    // Predetermined positions for arm
    final double armCollapsedIntoRobot = 0;
    final double armCollect = 250 * armTicksPerDegree;
    final double armClearBarrier = 230 * armTicksPerDegree;
    final double armScoreSpecimen = 160 * armTicksPerDegree;
    final double armScoreSampleInLow = 160 * armTicksPerDegree;
    final double armAttachHangingHook = 120 * armTicksPerDegree;
    final double armWinchRobot = 10 * armTicksPerDegree;

    // Value for intrake servo
    final double intakeCollect = -1.0;
    final double intakeOff = 0.0;
    final double intakeDeposit = 0.5;

    // Value for wrist servo
    final double wristFoldedIn = 0.9;
    final double wristFoldedOut  = 0.55;

    int armPosition = (int)armCollapsedIntoRobot;

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
        imu = hardwareMap.get(IMU.class, "imu");
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

        IMU.Parameters myIMUparameters;

        myIMUparameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );

        //Initializing IMU parameters
        imu.initialize(myIMUparameters);

        waitForStart();

        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(0.2);

        // Setting intake and wrist at beginning position
        intake.setPower(intakeOff);
        wrist.setPosition(wristFoldedIn);

        // Update telemetry
        telemetry.addLine("Robot Ready.");
        telemetry.update();


        // The Robots final pathing
        armStart();
        moveForward(0.05);
        moveLeft(1.0);
        moveForward(1.5);
        imu.resetYaw();
        turn(-90);
        moveForward(0.5);
        // armUp();
        while (opModeIsActive()) {
            idle();
        }

    }

    public void turn (double degrees)
    {
        double var = 2;
        while(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > degrees + var
                || imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) < degrees - var)
        {
            if (imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > degrees)
            {
                double x = (degrees - imu.getRobotYawPitchRollAngles().getYaw());
                double speed = 0.000225 * (x * x);


                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
                frontLeft.setPower(speed);
                frontRight.setPower(-speed);
                backLeft.setPower(speed);
                backRight.setPower(-speed);
                //telemetry.addData("speed", speed);
                //telemetry.update();
            }
            else if (imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) < degrees)
            {
                double x = (degrees - imu.getRobotYawPitchRollAngles().getYaw());
                double speed = 0.000225 * (x * x);


                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
                frontLeft.setPower(-speed);
                frontRight.setPower(speed);
                backLeft.setPower(-speed);
                backRight.setPower(speed);
                //telemetry.addData("speed", speed);
                //telemetry.update();
            }
            telemetry.addData("Yaw", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.update();
        }
        imu.resetYaw();
    }

    private void armStart()
    {
        armPosition = (int)armWinchRobot;
        armMotor.setTargetPosition(armPosition);

        telemetry.addLine("Arm Start Done");
        telemetry.update();
    }
    private void armUp() {
        armPosition = (int)armAttachHangingHook;
        armMotor.setTargetPosition(armPosition);

        telemetry.addLine("Arm Up Done");
        telemetry.update();
    }

    private void armDown() {
        armPosition = (int)armScoreSpecimen;
        armMotor.setTargetPosition(armPosition);
    }
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

    private void waitSec(long t) throws InterruptedException {
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        wait(t);
    }
}
