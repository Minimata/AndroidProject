package minimata.geosys.dummy;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import minimata.geosys.model.Area;

/**
 * Created by alexandre on 29.11.2016.
 *
 * This class lists all Alarms set by the user
 */

public class Alarms extends DummyContent {

    public Alarms(Bundle args){
        // Add some sample items.
        ArrayList<Area> areas = (ArrayList<Area>) args.get("areas");
        if(areas != null) {
            for(Area area : areas) {
                addItem(createItem(area.getId(), "Alarm" + area.getId()));
            }
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
