// Copyright (c) Team 564.
// Open Source Software; you can modify and/or share it under the terms of
// the BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DrivetrainSubsystem;

public class AutoNavCommand extends CommandBase {

  public enum AutoNavChallenge {
    BarrelRacing("BarrelRacing"),
    Bounce("Bounce"),
    Slalom("Slalom");

    private String challengeName;

    private AutoNavChallenge(String challengeName) {
      this.challengeName = challengeName;
    }
  }

  // private final AutoNavChallenge m_autoNavChallenge;

  // private final Path m_trajectoryFilePath;

  public AutoNavCommand(
      AutoNavChallenge autoNavChallenge, DrivetrainSubsystem drivetrainSubsystem) {
    // m_autoNavChallenge = autoNavChallenge;
    // m_trajectoryFilePath =
    //     Filesystem.getDeployDirectory().toPath().resolve("paths/Slalom.wpilib.json");
    // m_trajectory = TrajectoryUtil.fromPathweaverJson(m_trajectoryFilePath);
  }
}
