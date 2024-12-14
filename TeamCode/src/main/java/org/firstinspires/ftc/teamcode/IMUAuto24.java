package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@Autonomous(name = "IMUAuto4097")
public class IMUAuto24 extends LinearOpMode
{
    private IMU imu;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    @Override
    public void runOpMode() throws InterruptedException
    {
        frontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        frontRight = hardwareMap.dcMotor.get("motorFrontRight");
        backLeft = hardwareMap.dcMotor.get("motorBackLeft");
        backRight = hardwareMap.dcMotor.get("motorBackRight");
        imu = hardwareMap.get(IMU.class, "imu");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IMU.Parameters myIMUparameters;

        myIMUparameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );

        //Initializing IMU parameters
        imu.initialize(myIMUparameters);

        double Yaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);

        waitForStart();

        for(int i = 0; i < 3; i++){
            imu.resetYaw();
            turn(90);
            telemetry.addData("loop", i);
            telemetry.update();
        }

        //AutonomousState state = INITIAL;

        //while(opModeIsActive())
        //{
        //    switch (state) {
        //        case INITIAL:
        //            turn(90);
        //            state = FIRST_TURN;
        //        case FIRST_TURN:
        //            // moveForward(500);
        //            state = SECOND_TURN;
        //        case SECOND_TURN:
        //            turn(-90);
        //    }
        //    turn(90);
        //    turn(-90);

        //}



    }
    public void turn (double degrees)
    {
        double var = 3;
        while(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > degrees + var
                || imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) < degrees - var)
                //< degrees - var || imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) > degrees + var )
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
}

enum AutonomousState {
    INITIAL,
    FIRST_TURN,
    MOVE_FORWARD,
    SECOND_TURN,
    TOUCH_BAR // or grab piece?
}