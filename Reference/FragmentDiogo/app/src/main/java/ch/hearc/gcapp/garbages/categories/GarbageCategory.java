package ch.hearc.gcapp.garbages.categories;

import android.text.TextUtils;

/**
 * POJO class representing garbage categories in this application.
 *
 * A garbage category has:
 *
 *   - a name;
 *   - a garbage can name.
 */
public class GarbageCategory {

/*------------------------------------------------------------------------------------------------*\
 *                                                                                                *
 *                                          CONSTRUCTORS                                          *
 *                                                                                                *
\*------------------------------------------------------------------------------------------------*/

    public GarbageCategory(String name, String garbageCanName) {
        setName(name);
        setGarbageCanName(garbageCanName);
    }

/*------------------------------------------------------------------------------------------------*\
 *                                                                                                *
 *                                           PROPERTIES                                           *
 *                                                                                                *
\*------------------------------------------------------------------------------------------------*/

    /**
     * The name of this garbage category.
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * The garbage can name in which this kind of garbage must be thrown in.
     */
    private String garbageCanName;

    public String getGarbageCanName() {
        return garbageCanName;
    }

    public void setGarbageCanName(String garbageCanName) {
        this.garbageCanName = garbageCanName;
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

        if (!(obj instanceof GarbageCategory)) {
            return false;
        }

        GarbageCategory other = (GarbageCategory) obj;

        return TextUtils.equals(getName(), other.getName());
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = 31 * result + (getName() == null ? 0 : getName().hashCode());

        return result;
    }
}
