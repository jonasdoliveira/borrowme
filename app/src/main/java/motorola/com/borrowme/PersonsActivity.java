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
import motorola.com.borrowme.database.dao.PersonDAO;
import motorola.com.borrowme.database.entities.ItemEntity;
import motorola.com.borrowme.database.entities.PersonEntity;

public class PersonsActivity extends Activity {

    public static final String ITEM_KEY = "ITEM";

    long itemId;

    private ListView lvPersons;

    private ArrayList<PersonEntity> personsList = new ArrayList<>();
    private PersonDAO personsDAO;
    private ArrayAdapter<PersonEntity> personsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persons);

        personsDAO = PersonDAO.getInstance(this);

        lvPersons = (ListView) findViewById(R.id.lv_persons);

        personsAdapter = new ArrayAdapter<PersonEntity>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, personsList);

        personsAdapter.addAll(personsDAO.selectAll());
        lvPersons.setAdapter(personsAdapter);

        lvPersons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                ItemEntity itemEntity = ItemsDAO.getInstance(PersonsActivity.this).selectById(itemId);
                itemEntity.setPersonId(personsList.get(position).get_id());
                ItemsDAO.getInstance(PersonsActivity.this).update(itemEntity);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_persons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new_person) {
            Intent i = new Intent(this, AddPersonActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        personsList = new ArrayList<>(personsDAO.selectAll());

        personsAdapter.clear();
        personsAdapter.addAll(personsDAO.selectAll());
        personsAdapter.notifyDataSetChanged();

        super.onResume();
    }
}
