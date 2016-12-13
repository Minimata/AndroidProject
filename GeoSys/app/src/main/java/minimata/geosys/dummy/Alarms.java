package minimata.geosys.dummy;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by alexandre on 29.11.2016.
 *
 * This class lists all Alarms set by the user
 */

public class Alarms extends DummyContent {

    public Alarms(Bundle args){
        Log.d("d", args.toString());
        // Add some sample items.
        int idCount = 0;
        for (int i = 1; i <= 3; i++) {
            addItem(createItem(idCount++, "An alarm"));
        }
    }

    public AlarmItem createItem(int id, String description) {
        return new AlarmItem(id, description);
    }

    public class AlarmItem extends DummyItem {
        public AlarmItem(int id, String content){
            super(id, content);
        }
    }
}
