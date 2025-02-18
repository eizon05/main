package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.currency.Rate;
import seedu.address.model.currency.Symbol;
import seedu.address.model.itinerary.Name;

/**
 * Jackson friendly version of {@code CustomisedCurrency}.
 */
public class JsonAdaptedCurrency {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CustomisedCurrency's %s field is missing!";

    private final String name;
    private final String symbol;
    private final double rate;

    /**
     * Constructs a {@code JsonAdaptedCustomisedCurrency} with the given CustomisedCurrency details.
     */
    @JsonCreator
    public JsonAdaptedCurrency(@JsonProperty("name") String name,
                               @JsonProperty("symbol") String symbol,
                               @JsonProperty("rate") Double rate) {
        this.name = name;
        this.symbol = symbol;
        this.rate = rate;

    }

    /**
     * Converts a given {@code CustomisedCurrency} into this class for Jackson use.
     */
    public JsonAdaptedCurrency(CustomisedCurrency source) {
        this.name = source.getName().toString();
        this.symbol = source.getSymbol().toString();
        this.rate = source.getRate().getValue();
    }

    /**
     * Converts this Jackson-friendly adapted day object into the model's {@code CustomisedCurrency} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted day.
     */
    public CustomisedCurrency toModelType() throws IllegalValueException {

        final String modelName = name;
        final String modelSymbol = symbol;
        final String modelRate = String.valueOf(rate);

        return new CustomisedCurrency(new Name(modelName),
                new Symbol(modelSymbol), new Rate(modelRate));
    }

}
