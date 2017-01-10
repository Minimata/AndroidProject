package minimata.geosys.dummy;

import android.content.Context;
import android.net.sip.SipAudioCall;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import minimata.geosys.MainActivity;
import minimata.geosys.MySettingRecyclerViewAdapter;
import minimata.geosys.SettingFragment;

/**
 * Created by alexandre on 29.11.2016.
 *
 * This class lists the amount of settings available for a single type of alarm.
 */

public class Settings extends DummyContent {
    public final List<Setting> ITEMS = new ArrayList<Setting>();
    private final String TAG = "settings object";
    private Context context;

    public Settings(Bundle args, Context context) {
        // Add some sample items.
        this.context = context;
        Log.d("d", args.toString());
        addItem(new Slider(0));
        addItem(new OKButton(1));
    }

    private void addItem(Setting s) {
        ITEMS.add(s);
    }

    public abstract class Setting extends DummyItem {
        public int id;
        private Setting(int id, String content) {
            super(id, content);
        }
        public abstract View instantiateWidget();
    }

    public class Slider extends Setting {
        private Slider(int id){
            super(id, "radius : ");
        }
        public View instantiateWidget() {
            SeekBar seekBar = new SeekBar(context);
            seekBar.setMinimumWidth(100);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Log.d(TAG, "onProgressChanged: shits happenin yo.");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Log.d(TAG, "SeekBar touched");
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            return seekBar;
        }
    }

    public class OKButton extends Setting {
        private OKButton(int id) {
            super(id, "");
        }
        public View instantiateWidget() {
            Button v = new Button(context);
            v.setText("OK");
            v.setClickable(false);
            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        data.add(0, 100);
                    }
                    return false;
                }
            });

            return v;
        }
    }
}
