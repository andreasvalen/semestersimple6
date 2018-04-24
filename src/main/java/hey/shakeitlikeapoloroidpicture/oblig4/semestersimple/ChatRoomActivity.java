package hey.shakeitlikeapoloroidpicture.oblig4.semestersimple;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatRoomActivity extends AppCompatActivity {
    public static final String SEND_NICK = "hey.shakeitlikeapoloroidpicture.oblig4.semestersimple.SEND_NICK";
    public static final String INCOMING_MESSAGE ="hey.shakeitlikeapoloroidpicture.oblig4.semestersimple.INCOMING_MESSAGE";
    TextView chatchat;
    Button sendButton;
    EditText sendEditText;
    String serializedMsgFromBroadcast="";
    String textToSend;
    static Socket socket;
    Integer type;
    Message msgFromBroadcast;

    ObjectOutputStream out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Intent intent = getIntent();
        String nick = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        String address = intent.getStringExtra(MainActivity.EXTRA_ADDRESS);
        chatchat = (TextView) findViewById(R.id.chatchatTextView);

        Intent intentRecieve = new Intent(this, RecieveMessages.class);
        intentRecieve.putExtra("nick", nick);
        intentRecieve.putExtra("address", address);
        startService(intentRecieve);

        IntentFilter myIntentFilter = new IntentFilter("broadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(new MyBroadcastReciever(), myIntentFilter);
    }

    public void sendButtonOnClick(View view){
        sendEditText = (EditText) findViewById(R.id.sendEditText);
        textToSend = sendEditText.getText().toString();
        type=Message.CHAT_MESSAGE;

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        socket = RecieveMessages.getSocket();
                        try {
                            out = new ObjectOutputStream(socket.getOutputStream());
                            out.writeObject(type+textToSend);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();




    }

    public class MyBroadcastReciever extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String broadcastMsg = (String) intent.getExtras().get(INCOMING_MESSAGE);
            chatchat.append("\n"+broadcastMsg);
            msgFromBroadcast = new Message(-1, "emptypayload");
            msgFromBroadcast.deserialize(broadcastMsg);
            chatchat.append("\n onrecieve type: "+msgFromBroadcast.type+", payload: "+msgFromBroadcast.payload);

            handleRecievedMessage(msgFromBroadcast);

        }


    }

    private void handleRecievedMessage(Message fromServer) {
        //GET_LIST
        if (fromServer.type==0) {

        }
        //LOG_ON
        if (fromServer.type==1) {

        }
        //CHAT_MESSAGE
        if (fromServer.type==2) {
            chatchat.append("\n"+fromServer.payload+"appended from handlerecmsg");
        }
        //LOG_OFF
        if (fromServer.type==3) {

        }
        if (fromServer.type==4) {

        }
        if (fromServer.type==5) {

        }
        else{
            chatchat.append("ingen type registrert"+fromServer.type);
        }
    }
}
