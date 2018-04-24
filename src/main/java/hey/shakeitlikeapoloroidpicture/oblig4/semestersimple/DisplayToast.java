package hey.shakeitlikeapoloroidpicture.oblig4.semestersimple;

import android.content.Context;
import android.widget.Toast;

public class DisplayToast implements Runnable {
    private final Context context;
    String textToDisplay;

    public DisplayToast(Context context, String text){
        this.context = context;
        textToDisplay = text;
    }

    public void run(){
        Toast.makeText(context, textToDisplay, Toast.LENGTH_SHORT).show();
    }
}
