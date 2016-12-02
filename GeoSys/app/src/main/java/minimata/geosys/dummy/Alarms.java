package minimata.geosys.dummy;

/**
 * Created by alexandre on 29.11.2016.
 */

public class Alarms extends DummyContent {

    public Alarms(){
        // Add some sample items.
        for (int i = 1; i <= 3; i++) {
            addItem(createDummyItem(i, "An alarm"));
        }
    }
}
