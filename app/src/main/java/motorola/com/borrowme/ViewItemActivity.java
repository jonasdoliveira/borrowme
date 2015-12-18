package motorola.com.borrowme;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import motorola.com.borrowme.R;
import motorola.com.borrowme.database.dao.ItemsDAO;
import motorola.com.borrowme.database.dao.PersonDAO;
import motorola.com.borrowme.database.entities.ItemEntity;
import motorola.com.borrowme.database.entities.PersonEntity;

public class ViewItemActivity extends Activity {

    long itemID;

    private Button btnBorrowItem;
    private Button btnReturnItem;
    private TextView viewName;
    private TextView viewDescription;
    private TextView viewCode;
    private TextView contentPerson;
    private TextView contentPhone;
    private TextView contentEmail;
    private View whenBorrowed;
    private View whenNotBorrowed;

    private ItemsDAO itemsDAO;
    private PersonDAO personDAO;
    private PersonEntity personEntity;
    private ItemEntity itemEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);


        itemsDAO = ItemsDAO.getInstance(this);
        personDAO = PersonDAO.getInstance(this);


        viewName = (TextView) findViewById(R.id.view_name);
        viewDescription = (TextView) findViewById(R.id.view_description);
        viewCode = (TextView) findViewById(R.id.view_code);
        
        contentPerson = (TextView) findViewById(R.id.content_person);
        contentPhone = (TextView) findViewById(R.id.content_phone);
        contentEmail = (TextView) findViewById(R.id.content_email);

        btnBorrowItem = (Button) findViewById(R.id.btn_borrow_item);
        btnReturnItem = (Button) findViewById(R.id.btn_return_item);

        whenBorrowed = findViewById(R.id.view_when_borrowed);
        whenNotBorrowed = findViewById(R.id.view_when_not_borrowed);

        itemID = getIntent().getLongExtra("ITEM_ID", 0);
        if(itemID != 0){
            itemEntity = itemsDAO.selectById(itemID);
            viewName.setText(itemEntity.getName());
            viewDescription.setText(itemEntity.getDescription());
            viewCode.setText(itemEntity.getCode());

            if(itemEntity.getPersonId() != -1) {
                personEntity = personDAO.selectById(itemEntity.getPersonId());
                contentPerson.setText(personEntity.getName());
                contentPhone.setText(personEntity.getPhone());
                contentEmail.setText(personEntity.getEmail());
                whenBorrowed.setVisibility(View.VISIBLE);
                whenNotBorrowed.setVisibility(View.GONE);
            } else {
                whenBorrowed.setVisibility(View.GONE);
                whenNotBorrowed.setVisibility(View.VISIBLE);
            }
        }

        btnBorrowItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: TRANSITION TO PEOPLE ACTIVITY
            }
        });

        btnReturnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemEntity.setPersonId(-1);
                itemsDAO.update(itemEntity);
                whenBorrowed.setVisibility(View.GONE);
                whenNotBorrowed.setVisibility(View.VISIBLE);
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
