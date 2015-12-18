package motorola.com.borrowme.database.entities;

/**
 * Created by jonasoliveira on 15/12/15.
 */
public class ItemEntity {

    private long _id;
    private long collectionId;
    private String name;
    private String description;
    private String code;

    public ItemEntity(long _id, long collectionId, String name, String description, String code) {
        this._id = _id;
        this.collectionId = collectionId;
        this.name = name;
        this.description = description;
        this.code = code;
    }

    public ItemEntity(long collectionId, String name, String description, String code) {
        this.collectionId = collectionId;
        this.name = name;
        this.description = description;
        this.code = code;
    }

    public ItemEntity() {}

    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return name;
    }
}
