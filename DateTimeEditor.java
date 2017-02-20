package datetimespinner;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.text.DefaultFormatterFactory;

/**
 * An implementation of <code>JSpinner.DefaultEditor</code> to be used with <code>SpinnerDateTimeModel</code>
 * to provide <code>java.time</code> functionality to a <code>JSpinner</code>.
 *
 * @author <a href=http://stackoverflow.com/users/4475997/masterblaster?tab=profile>MasterBlaster</a>
 * @version 1.0
 */
public class DateTimeEditor extends JSpinner.DefaultEditor {

    /**
     * Creates a <code>DateTimeEditor</code> with the specified <code>JSpinner</code> and the <code>FormatStyle.SHORT</code>
     * format of the localized date time.
     *
     * @param spinner the spinner this editor is to be used with
     */
    public DateTimeEditor(JSpinner spinner) {
        this(spinner, DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
    }

    /**
     * Creates a <code>DateTimeEditor</code> with the specified <code>JSpinner</code> and <code>DateTimeFormatter</code>
     * @param spinner the spinner this editor is to be used with
     * @param format the format the date will be displayed in
     */
    public DateTimeEditor(JSpinner spinner, DateTimeFormatter format) {
        super(spinner);
        if(!(spinner.getModel() instanceof SpinnerDateTimeModel)) {
            throw new IllegalArgumentException("spinner model not a SpinnerDateModel!");
        }
        final SpinnerDateTimeModel model = (SpinnerDateTimeModel) spinner.getModel();
        final DateTimeEditorFormatter formatter = new DateTimeEditorFormatter(format);
        final DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter, formatter, formatter);
        final JFormattedTextField ftf = getTextField();
        ftf.setEditable(true);
        ftf.setFormatterFactory(factory);

        try {
            final String maxString = formatter.valueToString(model.getMinimum());
            final String minString = formatter.valueToString(model.getMaximum());
            ftf.setColumns(Math.max(maxString.length(), minString.length()));
        } catch (ParseException e) {
            // ignore like superclass
        }
    }

}