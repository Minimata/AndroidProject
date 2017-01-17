package minimata.geosys.dummy;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import java.util.ArrayList;
import java.util.List;

import minimata.geosys.GoogleMapFragment;
import minimata.geosys.MainActivity;

/**
 * Created by alexandre on 29.11.2016.
 * <p>
 * This class lists the amount of settings available for a single type of alarm.
 */

public class Settings extends DummyContent{
    public final List<Setting> ITEMS = new ArrayList<Setting>();
    private final String TAG = "settings object";
    private Context context;
    private MainActivity main;
    private GoogleMapFragment gmap;
    private Bundle args;
    private ArrayList<Setting> widgets;

    public Settings(Bundle args, Context context) {
        // Add some sample items.
        this.context = context;
        this.args = args;
        main = (MainActivity) context;
        gmap = main.getGMapFragment();
        widgets = new ArrayList<>();
        addItem(new Slider(0));
        addItem(new OKButton(1, widgets));
    }

    private void addItem(Setting s) {
        widgets.add(s);
        ITEMS.add(s);
    }

    public abstract class Setting extends DummyItem{
        private Setting(int id, String content) {
            super(id, content);
        }

        public abstract View instantiateWidget();

        public abstract int getValue();
    }

    public class Slider extends Setting {
        private SeekBar seekBar;

        private Slider(int id) {
            super(id, "radius : ");
        }

        public View instantiateWidget() {
            seekBar = new SeekBar(context);
            seekBar.setMinimumWidth(100);
            seekBar.setProgress(0);
            data.put(0, 0);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    gmap.updateRadius(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            return seekBar;
        }

        public int getValue() {
            return seekBar.getProgress();
        }
    }

    public class OKButton extends Setting {
        private ArrayList<Setting> widgets;

        private OKButton(int id, ArrayList<Setting> widgets) {
            super(id, "");
            this.widgets = widgets;
        }

        public View instantiateWidget() {
            Button v = new Button(context);
            v.setText("OK");
            v.setClickable(false);
            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        for (Setting widget : widgets) {
                            data.put(widget.id, widget.getValue());
                        }
                        //data.put(widgets.size(), gmap.getSelectedPosition());
                    }
                    return false;
                }
            });

            return v;
        }

        public int getValue() {
            return 0;
        }
    }
}
