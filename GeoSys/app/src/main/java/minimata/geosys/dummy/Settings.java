package minimata.geosys.dummy;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

import minimata.geosys.MainActivity;

/**
 * Created by alexandre on 29.11.2016.
 *
 * This class lists the amount of settings available for a single type of alarm.
 */

public class Settings extends DummyContent {
    public final List<Setting> ITEMS = new ArrayList<Setting>();

    public Settings(Bundle args) {
        // Add some sample items.
        Log.d("d", args.toString());
        addItem(new Slider(0));
        for (int i = 1; i <= 8; i++) {
            addItem(new Slider(i));
        }
        addItem(new OKButton(9));
    }

    private void addItem(Setting s) {
        ITEMS.add(s);
    }

    public class Setting extends DummyItem {
        public int id;
        public Object widget;
        private Setting(int id, String content) {
            super(id, content);
        }
    }

    public class Slider extends Setting {
        private Slider(int id){
            super(id, "radius : ");
//            widget = new SeekBar();
        }
    }

    public class OKButton extends Setting {
        private OKButton(int id) {
            super(id, "OK IS FINE");
        }
    }
}
