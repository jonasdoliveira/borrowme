package motorola.com.borrowme.database.entities;

/**
 * Created by jonasoliveira on 15/12/15.
 */
public class ItemEntity {

    private long _id;
    private String name;
    private String description;
    private String code;

    public ItemEntity(long _id, String name, String description, String code) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.code = code;
    }

    public ItemEntity(String name, String description, String code) {
        this.name = name;
        this.description = description;
        this.code = code;
    }

    public ItemEntity() {}

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
