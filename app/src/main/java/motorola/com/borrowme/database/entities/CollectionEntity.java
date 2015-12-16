package motorola.com.borrowme.database.entities;

/**
 * Created by jonasoliveira on 15/12/15.
 */
public class CollectionEntity {

    private long _id;
    private String name;

    public CollectionEntity(long _id, String name) {
        this._id = _id;
        this.name = name;
    }

    public CollectionEntity() {}

    public CollectionEntity(String name) {
        this.name = name;
    }

    public long get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
