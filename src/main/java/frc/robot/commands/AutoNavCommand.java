// Copyright (c) Team 564.
// Open Source Software; you can modify and/or share it under the terms of
// the BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.DrivetrainSubsystem;

public class AutoNavCommand extends SequentialCommandGroup {

  public enum AutoNavChallenge {
    BarrelRacing,
    Bounce,
    Slalom;
  }

  // AutoNav Challenge
  private final AutoNavChallenge m_autoNavChallenge;

  // Drivetrain Subsystem
  private final DrivetrainSubsystem m_drivetrainSubsystem;

  public AutoNavCommand(
      DrivetrainSubsystem drivetrainSubsystem, AutoNavChallenge autoNavChallenge) {
    m_drivetrainSubsystem = drivetrainSubsystem;
    m_autoNavChallenge = autoNavChallenge;

    addCommands(
        // Reset the robot to its starting position.
        new InstantCommand(
            () ->
                m_drivetrainSubsystem.resetOdometry(
                    new Pose2d(
                        getTrajectoryStartTranslation(m_autoNavChallenge), new Rotation2d()))),
        // Drive to a trajectory.
        new DriveTrajectoryCommand(m_drivetrainSubsystem, m_autoNavChallenge.toString()));
  }

  private Translation2d getTrajectoryStartTranslation(AutoNavChallenge autoNavChallenge) {
    // TODO: Verify these start positions.
    switch (autoNavChallenge) {
      case BarrelRacing:
        return new Translation2d(Units.inchesToMeters(30), Units.inchesToMeters(30));
      case Bounce:
        return new Translation2d(Units.inchesToMeters(30), Units.inchesToMeters(30));
      case Slalom:
        return new Translation2d(Units.inchesToMeters(30), Units.inchesToMeters(30));
    }
    return new Translation2d(0, 0);
  }

  /** This method is run when the command ends. */
  @Override
  public void end(boolean interrupted) {
    if (interrupted) m_drivetrainSubsystem.tankDriveVolts(0, 0);
  }
}
