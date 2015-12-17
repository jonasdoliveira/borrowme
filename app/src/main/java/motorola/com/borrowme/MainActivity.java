package motorola.com.borrowme;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import motorola.com.borrowme.database.dao.CollectionDAO;
import motorola.com.borrowme.database.entities.CollectionEntity;

public class MainActivity extends Activity implements DFragment.InsertCallback {
    ListView listView;
    FragmentManager fm = getFragmentManager();
    CollectionDAO colDAO;
    ArrayAdapter<CollectionEntity> adapter;
    ArrayList<CollectionEntity> collectionEntityArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.collections);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListView Clicked item index
                int itemPosition     = position;
                // ListView Clicked item value
                String  itemValue    = listView.getItemAtPosition(position).toString();
                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        collectionCreated();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_collection) {
            DFragment dFragment = new DFragment();
            // Show DialogFragment
            dFragment.show(fm, "Dialog Fragment");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void collectionCreated() {
        colDAO = CollectionDAO.getInstance(MainActivity.this);
        collectionEntityArrayList = new ArrayList<>(colDAO.selectAll());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, collectionEntityArrayList);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }
}
