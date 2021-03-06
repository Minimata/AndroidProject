package minimata.geosys.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * This helps us for polymorphism reasons
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    protected void addItem(DummyItem item) {
        ITEMS.add(item);
    }

    protected DummyItem createItem(int id, String desc) {
        return new DummyItem(id, desc);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public class DummyItem {
        public int id;
        public String content;
        public ArrayList<Object> data;

        public DummyItem(int id, String content) {
            this.id = id;
            this.content = content;
            data = new ArrayList<>();
        }
    }
}
