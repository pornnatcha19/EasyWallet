package pornnatcha.easywallet;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import pornnatcha.easywallet.adapter.WalletListAdapter;
import pornnatcha.easywallet.db.WalletDbHelper;
import pornnatcha.easywallet.model.WalletItem;

public class MainActivity extends AppCompatActivity {




    private WalletDbHelper mHelper;
    private SQLiteDatabase mDb;

    ImageButton mIncome_button ;
    ImageButton mExpense_button ;

    private ArrayList<WalletItem> mWalletItemList = new ArrayList<>();
    private WalletListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mIncome_button = (ImageButton) findViewById(R.id.mIncome_button);
        mExpense_button = (ImageButton) findViewById(R.id.mExpense_button);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = getIntent();
        //mType = intent.getStringExtra("type");
        mHelper = new WalletDbHelper(this);
        mDb = mHelper.getReadableDatabase();
        loadDataFromDb();//จะคิวรีข้อมูล ทุกแถว ทุกคอลัม
        mAdapter = new WalletListAdapter(
                this,
                R.layout.item,//<4>layout->new->layout reso->จะได้item.xml  <5>สร้างแพคเกจ adapter -> สร้างPhoneListAdapter.class
                mWalletItemList
        );
//อ้างอิงถึงลิสวิว
        ListView iv = (ListView) findViewById(R.id.list_view);
        iv.setAdapter((ListAdapter) mAdapter);



    }//end onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                loadDataFromDb();
                mAdapter.notifyDataSetChanged();
            }
        }
    }
    private void loadDataFromDb() {
        Cursor cursor = mDb.query(

                WalletDbHelper.TABLE_NAME,
                null,//เอามาทุกคอลัม
                 null,
                null,
                null,
                null,
                null
        );

        mWalletItemList.clear();//เคลียข้อมูลเก่าทิ้ง เผือไว้กรณีผู้ใช้แอดข้อมูลเพิ่มมา
//วนลูปเอาข้อมูลออกมา
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(WalletDbHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(WalletDbHelper.COL_TITLE));  //getมาแต่ละคอลัมของแถวนั้นๆ หรือcursor.getString(1); ช่อง1ตือtitle
            String money = cursor.getString(cursor.getColumnIndex(WalletDbHelper.COL_MONEY));  //getมาแต่ละคอลัมของแถวนั้นๆ หรือcursor.getString(1); ช่อง1ตือtitle

            String picture = cursor.getString(cursor.getColumnIndex(WalletDbHelper.COL_PICTURE));  //getมาแต่ละคอลัมของแถวนั้นๆ หรือcursor.getString(1); ช่อง1ตือtitle

            //สร้างโมเดลobj โดยผ่านคอนสตักจอPhoneItem ที่สร้างไว้
            WalletItem item = new WalletItem(id, title, money, picture);
            mWalletItemList.add(item);//ข้อมูลขากdbมาอยู่ในนี้หมดแล้ว
        }
    }
}
