package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Automatic_Field_Left")
public class Auto2keishi extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    final private static double SPEED = 0.5;
    private Servo pitch;
    private Servo hand;
    private DcMotor arm1;
    private DcMotor arm2;
    private Servo handBig;
    private Servo handSmall;
    private ElapsedTime elapsedTime;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        frontRight = hardwareMap.dcMotor.get("motorFrontRight");
        backLeft = hardwareMap.dcMotor.get("motorBackLeft");
        backRight = hardwareMap.dcMotor.get("motorBackRight");
        arm1 = hardwareMap.dcMotor.get("arm1");
        arm2 = hardwareMap.dcMotor.get("arm2");
        pitch = hardwareMap.servo.get("pitch");
        handBig = hardwareMap.servo.get("handBig");
        handSmall = hardwareMap.servo.get("handSmall");
        elapsedTime = new ElapsedTime();

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();


        slidesup(0.4);
        moveForward(0.5);
        moveLeft(2.8);
        moveRight(0.5);
    }

    private void slidesup(double timeout){
        elapsedTime.reset();
        arm1.setPower(0.8);
        arm2.setPower(0.8);
        while (elapsedTime.seconds() < timeout && opModeIsActive()) {
            idle();
        }
        arm1.setPower(0);
        arm2.setPower(0);
    }
    private void moveForward(double timeout) {
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

    private void moveLeft(double timeout) {
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
    private void moveRight(double timeout) {
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
