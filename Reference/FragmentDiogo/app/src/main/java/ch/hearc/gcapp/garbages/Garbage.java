package ch.hearc.gcapp.garbages;

import android.text.TextUtils;

import ch.hearc.gcapp.garbages.categories.GarbageCategory;

/**
 * POJO class representing garbages in this application.
 *
 * A garbage has:
 *
 *   - a name;
 *   - a category.
 */
public class Garbage {

/*------------------------------------------------------------------------------------------------*\
 *                                                                                                *
 *                                          CONSTRUCTORS                                          *
 *                                                                                                *
\*------------------------------------------------------------------------------------------------*/

    public Garbage(String name, GarbageCategory category) {
        setName(name);
        setCategory(category);
    }

/*------------------------------------------------------------------------------------------------*\
 *                                                                                                *
 *                                           PROPERTIES                                           *
 *                                                                                                *
\*------------------------------------------------------------------------------------------------*/

    /**
     * The name for this garbage.
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The category in which belongs this garbage.
     */
    private GarbageCategory category;

    public GarbageCategory getCategory() {
        return category;
    }

    public void setCategory(GarbageCategory category) {
        this.category = category;
    }

/*------------------------------------------------------------------------------------------------*\
 *                                                                                                *
 *                                    OBJECT METHOD OVERRIDES                                     *
 *                                                                                                *
\*------------------------------------------------------------------------------------------------*/

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Garbage)) {
            return false;
        }

        Garbage other = (Garbage) obj;

        return TextUtils.equals(getName(), other.getName());
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (getName() == null ? 0 : getName().hashCode());

        return result;
    }
}
