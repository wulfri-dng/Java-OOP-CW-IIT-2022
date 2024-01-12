import java.io.Serial;
import java.io.Serializable;
import java.security.Key;
import java.util.ArrayList;

public class Config implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final ArrayList<String> imageIdList;
    private final Key imageEncryptKey;

    public Config(ArrayList<String> imageIdList, Key imageEncryptKey) {
        this.imageIdList = imageIdList;
        this.imageEncryptKey = imageEncryptKey;
    }

    public ArrayList<String> getImageIdList() {
        return imageIdList;
    }

    public Key getImageEncryptKey() {
        return imageEncryptKey;
    }
}
