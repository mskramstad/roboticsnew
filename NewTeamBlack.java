package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name = "NewTeamBlack")

public class NewTeamBlack extends LinearOpMode {
  private DcMotor fl_motor;
  private DcMotor fr_motor;
  private DcMotor bl_motor;
  private DcMotor br_motor;
  private DcMotor lift_motor;
  private DcMotor arm_extension;
  
  private Servo box;
  //private Servo rotateClaw;
  //private Servo leftClaw;
  //private Servo rightClaw;
  
  //private int steps = 0;
  
  boolean buttonAPreviousState = false;
  boolean buttonBPreviousState = false;
  boolean buttonXPreviousState = false;
  boolean buttonYPreviousState = false;
  
  boolean button2APreviousState = false;
  boolean button2BPreviousState = false;
  boolean button2XPreviousState = false;
  boolean button2YPreviousState = false;
  
  int boxCounter = 0;
  //int clawCounter = 0;
  int rotateCounter = 0;
  
  boolean armExtended = false;
  
  @Override
  public void runOpMode() {
    //Classifying Configurations
    fl_motor = hardwareMap.get(DcMotor.class, "fl_motor");
    fr_motor = hardwareMap.get(DcMotor.class, "fr_motor");
    bl_motor = hardwareMap.get(DcMotor.class, "bl_motor");
    br_motor = hardwareMap.get(DcMotor.class, "br_motor");
    lift_motor = hardwareMap.get(DcMotor.class, "lift_motor"); 
    arm_extension = hardwareMap.get(DcMotor.class, "arm_extension");
    
    //rotateClaw = hardwareMap.get(Servo.class, "Rotate_Claw");
    //leftClaw = hardwareMap.get(Servo.class, "Clawleft");
    //rightClaw = hardwareMap.get(Servo.class, "Clawright");
    
    //Setting Up the Motors
    fl_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    fr_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    bl_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    br_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    lift_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
  
    // display instructions for the driver
    telemetry.addLine("INSTRUCTIONS FOR DRIVER:");
    telemetry.addLine("**MOVEMENT**");
    telemetry.addLine("LEFT STICK VERTICAL AXIS IS FOR DRIVING");
    telemetry.addLine("LEFT STICK HORIZONTAL AXIS IS FOR ROTATING");
    telemetry.addLine("RIGHT STICK HORIZONTAL AXIS IS FOR STRAFING");
    telemetry.addLine("**BUTTONS**");
    telemetry.addLine("X IS FOR LONG ARM UP & DOWN MOVEMENT");
    telemetry.addLine("A IS FOR LONG CLAW OPEN & CLOSE MOVEMENT");
    telemetry.addLine("Y IS FOR SHORT ARM UP & DOWN MOVEMENT");
    telemetry.addLine("B IS FOR SHORT CLAW OPEN & CLOSE MOVEMENT");
    telemetry.addLine("DPAD_UP IS FOR LIFTING SHORT ARM");
    telemetry.addLine("DPAD_DOWN IS FOR DROPPING SHORT ARM");
    telemetry.update();
    
    arm_extension.setTargetPosition(50);
    arm_extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    arm_extension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    fl_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    fr_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    bl_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    br_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    lift_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    arm_extension.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    waitForStart();
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        
        // read gamepad inputs for movement and control
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.right_stick_x;
        double rotate = gamepad1.left_stick_x;

        double frontLeftPower = drive + strafe + rotate;
        double frontRightPower = drive - strafe - rotate;
        double backLeftPower = drive - strafe + rotate;
        double backRightPower = drive + strafe - rotate;
        
        // Setting Motor Power
        double maxPower = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                Math.max(Math.abs(backLeftPower), Math.abs(backRightPower)));
        
        if (maxPower > 1.0) {
          frontLeftPower /= maxPower;
          frontRightPower /= maxPower;
          backLeftPower /= maxPower;
          backRightPower /= maxPower;
        }

        // set motor powers for drivetrain
        fl_motor.setPower(-frontLeftPower);
        fr_motor.setPower(frontRightPower);
        bl_motor.setPower(backLeftPower);
        br_motor.setPower(-backRightPower);
        
        
        
        
        
        
       /////////////// //PLAYER TWO
        if (gamepad2.b && !button2BPreviousState) {
            armExtended = !armExtended;
            if (armExtended) {
                arm_extension.setTargetPosition(-430);
            } else {
                arm_extension.setTargetPosition(0);
            }
            arm_extension.setPower(0.5);
        }
        
        
        
        //
        if (gamepad2.a && !button2APreviousState) {
          boxCounter++;
        }
        /*
        if (gamepad2.x && !button2XPreviousState) {
          clawCounter++;
        }
        */
        if (gamepad2.y && !button2YPreviousState) {
          rotateCounter++;
        }
        
        button2APreviousState = gamepad2.a;
        button2BPreviousState = gamepad2.b;
        button2XPreviousState = gamepad2.x;
        button2YPreviousState = gamepad2.y;
        ///////////////
        
        
        
        
        
        
        // Code for Arm Extension
        if (gamepad1.b && !buttonBPreviousState) {
            armExtended = !armExtended;
            if (armExtended) {
                arm_extension.setTargetPosition(-430);
            } else {
                arm_extension.setTargetPosition(0);
            }
            arm_extension.setPower(0.5);
        }
        
        // Code for the 4-stage viper
        if (gamepad1.dpad_up) {
          lift_motor.setPower(-1);
        } else if (gamepad1.dpad_down) {
          lift_motor.setPower(1);
        } else {
          lift_motor.setPower(0);
        }
        
        
        
        //
        if (gamepad1.a && !buttonAPreviousState) {
          boxCounter++;
        }
        /*
        if (gamepad1.x && !buttonXPreviousState) {
          clawCounter++;
        }
        */
        if (gamepad1.y && !buttonYPreviousState) {
          rotateCounter++;
        }
        
        /*
        if (clawCounter % 2 == 0) {
          leftClaw.setPosition(0);
          rightClaw.setPosition(1);
        } else {
          leftClaw.setPosition(0.6);
          rightClaw.setPosition(0.1);
        }
        
        if (rotateCounter % 2 == 0) {
          rotateClaw.setPosition(0);
        } else {
          rotateClaw.setPosition(0.7);
        }
        /*
        if (boxCounter % 2 == 0) {
          box.setPosition(0.5);
        } else {
          box.setPosition(0);
        }
        */
        // Update button states
        buttonAPreviousState = gamepad1.a;
        buttonBPreviousState = gamepad1.b;
        buttonXPreviousState = gamepad1.x;
        buttonYPreviousState = gamepad1.y;
        
        // Telemetry
        telemetry.addData("Box Counter", boxCounter);
        //telemetry.addData("Claw Counter", clawCounter);
        //telemetry.addData("Rotation Counter", rotateCounter);
        telemetry.addData("Arm Target Position", arm_extension.getTargetPosition());
        telemetry.addData("Arm Current Position", arm_extension.getCurrentPosition());
        //telemetry.addData("Rotation Postion", rotateClaw.getPosition());
        telemetry.addData("Drive", drive);
        telemetry.addData("Strafe", strafe);
        telemetry.addData("Rotate", rotate);
        telemetry.addData("FL Power", frontLeftPower);
        telemetry.addData("FR Power", frontRightPower);
        telemetry.addData("BL Power", backLeftPower);
        telemetry.addData("BR Power", backRightPower);
        telemetry.addData("Lift Power", lift_motor.getPower());
        telemetry.update();
      }
    }
  }
}
