package minimata.geosys.dummy;

import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import minimata.geosys.models.Area;

/**
 * Created by alexandre on 29.11.2016.
 *
 * This class lists all Alarms set by the user
 */

public class Alarms extends DummyContent {

    public Alarms(HashMap<Integer, Area> areas){

        for (Map.Entry<Integer, Area> entry : areas.entrySet()) {
            addItem(createItem(entry.getKey(), "Alarm: " + entry.getKey()));
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
