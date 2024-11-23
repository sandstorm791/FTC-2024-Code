package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class Test extends LinearOpMode{
    private Blinker control_Hub;
    private IMU imu;
    
    private DcMotor rightMotor;
    private DcMotor leftMotor;
    
    private DcMotor shoulderMotor;
    private DcMotor elbowMotor;
    
    private CRServo fingers;
    private CRServo hands;

    // todo: write your code here
    
    @Override
    public void runOpMode() {
        
        // declare objects and bind them to motors on hardware map
        rightMotor = hardwareMap.get(DcMotor.class, "Right");
        leftMotor = hardwareMap.get(DcMotor.class, "Left");
        
        shoulderMotor = hardwareMap.get(DcMotor.class, "Shoulder");
        elbowMotor = hardwareMap.get(DcMotor.class, "Elbow");
        fingers = hardwareMap.get(CRServo.class, "Fingers");
        hands = hardwareMap.get(CRServo.class, "Hands");
        
        // init telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        //wait for game to start
        waitForStart();
        
        double rightPower = 0;
        double leftPower = 0;
        
        double shoulderPower = 0;
        double shoulderToggle;
        
        double elbowPower = 0;
        double elbowToggle;
        double fingerControl = 0;
        double handControl = 0;
        
        // shoulder lock control variable (old shoulderlock)
        // double shoulderLock = 0.1;
        
        // new shoulder, elbow lock and drivetrain brake code
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shoulderMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elbowMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        while(opModeIsActive()) {
            
            // drivetrain apply power
            rightMotor.setPower(rightPower);
            leftMotor.setPower(leftPower);
            
            // apply power shoudler, elbow
            // old shoulderlock: shoulderMotor.setPower(shoulderPower + shoulderLock);
            
            shoulderMotor.setPower(shoulderPower);
            
            elbowMotor.setPower(elbowPower);
            
            // apply power to fingers
            // note that < 0.5 is one way and > 0.5 is another
            fingers.setPower(fingerControl);
            hands.setPower(handControl);
            
            leftPower = gamepad1.left_stick_y;
            rightPower = -gamepad1.right_stick_y;
            
            if (gamepad1.dpad_up) {
                fingerControl = 1;
            }
            else if (gamepad1.dpad_down) {
                fingerControl = -1;
            }
            else { fingerControl = 0; }
            
            // hands
            if (gamepad1.dpad_left) {
                handControl = -0.73;
            }
            else if (gamepad1.dpad_right) {
                handControl = 1;
            }
            else { handControl = 0.1; }
            
            // these if statements for shoulder and elbow decide whether to
            // invert the input based on whether bumper is pressed
            if (gamepad1.left_bumper) {
                shoulderToggle = -1.0;
            }
            else { shoulderToggle = 1.0; }
            
            /*old shoulder lock works by having a base tension toggled by A
            if (gamepad1.a) {
                shoulderLock = 0;
            }
            else { shoulderLock = 0.1; }
            */
            
            shoulderPower = gamepad1.left_trigger * shoulderToggle;
            
            if (gamepad1.right_bumper) {
                elbowToggle = -1.0;
            }
            else { elbowToggle = 1.0; }
            
            elbowPower = gamepad1.right_trigger * elbowToggle;
            
            //telemetry for motors
            telemetry.addData("Left Drive Power:", leftMotor.getPower());
            telemetry.addData("Right Drive Power:", leftMotor.getPower());
            
            telemetry.addData("Shoulder Power:", shoulderMotor.getPower());
            telemetry.addData("Elbow Power:", elbowMotor.getPower());
            
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
        
        
    }
}
