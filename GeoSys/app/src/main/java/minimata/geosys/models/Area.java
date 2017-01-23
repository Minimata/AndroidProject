package minimata.geosys.models;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Karim Luy on 22/01/2017.
 *
 * This retains the position {@link LatLng} and the radius of an area.
 */

public class Area implements Serializable {

    private int id;
    private transient LatLng position;
    private int radius;

    public Area(int id, LatLng position, int radius) {
        Log.d("YOLO", "Creating with: " + position.latitude + ", " + position.longitude);
        this.id = id;
        this.position = position;
        this.radius = radius;
    }

    public int getId() {
        return id;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeDouble(position.latitude);
        out.writeDouble(position.longitude);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        position = new LatLng(in.readDouble(), in.readDouble());
    }
}
