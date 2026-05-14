package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "New Test 2")
public class justin extends LinearOpMode {

  private DcMotor motorTest;

  /**
   * This function is executed when this OpMode is selected from the Driver Station.
   */
  @Override
  public void runOpMode() {
    motorTest = hardwareMap.get(DcMotor.class, "motorTest");
    double tgtPower = 0;
    // Put initialization blocks here.
    waitForStart();
    if (opModeIsActive()) {
      tgtPower = -this.gamepad1.left_stick_y;
      motorTest.setPower(tgtPower);
      telemetry.speak("Lucas Rocks!");
      telemetry.addData("Status", "Running");
      telemetry.update();
      // Put run blocks here.
      while (opModeIsActive()) {
        // Put loop blocks here.
        telemetry.update();
        motorTest.setPower(tgtPower);
      }
    }
  }
}
