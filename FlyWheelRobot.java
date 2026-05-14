// In the driver hub, we must assign variables to specific motors and servos
// three dots in corner and configure robot

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "NewTeamBlack")
public class FlyWheelRobot extends LinearOpMode {

    private DcMotor fl_motor;
    private DcMotor fr_motor;
    private DcMotor bl_motor;
    private DcMotor br_motor;
    private DcMotor fw_motor;
    private CRServo l_servo;
    private CRServo r_servo;

    @Override
    public void runOpMode() {

        // Hardware mapping
        fl_motor = hardwareMap.get(DcMotor.class, "fl_motor");
        fr_motor = hardwareMap.get(DcMotor.class, "fr_motor");
        bl_motor = hardwareMap.get(DcMotor.class, "bl_motor");
        br_motor = hardwareMap.get(DcMotor.class, "br_motor");
        fw_motor = hardwareMap.get(DcMotor.class, "fw_motor");
        l_servo = hardwareMap.get(CRServo.class, "l_servo");
        r_servo = hardwareMap.get(CRServo.class, "r_servo");

        // Motor setup
        fl_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fw_motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        fl_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fw_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Toggle variables
        boolean wheelSpinning = false;
        boolean prevX = false;   // previous button state

        waitForStart();

        while (opModeIsActive()) {

            // Drivetrain input

            // use the joysticks on the controller to move the robots
            // left joystick push forward and backwards to move straight and back
            // left joystick left and right turn around
            // right joystick left and right move horizontally.
            
            // blue button (x) is a toggle for the flywheel
            // up arrow on left - dpad loads the ball
            // down arrow is supposed to reject the ball
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.right_stick_x;
            double rotate = gamepad1.left_stick_x;

            double frontLeftPower = drive + strafe + rotate;
            double frontRightPower = drive - strafe - rotate;
            double backLeftPower = drive - strafe + rotate;
            double backRightPower = drive + strafe - rotate;

            double flyWheelPower = 0;
            double servoPower = 0;

            // -------- X BUTTON TOGGLE (EDGE DETECTION) --------
            boolean currX = gamepad1.x;

            if (currX && !prevX) {
                wheelSpinning = !wheelSpinning;
            }

            prevX = currX;
            // --------------------------------------------------

            if (wheelSpinning) {
                flyWheelPower = -0.855;
            }

            // Servo control
            if (gamepad1.dpad_up) {
                servoPower = 1;
            } else if (gamepad1.dpad_down) {
                servoPower = -1;
            }

            // Normalize drivetrain power
            double maxPower = Math.max(
                    Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                    Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))
            );

            if (maxPower > 1.0) {
                frontLeftPower /= maxPower;
                frontRightPower /= maxPower;
                backLeftPower /= maxPower;
                backRightPower /= maxPower;
            }

            // Set motor powers
            fl_motor.setPower(-frontLeftPower);
            fr_motor.setPower(frontRightPower);
            bl_motor.setPower(-backLeftPower);
            br_motor.setPower(-backRightPower);
            fw_motor.setPower(flyWheelPower);

            l_servo.setPower(servoPower);
            r_servo.setPower(-servoPower);

            // Telemetry
            telemetry.addData("Flywheel On", wheelSpinning);
            telemetry.addData("Flywheel Power", flyWheelPower);
            telemetry.update();
        }
    }
}
