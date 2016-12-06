package minimata.geosys.dummy;

/**
 * Created by alexandre on 29.11.2016.
 *
 * This class lists the amount of settings available for a single type of alarm.
 */

public class Settings extends DummyContent {
    public Settings() {
        // Add some sample items.
        addItem(createItem(idCount++, "Slider km setting"));
        for (int i = 1; i <= 8; i++) {
            addItem(createItem(idCount++, "A Setting"));
        }
    }

    @Override
    public SettingItem createItem(int id, String description) {
        return new SettingItem(String.valueOf(id), description);
    }

    public class SettingItem extends DummyItem {
        public SettingItem(String id, String content){
            super(id, content);
        }
    }
}
