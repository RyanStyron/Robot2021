// Copyright (c) Team 564.
// Open Source Software; you can modify and/or share it under the terms of
// the BSD license file in the root directory of this project.

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// This is a modification of SendableChooser that adds support for adding a listener.

package frc.robot.util;

import static edu.wpi.first.wpilibj.util.ErrorMessages.requireNonNullParam;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SendableRegistry;

/**
 * The {@link SendableChooser} class is a useful tool for presenting a selection of options to the
 * {@link SmartDashboard}.
 *
 * <p>For instance, you may wish to be able to select between multiple autonomous modes. You can do
 * this by putting every possible Command you want to run as an autonomous into a {@link
 * SendableChooser} and then put it into the {@link SmartDashboard} to have a list of options appear
 * on the laptop. Once autonomous starts, simply ask the {@link SendableChooser} what the selected
 * value is.
 *
 * @param <V> The type of the values to be stored
 */
public class SendableChooser<V> implements Sendable, AutoCloseable {
  /** The key for the default value. */
  private static final String DEFAULT = "default";
  /** The key for the selected option. */
  private static final String SELECTED = "selected";
  /** The key for the active option. */
  private static final String ACTIVE = "active";
  /** The key for the option array. */
  private static final String OPTIONS = "options";
  /** The key for the instance number. */
  private static final String INSTANCE = ".instance";
  /** A map linking strings to the objects the represent. */
  private final Map<String, V> m_map = new LinkedHashMap<>();

  private String m_defaultChoice = "";
  private final int m_instance;
  private static final AtomicInteger s_instances = new AtomicInteger();

  private final List<Consumer<V>> m_listeners = new ArrayList<>();

  /** Instantiates a {@link SendableChooser}. */
  public SendableChooser() {
    m_instance = s_instances.getAndIncrement();
    SendableRegistry.add(this, "SendableChooser", m_instance);
  }

  @Override
  public void close() {
    SendableRegistry.remove(this);
  }

  /**
   * Adds the given object to the list of options. On the {@link SmartDashboard} on the desktop, the
   * object will appear as the given name.
   *
   * @param name the name of the option
   * @param object the option
   */
  public void addOption(String name, V object) {
    m_map.put(name, object);
  }

  /**
   * Adds the given object to the list of options.
   *
   * @deprecated Use {@link #addOption(String, Object)} instead.
   * @param name the name of the option
   * @param object the option
   */
  @Deprecated
  public void addObject(String name, V object) {
    addOption(name, object);
  }

  /**
   * Add a listener for changes to the active option.
   *
   * @param name the name of the option
   * @param object the option
   */
  public void addListener(Consumer<V> listener) {
    m_listeners.add(listener);
  }

  /**
   * Adds the given object to the list of options and marks it as the default. Functionally, this is
   * very close to {@link #addOption(String, Object)} except that it will use this as the default
   * option if none other is explicitly selected.
   *
   * @param name the name of the option
   * @param object the option
   */
  public void setDefaultOption(String name, V object) {
    requireNonNullParam(name, "name", "setDefaultOption");

    m_defaultChoice = name;
    addOption(name, object);
  }

  /**
   * Adds the given object to the list of options and marks it as the default.
   *
   * @deprecated Use {@link #setDefaultOption(String, Object)} instead.
   * @param name the name of the option
   * @param object the option
   */
  @Deprecated
  public void addDefault(String name, V object) {
    setDefaultOption(name, object);
  }

  /**
   * Returns the selected option. If there is none selected, it will return the default. If there is
   * none selected and no default, then it will return {@code null}.
   *
   * @return the option selected
   */
  public V getSelected() {
    m_mutex.lock();
    try {
      if (m_selected != null) {
        return m_map.get(m_selected);
      } else {
        return m_map.get(m_defaultChoice);
      }
    } finally {
      m_mutex.unlock();
    }
  }

  private String m_selected;
  private final List<NetworkTableEntry> m_activeEntries = new ArrayList<>();
  private final ReentrantLock m_mutex = new ReentrantLock();

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("String Chooser");
    builder.getEntry(INSTANCE).setDouble(m_instance);
    builder.addStringProperty(DEFAULT, () -> m_defaultChoice, null);
    builder.addStringArrayProperty(OPTIONS, () -> m_map.keySet().toArray(new String[0]), null);
    builder.addStringProperty(
        ACTIVE,
        () -> {
          m_mutex.lock();
          try {
            if (m_selected != null) {
              return m_selected;
            } else {
              return m_defaultChoice;
            }
          } finally {
            m_mutex.unlock();
          }
        },
        null);
    m_mutex.lock();
    try {
      m_activeEntries.add(builder.getEntry(ACTIVE));
      builder
          .getEntry(ACTIVE)
          .addListener(
              (event) -> {
                if (!m_listeners.isEmpty()) {
                  V value = m_map.get(event.value.getValue());
                  for (Consumer<V> listener : m_listeners) listener.accept(value);
                }
              },
              EntryListenerFlags.kImmediate | EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
    } finally {
      m_mutex.unlock();
    }
    builder.addStringProperty(
        SELECTED,
        null,
        val -> {
          m_mutex.lock();
          try {
            m_selected = val;
            for (NetworkTableEntry entry : m_activeEntries) {
              entry.setString(val);
            }
          } finally {
            m_mutex.unlock();
          }
        });
  }
}
