
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Auton1")
public class Auton1 extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
     ElapsedTime runtime = new ElapsedTime();
     DcMotor frontLeft = null;
     DcMotor backLeft = null;
     DcMotor frontRight = null;
     DcMotor backRight = null;
     DcMotorEx launcher=null;
     DcMotor intake =null;
     DcMotor turretrotateer=null;

    private GoBildaPinpointDriver pinpoint=null;

    @Override
    public void runOpMode() {

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        intake = hardwareMap.get(DcMotor.class, "intake");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");

        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        launcher.setDirection(DcMotorSimple.Direction.REVERSE);

        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        if (opModeIsActive())
        {

            moveForward();
        }
    }


    public void shooterFunction()
    {
//        if(gamepad2.left_bumper)
//        {
//            turretrotateer.setPower(1);
//        }
//        if(gamepad2.right_bumper)
//        {
//            turretrotateer.setPower(-1);
//        }
//        else {
//            turretrotateer.setPower(0);
//        }

        if (gamepad2.right_trigger > 0.1)
        {
            launcher.setPower(1);
        }
        else
        {
            launcher.setPower(0);
        }

//        if(gamepad1.dpad_up)
//        {
//            spring.setPosition(0.5);
//        }
//        if(gamepad1.dpad_down)
//        {
//            spring.setPosition(0);
//        }


    }

    public void moveForward()
    {
        frontLeft.setPower(.5);
        frontRight.setPower(.5);
        backLeft.setPower(.5);
        backRight.setPower(.5);
        sleep(10000);
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void intaker()
    {
        if (gamepad1.left_trigger > 0.1 || gamepad2.left_trigger > 0.1)
        {
            intake.setPower(1);
        }
        else
        {
            intake.setPower(0);
        }
    }

    public void mecanumDrive()
    {
           double max;

        // uses left joystick to go forward & strafe, and right joystick to rotate.
        double vertical = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double strafe = gamepad1.left_stick_x; // STRAFE
        double rotate =  gamepad1.right_stick_x; // rotate/ROTATE

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double frontLeftPower = vertical + strafe + rotate;
        double frontRightPower = vertical - strafe - rotate;
        double backLeftPower = vertical - strafe + rotate;
        double backRightPower = vertical + strafe - rotate;

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower /= max;
            frontRightPower /= max;
            backLeftPower  /= max;
            backRightPower /= max;
        }

        // Send calculated power to wheels
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
        telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
        telemetry.update();
    }
}
