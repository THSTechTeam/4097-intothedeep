package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "practiceClass", group = "example")
public class practiceClass extends LinearOpMode
{
    // Initialize all Servos, Motors, and Sensors
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    @Override
    public void runOpMode() throws InterruptedException
    {
        //name it on the driver station
        frontLeftMotor = hardwareMap.dcMotor.get("motorFrontLeft");
        frontRightMotor = hardwareMap.dcMotor.get("motorFrontRight");
        backLeftMotor = hardwareMap.dcMotor.get("motorBackLeft");
        backRightMotor = hardwareMap.dcMotor.get("motorBackRight");

        //set motor condition
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE );
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //goes in this order: Translation, strafe, rotation
        double FL = gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;
        double FR = gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
        double BL = gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x;
        double BR = gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x;

        waitForStart();

        while(opModeIsActive())
        {
            frontLeftMotor.setPower(FL);
            frontRightMotor.setPower(FR);
            backLeftMotor.setPower(BL);
            backRightMotor.setPower(BR);

            idle();
        }
    }
}
