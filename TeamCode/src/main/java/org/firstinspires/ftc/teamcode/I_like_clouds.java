package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "clouds")
public class I_like_clouds extends LinearOpMode {
    private DcMotor FLmotor;
    private DcMotor FRmotor;
    private DcMotor BLmotor;
    private DcMotor BRmotor;

    @Override
    public void runOpMode() throws InterruptedException {
        FLmotor = hardwareMap.dcMotor.get("FLmotor");
        FRmotor = hardwareMap.dcMotor.get("FRmotor");
        BLmotor = hardwareMap.dcMotor.get("BLmotor");
        BRmotor = hardwareMap.dcMotor.get("BRmotor");

        FLmotor.setDirection(DcMotorSimple.Direction.REVERSE);
        BLmotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {

            double FL = -gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x;
            double FR = -gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x;
            double BL = -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x;
            double BR = -gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x;

            FLmotor.setPower(FL);
            FLmotor.setPower(FR);
            FLmotor.setPower(BL);
            FLmotor.setPower(BR);

            if(gamepad1.a = true){
                telemetry.addData("Is it true?:", "SO TRUE");
                telemetry.update();
            } else{
                telemetry.addData("Is it true?:", "NOT SO TRUE");
                telemetry.update();
            }
            }
        }
    }

