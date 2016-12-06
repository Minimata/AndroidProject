package minimata.geosys.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    protected int idCount = 0;

    /**
     * A map of sample (dummy) items, by ID.
     */
    private final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    protected void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    protected DummyItem createItem(int id, String description) {
        return new DummyItem(String.valueOf(id), description);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public class DummyItem {
        public final String id;
        public final String content;

        public DummyItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
