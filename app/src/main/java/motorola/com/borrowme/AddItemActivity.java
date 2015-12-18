package motorola.com.borrowme;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import motorola.com.borrowme.database.dao.ItemsDAO;
import motorola.com.borrowme.database.entities.ItemEntity;

public class AddItemActivity extends Activity {

    public static final String COLLECTION_KEY = "COLLECTION";

    private Button btnSaveItem;
    private EditText edtName;
    private EditText edtDescription;
    private EditText edtCode;

    private ItemsDAO itemsDAO;

    private long collectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        collectionId = getIntent().getLongExtra(COLLECTION_KEY, 0);

        itemsDAO = ItemsDAO.getInstance(this);

        edtName = (EditText) findViewById(R.id.edt_name);
        edtDescription = (EditText) findViewById(R.id.edt_description);
        edtCode = (EditText) findViewById(R.id.edt_code);
        btnSaveItem = (Button) findViewById(R.id.btn_save_item);

        long itemID = getIntent().getLongExtra("ITEM_ID", 0);

        if(itemID != 0){
            ItemEntity itemEntity = itemsDAO.selectById(itemID);
            edtName.setText(itemEntity.getName());
            edtDescription.setText(itemEntity.getDescription());
            edtCode.setText(itemEntity.getCode());
        }

        btnSaveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemEntity itemEntity = new ItemEntity(
                        collectionId,
                        edtName.getText().toString(),
                        edtDescription.getText().toString(),
                        edtCode.getText().toString(),
                        0);

                itemsDAO.insert(itemEntity);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
