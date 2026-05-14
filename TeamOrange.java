package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TeamOrange")
public class TeamOrange extends LinearOpMode {

  private Servo flywheel_vertical;
  private Servo flywheel_servo_one;
  private Servo flywheel_servo_two;
  private Servo bucket_servo;

  private DcMotor fl_motor;
  private DcMotor fr_motor;
  private DcMotor bl_motor;
  private DcMotor br_motor;
  private DcMotor hornet_motor;

  @Override
  public void runOpMode() {
    flywheel_servo_one = hardwareMap.get(Servo.class, "flywheel_servo_one");
    flywheel_servo_two = hardwareMap.get(Servo.class, "flywheel_servo_two");
    flywheel_vertical = hardwareMap.get(Servo.class, "flywheel_vertical");
    bucket_servo = hardwareMap.get(Servo.class, "bucket_servo");

    fl_motor = hardwareMap.get(DcMotor.class, "fl_motor");
    fr_motor = hardwareMap.get(DcMotor.class, "fr_motor");
    bl_motor = hardwareMap.get(DcMotor.class, "bl_motor");
    br_motor = hardwareMap.get(DcMotor.class, "br_motor");
    hornet_motor = hardwareMap.get(DcMotor.class, "hornet_motor");

    fl_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    fr_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    bl_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    br_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    flywheel_vertical.setPosition(0.4);
    
    waitForStart();
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        
        if (gamepad1.right_bumper) {
          flywheel_servo_one.setPosition(1);
          telemetry.addLine("IN");
        } else if (gamepad1.left_bumper) {
          flywheel_servo_one.setPosition(0);
          telemetry.addLine("OUT");
        } else if (gamepad1.right_stick_button) {
          flywheel_servo_one.setPosition(0.5);
          telemetry.addLine("STOP");
        }
        
        if (gamepad1.x){
          bucket_servo.setPosition(0);
          telemetry.addLine("BUCKET UP");
        } else if (gamepad1.y) {
          bucket_servo.setPosition(1);
          telemetry.addLine("BUCKET DOWN");
        }
        
        if (gamepad1.dpad_up) {
          flywheel_vertical.setPosition(0);
          telemetry.addLine("UP");
        } else if (gamepad1.dpad_down) {
          flywheel_vertical.setPosition(0.4);
          telemetry.addLine("DOWN");
        }
        
        double hornetMotorPower = gamepad1.left_trigger;
        double hornetMotorPowerMinus = gamepad1.right_trigger;
        
        double drive = -gamepad1.left_stick_y;
        double strafe = -gamepad1.right_stick_x;
        double rotate = gamepad1.left_stick_x;

        double frontLeftPower = rotate - strafe + drive;
        double frontRightPower = rotate - strafe - drive;
        double backLeftPower = rotate + strafe + drive;
        double backRightPower = rotate + strafe - drive;

        double maxPower = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)));
        
        if (maxPower > 1.0) {
          frontLeftPower /= maxPower;
          frontRightPower /= maxPower;
          backLeftPower /= maxPower;
          backRightPower /= maxPower;
        }

        fl_motor.setPower(frontLeftPower);
        fr_motor.setPower(frontRightPower);
        bl_motor.setPower(-backLeftPower);
        br_motor.setPower(-backRightPower);
        
        hornet_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hornet_motor.setPower(hornetMotorPower);
        hornet_motor.setPower(-hornetMotorPowerMinus);

        telemetry.addData("Drive", drive);
        telemetry.addData("Strafe", strafe);
        telemetry.addData("Rotate", rotate);
        telemetry.addData("FL Power", frontLeftPower);
        telemetry.addData("FR Power", frontRightPower);
        telemetry.addData("BL Power", backLeftPower);
        telemetry.addData("BR Power", backRightPower);
        telemetry.update();
      }
    }
  }
}
