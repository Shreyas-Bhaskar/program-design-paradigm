package vehicle;

/**
 * This interface represents the basic functionalities of a
 * manual transmission system in a vehicle.
 * It allows controlling and retrieving the state of the transmission,
 * including gear changes and speed adjustments.
 */
public interface ManualTransmission {

  /**
   * Retrieves the current status of the transmission.
   *
   * @return The current status as a String. This can include information about the possibility
   *         of changing gears or warnings if certain operations cannot be performed.
   */
  String getStatus();

  /**
   * Gets the current speed of the vehicle.
   *
   * @return The current speed as an integer.
   */
  int getSpeed();

  /**
   * Gets the current gear of the vehicle.
   *
   * @return The current gear as an integer.
   */
  int getGear();

  /**
   * Increases the speed of the vehicle by a fixed amount.
   * This method will adjust the speed within the constraints of the current gear.
   *
   * @return The updated ManualTransmission object reflecting the new state.
   */
  ManualTransmission increaseSpeed();

  /**
   * Decreases the speed of the vehicle by a fixed amount.
   * This method will adjust the speed within the constraints of the current gear.
   *
   * @return The updated ManualTransmission object reflecting the new state.
   */
  ManualTransmission decreaseSpeed();

  /**
   * Increases the gear of the vehicle by one.
   * This operation is constrained by the vehicle's current speed and the maximum gear limit.
   *
   * @return The updated ManualTransmission object reflecting the new state.
   */
  ManualTransmission increaseGear();

  /**
   * Decreases the gear of the vehicle by one.
   * This operation is constrained by the vehicle's current speed and the minimum gear limit.
   *
   * @return The updated ManualTransmission object reflecting the new state.
   */
  ManualTransmission decreaseGear();
}
