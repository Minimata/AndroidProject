package ch.hearc.gcapp.garbages;

import java.util.ArrayList;
import java.util.List;

import ch.hearc.gcapp.garbages.categories.GarbageCategory;

/**
 * Since we didn't learn (yet) how to store data in an Android application, we will use this class
 * to store our data in memory.
 */
public class GarbageStore {

    public static final List<Garbage> GARBAGES = new ArrayList<>();

    public static final List<GarbageCategory> GARBAGE_CATEGORIES = new ArrayList<>();

    static {
        // Let's create some data on the fly...
        GarbageCategory categoryPet = new GarbageCategory("PET", "PET recycle bin");
        GarbageCategory categoryIron = new GarbageCategory("Iron", "Iron container");
        GarbageCategory categoryAluminum = new GarbageCategory("Aluminum", "Aluminum container");

        GARBAGE_CATEGORIES.add(categoryPet);
        GARBAGE_CATEGORIES.add(categoryIron);
        GARBAGE_CATEGORIES.add(categoryAluminum);

        GARBAGES.add(new Garbage("Valser Classic", categoryPet));
        GARBAGES.add(new Garbage("Lipton Lemon Ice Tea", categoryPet));
        GARBAGES.add(new Garbage("Coca Cola", categoryPet));

        GARBAGES.add(new Garbage("Canned food", categoryIron));
        GARBAGES.add(new Garbage("Pot lid", categoryIron));
        GARBAGES.add(new Garbage("Canned sardines", categoryIron));

        GARBAGES.add(new Garbage("Yogurt lid", categoryAluminum));
        GARBAGES.add(new Garbage("Knife blade", categoryAluminum));
        GARBAGES.add(new Garbage("Aluminum foil", categoryAluminum));
    }

    /**
     * Utility function to find a garbage by its name.
     */
    public static Garbage findGarbageByName(String name) {
        Garbage result = null;

        for (Garbage garbage : GARBAGES) {
            if (garbage.getName().equals(name)) {
                result = garbage;
            }
        }

        return result;
    }

    /**
     * Utility function to find a garbage category by its name.
     */
    public static GarbageCategory findGarbageCategoryByName(String name) {
        GarbageCategory result = null;

        for (GarbageCategory garbageCategory : GARBAGE_CATEGORIES) {
            if (garbageCategory.getName().equals(name)) {
                result = garbageCategory;
            }
        }

        return result;
    }
}
