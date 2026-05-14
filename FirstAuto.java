package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "NewTeamBlack Autonomous")
public class FirstAuto extends LinearOpMode {
  
  private DcMotor fl_motor, fr_motor, bl_motor, br_motor, lift_motor, arm_extension;
  private Servo box;
  private Servo rotateClaw;
  
  @Override
  public void runOpMode() {
    fl_motor = hardwareMap.get(DcMotor.class, "fl_motor");
    fr_motor = hardwareMap.get(DcMotor.class, "fr_motor");
    bl_motor = hardwareMap.get(DcMotor.class, "bl_motor");
    br_motor = hardwareMap.get(DcMotor.class, "br_motor");
    arm_extension = hardwareMap.get(DcMotor.class, "arm_extension");
    lift_motor = hardwareMap.get(DcMotor.class, "lift_motor");
    
    rotateClaw = hardwareMap.get(Servo.class, "Rotate_Claw");
    box = hardwareMap.get(Servo.class, "box");
    
    
    
    setMotorBehavior();
    
    
    waitForStart();

    if (opModeIsActive()) {
      givePower(-1,1);
      
      driveBackward(0.7,1);
      
      stopMotors();//
      sleep(1000); //
      
      driveForward(1,0.2);
      
      stopMotors(); //
      sleep(2000); //
      
      turnLeft(0.5,0.3);
      
      stopMotors(); //
      sleep(1000); //
      
      driveBackward(0.5,0.3);
      
      stopMotors(); //
      
      sleep(1000); //
      
      rotateClaw.setPosition(1);
      sleep(500); //
      liftUp(-1,1.8);
      stopMotors();
      sleep(250); //
      box.setPosition(0.4); 
      box.setPosition(0);
      
      sleep(1000);           
    }
  }

  private void setMotorBehavior() {
    DcMotor[] motors = {fl_motor, fr_motor, bl_motor, br_motor, lift_motor};
    for (DcMotor motor : motors) {
      motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
  }
  
  private void crabWalkLeft(double power, double seconds){
    fl_motor.setPower(power);
    fr_motor.setPower(power);
    bl_motor.setPower(power);
    br_motor.setPower(power);
    sleep((long)(seconds * 1000));
  }
  
  private void driveForward(double power, double seconds) {
    fl_motor.setPower(-power);
    fr_motor.setPower(power);
    bl_motor.setPower(power);
    br_motor.setPower(-power);
    sleep((long)(seconds * 1000));
  }

  private void driveBackward(double power, double seconds) {
    fl_motor.setPower(power);
    fr_motor.setPower(-power);
    bl_motor.setPower(-power);
    br_motor.setPower(power);
    sleep((long)(seconds * 1000)); // Convert seconds to milliseconds
  }
  private void givePower(double power, double seconds){
    arm_extension.setPower(power);
    sleep((long)(seconds * 1000));
  }
  
  private void liftUp(double power, double seconds){
    lift_motor.setPower(power);
    sleep((long)(seconds * 1000));
  }
  
  private void turnLeft(double power, double seconds) {
    fl_motor.setPower(-power);
    fr_motor.setPower(power);
    bl_motor.setPower(-power);
    br_motor.setPower(power);
    sleep((long)(seconds * 1000)); // Convert seconds to milliseconds
  }
  
  
  private void turnRight(double power, double seconds) {
    fl_motor.setPower(power);
    fr_motor.setPower(-power);
    bl_motor.setPower(power);
    br_motor.setPower(-power);
    sleep((long)(seconds * 1000)); // Convert seconds to milliseconds
  }

  private void stopMotors() {
    lift_motor.setPower(0);
    fl_motor.setPower(0);
    fr_motor.setPower(0);
    bl_motor.setPower(0);
    br_motor.setPower(0);
    arm_extension.setPower(0);
  }
}
