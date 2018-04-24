package hey.shakeitlikeapoloroidpicture.oblig4.semestersimple;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class RecieveMessages extends IntentService {
    public static final String ACTION_TEST = "broadcast";
    static Socket socket;// = new Socket("127.0.0.1", 7800);
    String username;
    String address;
    String serializedMsgIn = "";
    String messageToBroadcast;
    Message msgIn;
    Handler myHandler;
    ObjectInputStream in;
    ObjectOutputStream out;
    Boolean shouldRun = true;


    public RecieveMessages(){
        super("RecieveMessages");
        myHandler = new Handler();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        username = (String) intent.getExtras().get("nick");
        address = (String) intent.getExtras().get("address");
        myHandler.post(new DisplayToast(this, username+address));

        try {
            makeHandshake();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void makeHandshake() throws IOException, ClassNotFoundException {
        try{
            socket = new Socket(InetAddress.getByName(address), 7800);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){
            e.printStackTrace();
        }
        int i =1;
        out.writeObject(Message.LOG_ON+username);//
        while(true){//Har restriksjoner på whileloopen så lenge det bare er en toast
            serializedMsgIn =(String) in.readObject();
           // msgIn = new Message(-1, "emptypayload");
            //msgIn.deserialize(serializedMsgIn);

           // messageToBroadcast=msgIn.getPayload();
          //  myHandler.post(new DisplayToast(this, serializedMsgIn));//for debug

            Intent broadcastIntent = new Intent("broadcast");//ACTION_TEST=broadcast
            broadcastIntent.putExtra(ChatRoomActivity.INCOMING_MESSAGE, serializedMsgIn);
            LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
            myHandler.post(new DisplayToast(this, serializedMsgIn+" b"));




        }


    }

    public static Socket getSocket(){
        return socket;
    }

}
