package motorola.com.borrowme;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import motorola.com.borrowme.database.dao.ItemsDAO;
import motorola.com.borrowme.database.dao.PersonDAO;
import motorola.com.borrowme.database.entities.ItemEntity;
import motorola.com.borrowme.database.entities.PersonEntity;

public class AddPersonActivity extends Activity {

    private Button btnSavePerson;
    private EditText edtName;
    private EditText edtPhone;
    private EditText edtEmail;

    private PersonDAO personsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        personsDAO = PersonDAO.getInstance(this);

        edtName = (EditText) findViewById(R.id.edt_name);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        btnSavePerson = (Button) findViewById(R.id.btn_save_person);

        btnSavePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonEntity personEntity = new PersonEntity(
                        edtName.getText().toString(),
                        edtPhone.getText().toString(),
                        edtEmail.getText().toString());

                personsDAO.insert(personEntity);
                finish();
            }
        });

    }
}
