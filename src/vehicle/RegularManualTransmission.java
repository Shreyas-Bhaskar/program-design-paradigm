package vehicle;
/**
 * Implementation of the ManualTransmission interface, representing a standard manual transmission
 * with five gears.
 * It manages transmission states including the current gear, speed, and
 * relevant status messages.
 * The transmission behavior, such as speed changes and gear shifts,
 * adheres to the constraints defined by the gear ranges.
 */

public class RegularManualTransmission implements ManualTransmission {
  private int speed;
  private int gear;
  private String status;
  private final int[][] gearRanges;
  /**
   * Constructs a RegularManualTransmission with specified speed ranges for each gear.
   * Each gear's speed range is defined by two integers representing the lower and
   * upper speed limits. The constructor validates the input ranges and sets up the
   * initial state of the transmission.
   *
   * @param gearLimits Variable number of integers representing the lower and upper
   *                   limits of each gear's speed range.
   * @throws IllegalArgumentException If the gear range inputs are invalid, such as
   *                                  non-sequential ranges or incorrect number of
   *                                  arguments.
   */

  public RegularManualTransmission(int... gearLimits) {
    if (gearLimits.length != 10) {
      throw new IllegalArgumentException("Invalid number of gear range arguments");
    }

    gearRanges = new int[5][2];
    for (int i = 0, j = 0; i < 5; i++, j += 2) {
      gearRanges[i][0] = gearLimits[j];
      gearRanges[i][1] = gearLimits[j + 1];

      // Check if lower limit is higher than upper limit
      if (gearRanges[i][0] > gearRanges[i][1]) {
        throw new IllegalArgumentException("Lower limit of gear range cannot " +
                "be higher than the upper limit");
      }

      // Check for non-overlapping ranges
      if (i > 0 && gearRanges[i][0] > gearRanges[i - 1][1]) {
        throw new IllegalArgumentException("Gaps between gear ranges are not allowed");
      }
    }

    if (gearRanges[0][0] != 0) {
      throw new IllegalArgumentException("Lower speed of the first gear must be 0");
    }

    this.speed = 0;
    this.gear = 1;
    this.status = "OK: everything is OK.";
  }


  @Override
  public String getStatus() {
    return status;
  }

  @Override
  public int getSpeed() {
    return speed;
  }

  @Override
  public int getGear() {
    return gear;
  }

  @Override
  public ManualTransmission increaseSpeed() {
    if (speed == gearRanges[4][1]) {
      status = "Cannot increase speed. Reached maximum speed.";
    } else if (speed >= gearRanges[gear - 1][1]) {
      status = "Cannot increase speed, increase gear first.";
    } else {
      speed++;
      updateStatusAfterSpeedChange();
    }
    return this;
  }

  @Override
  public ManualTransmission decreaseSpeed() {
    if (speed == 0) {
      status = "Cannot decrease speed. Reached minimum speed.";
    } else if (speed <= gearRanges[gear - 1][0]) {
      status = "Cannot decrease speed, decrease gear first.";
    } else {
      speed--;
      updateStatusAfterSpeedChange();
    }
    return this;
  }

  @Override
  public ManualTransmission increaseGear() {
    if (gear == 5) {
      status = "Cannot increase gear. Reached maximum gear.";
    } else if (speed < gearRanges[gear][0]) {
      status = "Cannot increase gear, increase speed first.";
    } else {
      gear++;
      updateStatusAfterGearChange();
    }
    return this;
  }

  @Override
  public ManualTransmission decreaseGear() {
    if (gear == 1) {
      status = "Cannot decrease gear. Reached minimum gear.";
    } else if (speed > gearRanges[gear - 2][1]) {
      status = "Cannot decrease gear, decrease speed first.";
    } else {
      gear--;
      updateStatusAfterGearChange();
    }
    return this;
  }

  private void updateStatusAfterSpeedChange() {
    if (gear < 5 && speed >= gearRanges[gear][0]) {
      status = "OK: you may increase the gear.";
    } else if (gear > 1 && speed <= gearRanges[gear - 2][1]) {
      status = "OK: you may decrease the gear.";
    } else {
      status = "OK: everything is OK.";
    }
  }

  private void updateStatusAfterGearChange() {
    status = "OK: everything is OK.";
  }
}
