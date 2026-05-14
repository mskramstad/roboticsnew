package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="RobotControl")
public class RobotControl extends OpMode {

    // Motor declarations for drivetrain
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private DcMotor linearActuator, viperSlide, liftMotor;

    // Servo declarations for arm and claw
    private Servo servoOne, clawOne, clawTwo;

    // Constants for arm and claw positions
    private static final double ARM_DOWN_POSITION = 0.2;
    private static final double ARM_UP_POSITION = 0.8;
    private static final double CLAW_OPEN_POSITION = 0.8;
    private static final double CLAW_CLOSED_POSITION = 0.2;

    @Override
    public void init() {
        // Initialize drivetrain motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        // Initialize mechanism motors
        linearActuator = hardwareMap.get(DcMotor.class, "linearActuator");
        viperSlide = hardwareMap.get(DcMotor.class, "viperSlide");
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");

        // Initialize servos for the arm and claw
        servoOne = hardwareMap.get(Servo.class, "servoOne");
        clawOne = hardwareMap.get(Servo.class, "clawOne");
        clawTwo = hardwareMap.get(Servo.class, "clawTwo");

        // Set ZeroPowerBehavior for motors
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearActuator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse direction for left drivetrain motors
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        

        // Reverse direction of clawTwo to sync claw opening/closing
        clawTwo.setDirection(Servo.Direction.REVERSE);

        // Initialize servo positions
        servoOne.setPosition(ARM_UP_POSITION);
        clawOne.setPosition(CLAW_OPEN_POSITION);
        clawTwo.setPosition(CLAW_OPEN_POSITION);

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void loop() {
        // === Drivetrain Control with Crabwalk ===
        float vertical = gamepad1.right_stick_y;
        float horizontal = gamepad1.right_stick_x;
        float pivot = gamepad1.left_stick_x;
        
        frontLeft.setPower(pivot + (vertical - horizontal));
        backRight.setPower(pivot + vertical + horizontal);
        frontRight.setPower(-pivot + vertical + horizontal);
        backLeft.setPower(-pivot + (vertical - horizontal));
        
        // === Lift Control ===
        if (gamepad1.dpad_left) {
            liftMotor.setPower(0.1);
        } else if (gamepad1.dpad_right) {
            liftMotor.setPower(-1);
        }
        
        
        // === Linear Actuator Control ===
        if (gamepad1.left_bumper) {
            linearActuator.setPower(1.0); // Extend
        } else if (gamepad1.right_bumper) {
            linearActuator.setPower(-1.0); // Retract
        } else {
            linearActuator.setPower(0.0);
        }

        // === Viper Slide Control ===
        if (gamepad1.dpad_up) {
            viperSlide.setPower(1.0); // Lift up
        } else if (gamepad1.dpad_down) {
            viperSlide.setPower(-1.0); // Lower down
        } else {
            viperSlide.setPower(0.0);
        }

        // === Arm Control ===
        if (gamepad1.a) {
            servoOne.setPosition(ARM_DOWN_POSITION);
            telemetry.addData("Arm", "Moving Down");
            sleep(200); // Debounce
        } else if (gamepad1.b) {
            servoOne.setPosition(ARM_UP_POSITION);
            telemetry.addData("Arm", "Moving Up");
            sleep(200); // Debounce
        }

        // === Claw Control ===
        if (gamepad1.x) {
            clawOne.setPosition(CLAW_CLOSED_POSITION);
            clawTwo.setPosition(CLAW_CLOSED_POSITION);
            telemetry.addData("Claw", "Closing");
            sleep(200); // Debounce
        } else if (gamepad1.y) {
            clawOne.setPosition(CLAW_OPEN_POSITION);
            clawTwo.setPosition(CLAW_OPEN_POSITION);
            telemetry.addData("Claw", "Opening");
            sleep(200); // Debounce
        }

        // === Telemetry Data ===
        telemetry.addData("ServoOne (Arm) Position", servoOne.getPosition());
        telemetry.addData("ClawOne Position", clawOne.getPosition());
        telemetry.addData("ClawTwo Position", clawTwo.getPosition());
       /* telemetry.addData("Drivetrain Power", "FL: %.2f, FR: %.2f, BL: %.2f, BR: %.2f",
                frontLeftPower, frontRightPower, backLeftPower, backRightPower);*/
        telemetry.update();
    }


    // Helper method for sleep in LinearOpMode
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
