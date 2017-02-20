package datetimespinner;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.text.DateFormatter;

/**
 * A implementation of <code>DateFormatter</code> used by <code>DateTimeEditor</code> to format
 * <code>LocalDateTime</code>s from a <code>String</code> or to a <code>String</code>.
 *
 * @author <a href=http://stackoverflow.com/users/4475997/masterblaster?tab=profile>MasterBlaster</a>
 * @version 1.0
 */
public class DateTimeEditorFormatter extends DateFormatter {

    /**
     * The formatter used to format <code>LocalDateTime</code>s to and from <code>String</code>s
     */
    private final DateTimeFormatter formatter;

    /**
     * Creates a <code>DateTimeEditorFormatter</code> with the specified <code>DateTimeFormatter</code>.
     *
     * @param formatter the formatter to be used to format <code>LocalDateTime</code>s to and from <code>String</code>s
     */
    public DateTimeEditorFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public LocalDateTime stringToValue(String text) throws ParseException {
        return stringToValue(text, formatter);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if(value == null) {
            return "";
        }
        LocalDateTime time = null;
        try {
            time = stringToValue(value.toString(), DateTimeFormatter.ISO_DATE_TIME);
        } catch (ParseException e) {
            time = stringToValue(value.toString(), formatter);
        }
        return time != null ? time.format(formatter) : null;
    }

    private LocalDateTime stringToValue(String text, DateTimeFormatter formatter) throws ParseException {
        LocalDateTime time = null;
        try {
            time = LocalDateTime.parse(text, formatter);
        } catch(DateTimeParseException e) {
            try {
                time = LocalDate.parse(text, formatter).atStartOfDay();
            } catch(DateTimeParseException e2) {
                throw new ParseException(e2.getLocalizedMessage(), e2.getErrorIndex());
            }
        }

        return time;
    }

}