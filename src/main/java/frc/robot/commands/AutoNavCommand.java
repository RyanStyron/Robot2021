// Copyright (c) Team 564.
// Open Source Software; you can modify and/or share it under the terms of
// the BSD license file in the root directory of this project.

package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;

import frc.robot.subsystems.DrivetrainSubsystem;

public class AutoNavCommand extends DriveTrajectoryCommand {

  public enum AutoNavChallenge {
    BarrelRacing,
    Bounce,
    Slalom;
  }

  public AutoNavCommand(
      AutoNavChallenge autoNavChallenge, DrivetrainSubsystem drivetrainSubsystem) {
    super(drivetrainSubsystem, getTrajectory(autoNavChallenge));
  }

  private static Trajectory getTrajectory(AutoNavChallenge autoNavChallenge) {
    Path trajectoryFilePath =
        Filesystem.getDeployDirectory()
            .toPath()
            .resolve("paths/" + autoNavChallenge.toString() + ".wpilib.json");
    Trajectory trajectory = new Trajectory();

    try {
      trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryFilePath);
    } catch (IOException exception) {
      exception.printStackTrace();
    }
    return trajectory;
  }
}
