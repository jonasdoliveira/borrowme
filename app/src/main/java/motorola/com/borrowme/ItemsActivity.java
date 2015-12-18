package motorola.com.borrowme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import motorola.com.borrowme.database.dao.ItemsDAO;
import motorola.com.borrowme.database.entities.ItemEntity;

public class ItemsActivity extends Activity {

    public static final String COLLECTION_KEY = "COLLECTION";

    long currentCollectionId;

    private ListView lvItems;


    private ArrayList<ItemEntity> itemsList = new ArrayList<>();
    private ItemsDAO itemsDAO;
    private ArrayAdapter<ItemEntity> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        lvItems = (ListView) findViewById(R.id.lv_items);

        currentCollectionId = getIntent().getLongExtra(COLLECTION_KEY, 0);

        itemsDAO = ItemsDAO.getInstance(this);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ViewItemActivity.class);
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
            i.putExtra(AddItemActivity.COLLECTION_KEY, currentCollectionId);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        itemsList = new ArrayList<>(itemsDAO.selectByCollection(currentCollectionId));

        itemsAdapter = new ArrayAdapter<ItemEntity>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, itemsList);

        lvItems.setAdapter(itemsAdapter);

        itemsAdapter.notifyDataSetChanged();

        super.onResume();
    }
}
