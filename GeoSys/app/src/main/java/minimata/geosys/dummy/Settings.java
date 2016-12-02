package minimata.geosys.dummy;

/**
 * Created by alexandre on 29.11.2016.
 */

public class Settings extends DummyContent {
    public Settings() {
        // Add some sample items.
        addItem(createDummyItem(0, "Slider km setting"));
        for (int i = 1; i <= 8; i++) {
            addItem(createDummyItem(i, "A Setting"));
        }
    }
}
