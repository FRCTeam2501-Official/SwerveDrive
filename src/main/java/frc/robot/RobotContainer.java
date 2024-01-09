// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.swervedrive;
import frc.robot.subsystems.drive;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
private final drive s_drive = new drive();

private final Joystick control = new Joystick(1);
private final Joystick turn = new Joystick(2);
private final Joystick xbox= new Joystick(0);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {configureBindings();}

  private void configureBindings() {

//s_drive.setDefaultCommand(new swervedrive(s_drive,() -> -control.getRawAxis(1) ,() -> -control.getRawAxis(0) ,() -> turn.getRawAxis(0) ) );

s_drive.setDefaultCommand(new swervedrive(s_drive,() -> -xbox.getRawAxis(1) ,() -> -xbox.getRawAxis(0) ,() -> xbox.getRawAxis(2),1 ) );

JoystickButton rt = new JoystickButton(xbox, 8);
JoystickButton lt = new JoystickButton(xbox, 7);
rt.onTrue(new  swervedrive(s_drive,() -> -xbox.getRawAxis(1) ,() -> -xbox.getRawAxis(0) ,() -> xbox.getRawAxis(2),1 ));
lt.onTrue(new  swervedrive(s_drive,() -> -xbox.getRawAxis(1) ,() -> -xbox.getRawAxis(0) ,() -> xbox.getRawAxis(2),.25 ));


  }


  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
}
}
