package pornnatcha.easywallet.model;

/**
 * Created by USER on 10/12/2560.
 */

public class WalletItem {

    public final int id;
    public final String title;
    public final String money;
    public final String picture;

    public WalletItem(int id, String title, String money, String picture) {
        this.id = id;
        this.title = title;
        this.money = money;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return title;
    }
}
