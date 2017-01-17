package minimata.geosys.dummy;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by alexandre on 29.11.2016.
 *
 * This class lists all type of settings the user can set to a position.
 * Meaning alarm, silent mode, battery economy mode, whatever.
 */

public class Types extends DummyContent {
    public Types(Bundle args) {
        int idCount = 0;
        addItem(createItem(idCount++, "An Alarm to wake up."));
    }

    public TypeItem createItem(int id, String description) {
        return new TypeItem(id, description);
    }

    public class TypeItem extends DummyItem {
        public TypeItem(int id, String content){
            super(id, content);
        }
    }
}
