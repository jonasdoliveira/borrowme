package motorola.com.borrowme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.opengl.ETC1;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import motorola.com.borrowme.database.dao.ItemsDAO;
import motorola.com.borrowme.database.entities.ItemEntity;

public class ItemsActivity extends Activity {

    public static final String COLLECTION_KEY = "COLLECTION";

    private ListView lvItems;


    private ArrayList<ItemEntity> itemsList = new ArrayList<>();
    private ItemsDAO itemsDAO;
    private ArrayAdapter<ItemEntity> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        itemsDAO = ItemsDAO.getInstance(this);

        lvItems = (ListView) findViewById(R.id.lv_items);

        itemsAdapter = new ArrayAdapter<ItemEntity>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, itemsList);

        itemsAdapter.addAll(itemsDAO.selectAll());
        lvItems.setAdapter(itemsAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), AddItemActivity.class);
                i.putExtra("ITEM_ID", itemsAdapter.getItem(position).get_id());
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new_item) {
            Intent i = new Intent(this, AddItemActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        itemsList = new ArrayList<>(itemsDAO.selectAll());

        itemsAdapter.clear();
        itemsAdapter.addAll(itemsDAO.selectAll());
        itemsAdapter.notifyDataSetChanged();

        super.onResume();
    }
}
