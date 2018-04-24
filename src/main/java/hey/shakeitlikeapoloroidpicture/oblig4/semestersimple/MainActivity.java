package hey.shakeitlikeapoloroidpicture.oblig4.semestersimple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "hey.shakeitlikeapoloroidpicture.oblig4.semestersimple.EXTRA_TEXT";
    public static final String EXTRA_ADDRESS = "hey.shakeitlikeapoloroidpicture.oblig4.semestersimple.EXTRA_ADDRESS";
    Button connectButton;
    EditText nickEditText;
    EditText addressEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void connectOnClick(View view){
        openChatRoom();
    }

    public void openChatRoom(){
        nickEditText = (EditText) findViewById(R.id.nickEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);

        String nick = nickEditText.getText().toString();
        String address = addressEditText.getText().toString();

        Intent intent = new Intent(this, ChatRoomActivity.class);
        intent.putExtra(EXTRA_ADDRESS, address);
        intent.putExtra(EXTRA_TEXT, nick);
        startActivity(intent);

    }
}
