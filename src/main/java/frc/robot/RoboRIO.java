// Copyright (c) Team 564.
// Open Source Software; you can modify and/or share it under the terms of
// the BSD license file in the root directory of this project.

package frc.robot;

/** This class provides constants relating to the hardware connected to the RoboRIO. */
public final class RoboRIO {
  // CAN Devices

  /** The port of the front left drive motor. */
  public static final int kPortMotorDriveFrontLeft = 1;
  /** The port of the back left drive motor. */
  public static final int kPortMotorDriveBackLeft = 2;
  /** The port of the front right drive motor. */
  public static final int kPortMotorDriveFrontRight = 3;
  /** The port of the back right drive motor. */
  public static final int kPortMotorDriveBackRight = 4;
  /** The port of the intake motor. */
  public static final int kPortMotorIntake = 7;
  /** The port of the left launcher motor. */
  public static final int kPortMotorLauncherLeft = 8;
  /** The port of the conveyor belt. */
  public static final int kPortMotorBelt = 10;
  /**
   * The port of the right launcher motor. On the electronics board, this is #8b, but this couldn't
   * be replicated due to software limitaitons.
   */
  public static final int kPortMotorLauncherRight = 11;

  // PCM Devices

  /** The port of the forward channel of the launcher cannon double solenoid. */
  public static final int kPortDoubleSolenoidForwardLauncherCannon = 6;
  /** The port of the backward channel of the launcher cannon double solenoid. */
  public static final int kPortDoubleSolenoidBackwardLauncherCannon = 5;

  // DIO Devices

  /** The port of the limit switch sensor of the winch. */
  // public static final int kPortLimitSwitchSensorWinch = -1;
  /**
   * The port of the photoelectric sensor at the bottom of the storage, where balls enter. This is a
   * 42EF-D1MNAK-A2.
   */
  // public static final int kPortPhotoelectricSensorEnter = -1;
  /**
   * The port of the photoelectric sensor at the top of the storage, where balls leave. This is a
   * 42JT-F5LET1-A2.
   */
  // public static final int kPortPhotoelectricSensorExit = -1;

  // Analog Inputs

  /** The port of the ultrasonic sensor. */
  public static final int kPortUltrasonicSensorPort = 0;
  /**
   * The conversion from the voltage reading of the analog ultrasonic sensor, to meters. The voltage
   * / distance range for the MB1013 is 300-mm / 293mV, 5000-mm / 4.885V. For more info, see the
   * description of pin 3 of the board here:
   * https://www.maxbotix.com/documents/HRLV-MaxSonar-EZ_Datasheet.pdf
   */
  public static final double kMetersPerVoltUltrasonic = (5000 / 4.885) * (1.0 / 1000);
  /**
   * The minimum distance that the distance sensor can read.
   *
   * @see #kMetersPerVolt
   */
  public static final double kMinimumReadingUltrasonic = (300) * (1.0 / 1000);
  /**
   * The maximum distance that the distance sensor can read. See above for info on where this value
   * came from.
   *
   * @see #kMetersPerVolt
   */
  public static final double kMaximumReadingUltrasonic = (5000) * (1.0 / 1000);
  /**
   * The range of values that the ultrasonic sensor will read.
   *
   * @see #kMetersPerVolt
   */
  public static final double kUltrasonicRange =
      kMaximumReadingUltrasonic - kMinimumReadingUltrasonic;
}
