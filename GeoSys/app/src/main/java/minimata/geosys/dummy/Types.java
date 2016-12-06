package minimata.geosys.dummy;

/**
 * Created by alexandre on 29.11.2016.
 *
 * This class lists all type of settings the user can set to a position.
 * Meaning alarm, silent mode, battery economy mode, whatever.
 */

public class Types extends DummyContent {
    public Types(){
        addItem(createItem(idCount++, "An Alarm to wake up."));
    }

    @Override
    public TypeItem createItem(int id, String description) {
        return new TypeItem(String.valueOf(id), description);
    }

    public class TypeItem extends DummyItem {
        public TypeItem(String id, String content){
            super(id, content);
        }
    }
}
