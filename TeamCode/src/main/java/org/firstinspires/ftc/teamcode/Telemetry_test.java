package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Telemetry Test", group = "test")
public class Telemetry_test extends LinearOpMode {
    @Override
            public void runOpMode() throws InterruptedException {


    waitForStart();
    telemetry.addData("SOSOSIGMAA"," BIBIDI SKOILET");
    telemetry.update();
    while (opModeIsActive()) {
        return;
    }
}
}
