package datetimespinner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import javax.swing.AbstractSpinnerModel;

/**
 * An implementation of <code>AbstractSpinnerModel</code> to be used with <code>DateTimeEditor</code> to provide
 * <code>java.time</code> functionality to a <code>JSpinner</code>.
 *
 * @author <a href=http://stackoverflow.com/users/4475997/masterblaster?tab=profile>MasterBlaster</a>
 * @version 1.0
 */
public class SpinnerDateTimeModel extends AbstractSpinnerModel {

    /**
     * The current <code>LocalDateTime</code>. Used to calculate the next and previous value.
     * Cannot go below the {@link SpinnerDateTimeModel#minimum} or above the {@link SpinnerDateTimeModel#maximum} values.
     *
     * @see SpinnerDateTimeModel#getValue() getValue()
     * @see SpinnerDateTimeModel#setValue(Object) setValue(Object)
     */
    private LocalDateTime value;

    /**
     * The minimum <code>LocalDateTime</code> {@link SpinnerDateTimeModel#value} is allowed to be.
     *
     * @see SpinnerDateTimeModel#getMinimum() getMinimum()
     * @see SpinnerDateTimeModel#setMinimum(LocalDateTime) setMinimum(LocalDateTime)
     */
    private LocalDateTime minimum;

    /**
     * The maximum <code>LocalDateTime</code> {@link SpinnerDateTimeModel#value} is allowed to be.
     *
     * @see SpinnerDateTimeModel#getMaximum() getMaximum()
     * @see SpinnerDateTimeModel#setMaximum(LocalDateTime) setMaximum(LocalDateTime)
     */
    private LocalDateTime maximum;

    /**
     * The unit of time by witch {@link SpinnerDateTimeModel#value} is incremented by one
     * when calculating the next and previous value.
     *
     * @see SpinnerDateTimeModel#getUnit() getUnit()
     * @see SpinnerDateTimeModel#setUnit(TemporalUnit) setUnit(TemporalUnit)
     * @see SpinnerDateTimeModel#getNextValue() getNextValue()
     * @see SpinnerDateTimeModel#getPreviousValue() getPreviousValue()
     */
    private TemporalUnit unit;

    /**
     * Creates a <code>SpinnerDateTimeModel</code> with an initial value of the current <code>LocalDateTime</code>.
     */
    public SpinnerDateTimeModel() {
        this(LocalDateTime.now());
    }

    /**
     * Creates a <code>SpinnerDateTimeModel</code> with the specified intitial value, no minimum or maximum, and an
     * increment unit of <code>ChromoUnit.DAYS</code>.
     * @param initialValue the intial value of this model
     */
    public SpinnerDateTimeModel(LocalDateTime initialValue) {
        this(initialValue, null, null, ChronoUnit.DAYS);
    }

    /**
     * Creates a <code>SpinnerDateTimeModel</code> with the specified initial value, minimum, maximum, and increment unit.
     *
     * @param initialValue the initial value of this model
     * @param minimum the lowest value the current value is allowed to go, or <code>null</code> if there is no minimum
     * @param maximum the highest value the current value is allowed to go, or <code>null</code> if there is no maximum
     * @param unit the unit by which the current value is incremented when calculating the previous and next values.
     * @throws IllegalArgumentException if <code>initialValue</code> or <code>unit</code> are <code>null</code>
     */
    public SpinnerDateTimeModel(LocalDateTime initialValue, LocalDateTime minimum, LocalDateTime maximum, TemporalUnit unit) {
        if(initialValue == null) {
            throw new IllegalArgumentException("initialValue is null!");
        } if(unit == null) {
            throw new IllegalArgumentException("unit is null!");
        } if(!(((minimum == null) || (minimum.compareTo(value) <= 0)) && ((maximum == null) || (maximum.compareTo(value) >= 0)))) {
            throw new IllegalArgumentException("(start <= value <= end) is false");
        }

        value = initialValue;
        this.minimum = minimum;
        this.maximum = maximum;
        this.unit = unit;
    }

    @Override
    public LocalDateTime getNextValue() {
        final LocalDateTime possible = value.plus(1L, unit);
        return maximum == null || possible.compareTo(maximum) < 1 ? possible : null;
    }

    @Override
    public LocalDateTime getPreviousValue() {
        final LocalDateTime possible = value.minus(1L, unit);
        return minimum == null || possible.compareTo(minimum) > -1 ? possible : null;
    }

    @Override
    public LocalDateTime getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if((value == null) || !(value instanceof LocalDateTime)) {
            throw new IllegalArgumentException("Illegal value!");
        }
        if (!this.value.equals(value)) {
            this.value = (LocalDateTime) value;
            fireStateChanged();
        }
    }

    /**
     * Gets the minimum value that the current value cannot go below.
     *
     * @return the minimum value
     * @see SpinnerDateTimeModel#minimum minimum
     * @see SpinnerDateTimeModel#setMinimum(LocalDateTime) setMinimum(LocalDateTime)
     */
    public LocalDateTime getMinimum() {
        return minimum;
    }

    /**
     * Sets the minimum value that the current value cannot go below.
     *
     * @param minimum the minimum value
     * @see SpinnerDateTimeModel#minimum minimum
     * @see SpinnerDateTimeModel#getMinimum() getMinimum()
     */
    public void setMinimum(LocalDateTime minimum) {
        this.minimum = minimum;
    }

    /**
     * Gets the maximum value that the current value cannot go above.
     *
     * @return the maximum value
     * @see SpinnerDateTimeModel#maximum maximum
     * @see SpinnerDateTimeModel#setMaximum(LocalDateTime) setMaximum(LocalDateTime)
     */
    public LocalDateTime getMaximum() {
        return maximum;
    }

    /**
     * Sets the maximum value that the current value cannot go above.
     *
     * @param maximum the maximum value
     * @see SpinnerDateTimeModel#maximum maximum
     * @see SpinnerDateTimeModel#getMaximum() getMaximum()
     */
    public void setMaximum(LocalDateTime maximum) {
        this.maximum = maximum;
    }

    /**
     * Gets the time unit used to increment the current value when calculating
     * the previous and next values
     *
     * @return the unit
     * @see SpinnerDateTimeModel#unit unit
     * @see SpinnerDateTimeModel#setUnit(TemporalUnit) setUnit(TemporalUnit)
     */
    public TemporalUnit getUnit() {
        return unit;
    }

    /**
     * Sets the time unit used to increment the current value when calculating
     * the previous and next values
     *
     * @param unit the unit
     * @see SpinnerDateTimeModel#unit
     * @see SpinnerDateTimeModel#getUnit() getUnit()
     */
    public void setUnit(TemporalUnit unit) {
        this.unit = unit;
    }

}