package seedu.address.ui.currency;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.model.currency.CustomisedCurrency;


/**
 * TODO: Implement display for inventory and booking labels.
 */
public class UnselectedCurrencyCard extends CurrencyCard {
    private static final String FXML = "currency/CurrencyCard.fxml";

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label rateLabel;
    @FXML
    private VBox propertiesContainer;

    private CustomisedCurrency currency;
    private Index displayedIndex;

    public UnselectedCurrencyCard(CustomisedCurrency currency, Index displayedIndex) {
        super(FXML, currency, displayedIndex);
    }

}
