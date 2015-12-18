package motorola.com.borrowme;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import motorola.com.borrowme.R;
import motorola.com.borrowme.database.dao.ItemsDAO;
import motorola.com.borrowme.database.entities.ItemEntity;

public class ViewItemActivity extends Activity {

    private Button btnSaveItem;
    private EditText viewName;
    private EditText viewDescription;
    private EditText viewCode;

    private ItemsDAO itemsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemsDAO = ItemsDAO.getInstance(this);

        viewName = (EditText) findViewById(R.id.edt_name);
        viewDescription = (EditText) findViewById(R.id.edt_description);
        viewCode = (EditText) findViewById(R.id.edt_code);
        btnSaveItem = (Button) findViewById(R.id.btn_save_item);

        long itemID = getIntent().getLongExtra("ITEM_ID", 0);
        if(itemID != 0){
            ItemEntity itemEntity = itemsDAO.selectById(itemID);
            viewName.setText(itemEntity.getName());
            viewDescription.setText(itemEntity.getDescription());
            viewCode.setText(itemEntity.getCode());
        }

        btnSaveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemEntity itemEntity = new ItemEntity(
                        0000000001, //TODO: GET THIS FROM CONTEXT
                        viewName.getText().toString(),
                        viewDescription.getText().toString(),
                        viewCode.getText().toString());

                itemsDAO.insert(itemEntity);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
    }

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
}
