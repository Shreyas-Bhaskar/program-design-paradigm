import org.junit.Before;
import org.junit.Test;

import vehicle.RegularManualTransmission;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegularManualTransmissionTest {

  private RegularManualTransmission transmission;

  @Before
  public void setup() {
    transmission = new RegularManualTransmission(0, 10, 5, 20, 15, 30, 25, 40, 35, 50);
  }

  @Test
  public void testConstructorWithInvalidGearRanges() {
    boolean thrown = false;
    try {
      new RegularManualTransmission(7, 10, 8, 20, 15, 30, 28, 40, 35, 50);
    } catch (IllegalArgumentException e) {
      thrown = true;
    }
    assertTrue("Constructor should throw IllegalArgumentException for invalid gear ranges", thrown);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLowerLimitGreaterThanUpperLimit() {
    new RegularManualTransmission(0, 4, 3, 20, 45, 20, 25, 40, 35, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFirstGearLowerSpeedNotZero() {
    new RegularManualTransmission(1, 10, 11, 20, 21, 30, 31, 40, 41, 50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLowerLimitOfHigherGearNotGreaterThanPrevious() {
    new RegularManualTransmission(0, 10, 15, 20, 10, 10, 30, 40, 35, 50);
  }

  @Test
  public void testInitialConditions() {
    assertEquals(0, transmission.getSpeed());
    assertEquals(1, transmission.getGear());
    assertEquals("OK: everything is OK.", transmission.getStatus());
  }

  @Test
  public void testIncreaseSpeedWithinRange() {
    transmission.increaseSpeed();
    assertEquals(1, transmission.getSpeed());
    assertEquals("OK: everything is OK.", transmission.getStatus());
  }

  @Test
  public void testIncreaseSpeedAtUpperLimit() {
    for (int i = 0; i < 10; i++) {
      transmission.increaseSpeed();
    }
    assertEquals(10, transmission.getSpeed());
    assertEquals("OK: you may increase the gear.", transmission.getStatus());
  }

  @Test
  public void testDecreaseSpeedWithinRange() {
    transmission.increaseSpeed();
    transmission.decreaseSpeed();
    assertEquals(0, transmission.getSpeed());
    assertEquals("OK: everything is OK.", transmission.getStatus());
  }

  @Test
  public void testDecreaseSpeedAtLowerLimit() {
    transmission.increaseSpeed();
    transmission.decreaseSpeed();
    transmission.decreaseSpeed();
    assertEquals(0, transmission.getSpeed());
    assertEquals("Cannot decrease speed. Reached minimum speed.", transmission.getStatus());
  }

  @Test
  public void testIncreaseGearWithinRange() {
    transmission.increaseSpeed().increaseSpeed();
    transmission.increaseGear();
    assertEquals(1, transmission.getGear());
    assertEquals("Cannot increase gear, increase speed first.", transmission.getStatus());
  }

  @Test
  public void testDecreaseGearWithinRange() {
    transmission.increaseSpeed().increaseSpeed();
    transmission.increaseGear();
    transmission.decreaseGear();
    assertEquals(1, transmission.getGear());
    assertEquals("Cannot decrease gear. Reached minimum gear.", transmission.getStatus());
  }

  @Test
  public void testIncreaseSpeedBeyondMax() {
    for (int i = 0; i <= 50; i++) {
      transmission.increaseSpeed();
    }
    assertEquals(10, transmission.getSpeed());
    assertEquals("Cannot increase speed, increase gear first.", transmission.getStatus());
  }

  @Test
  public void testDecreaseSpeedBelowMin() {
    transmission.decreaseSpeed();
    assertEquals(0, transmission.getSpeed());
    assertEquals("Cannot decrease speed. Reached minimum speed.", transmission.getStatus());
  }

  @Test
  public void testCannotIncreaseGearBeyondMax() {
    transmission.increaseSpeed();
    for (int i = 1; i < 5; i++) {
      while (!transmission.getStatus().equals("OK: you may increase the gear.")) {
        transmission.increaseSpeed();
      }
      transmission.increaseGear();
    }

    // At this point, the transmission should be in gear 5.
    assertEquals(5, transmission.getGear());

    // Try to increase the gear again.
    transmission.increaseGear();

    // Check if the status message is as expected.
    assertEquals("Cannot increase gear. Reached maximum gear.", transmission.getStatus());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNonOverlappingRanges() {
    // Example of non-overlapping gear ranges
    new RegularManualTransmission(0, 10, 15, 20, 25, 30, 35, 40, 45, 50);
  }

  @Test
  public void testDecreaseGearBelowMin() {
    transmission.decreaseGear();
    assertEquals(1, transmission.getGear());
    assertEquals("Cannot decrease gear. Reached minimum gear.", transmission.getStatus());
  }

}
