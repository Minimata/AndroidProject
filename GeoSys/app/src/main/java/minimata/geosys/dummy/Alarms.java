package minimata.geosys.dummy;

/**
 * Created by alexandre on 29.11.2016.
 *
 * This class lists all Alarms set by the user
 */

public class Alarms extends DummyContent {

    public Alarms(){
        // Add some sample items.
        for (int i = 1; i <= 3; i++) {
            addItem(createItem(idCount++, "An alarm"));
        }
    }

    @Override
    public AlarmItem createItem(int id, String description) {
        return new AlarmItem(String.valueOf(id), description);
    }

    public class AlarmItem extends DummyItem {
        public AlarmItem(String id, String content){
            super(id, content);
        }
    }
}
