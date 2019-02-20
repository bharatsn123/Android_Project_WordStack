

package com.google.engedu.wordstack;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private static final int WORD_LENGTH = 5;
    public static final int LIGHT_BLUE = Color.rgb(176, 200, 255);
    public static final int LIGHT_GREEN = Color.rgb(200, 255, 200);
    private ArrayList<String> words = new ArrayList<>();
    private Random random = new Random();
    private StackedLayout stackedLayout;
    private String word1, word2;
    private Stack<LetterTile> placedtiles=new Stack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null) {
                String word = line.trim();
                /**
                 **  YOUR CODE GOES HERE
                 **/
                if(word.length()==5) //--
                    words.add(word); //--
            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        LinearLayout verticalLayout = (LinearLayout) findViewById(R.id.vertical_layout);
        stackedLayout = new StackedLayout(this);
        verticalLayout.addView(stackedLayout, 3);

        View word1LinearLayout = findViewById(R.id.word1);
        //word1LinearLayout.setOnTouchListener(new TouchListener());
        word1LinearLayout.setOnDragListener(new DragListener());
        View word2LinearLayout = findViewById(R.id.word2);
        //word2LinearLayout.setOnTouchListener(new TouchListener());
        word2LinearLayout.setOnDragListener(new DragListener());
    }

    private class TouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN && !stackedLayout.empty()) {
                LetterTile tile = (LetterTile) stackedLayout.peek();
                tile.moveToViewGroup((ViewGroup) v);
                if (stackedLayout.empty()) {
                    TextView messageBox = (TextView) findViewById(R.id.message_box);
                    messageBox.setText(word1 + " " + word2);
                }
                /**
                 **  YOUR CODE GOES HERE
                 **/
                placedtiles.push(tile);
                return true;
            }
            return false;
        }
    }

    private class DragListener implements View.OnDragListener {

        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(LIGHT_GREEN);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(Color.WHITE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign Tile to the target Layout
                    LetterTile tile = (LetterTile) event.getLocalState();
                    tile.moveToViewGroup((ViewGroup) v);
                    if (stackedLayout.empty()) {
                        TextView messageBox = (TextView) findViewById(R.id.message_box);
                        messageBox.setText(word1 + " " + word2);
                    }
                    /**
                     **  YOUR CODE GOES HERE
                     **/
                    placedtiles.push(tile);

                    return true;
            }
            return false;
        }
    }

    public boolean onStartGame(View view) {
        TextView messageBox = (TextView) findViewById(R.id.message_box);
        messageBox.setText("Game started");
        /**
         **  YOUR CODE GOES HERE
         **/
        stackedLayout.removeAllViews();
        LinearLayout Lword1=(LinearLayout)findViewById(R.id.word1);
        LinearLayout Lword2=(LinearLayout)findViewById(R.id.word2);
        Lword1.removeAllViews();
        Lword2.removeAllViews();
        //the above code clears the old tiles from all three sections
        int num1=random.nextInt(words.size())+1;
        int num2=random.nextInt(words.size())+1;
        word1=words.get(num1);
        word2=words.get(num2);
        TextView w1=(TextView)findViewById(R.id.textView);
        TextView w2=(TextView)findViewById(R.id.textView2);
        w1.setText(word1);
        w2.setText(word2);
        String onetwo="";
        num1=0;
        num2=0;
        int i=0,d;

        while(num1<WORD_LENGTH||num2<WORD_LENGTH)
        {
            d=random.nextInt(2);
            if(d==0&&num1<WORD_LENGTH) {
                onetwo=onetwo+word1.charAt(num1);
                num1++; i++;
            }
            else if(num2<WORD_LENGTH) {
                onetwo = onetwo + word2.charAt(num2);
                num2++; i++;
            }
                else if(i==WORD_LENGTH*2)
                break;
        }

        messageBox.setText(onetwo);
        char ch;
        for(i=onetwo.length()-1;i>=0;i--) {
            LetterTile t = new LetterTile(MainActivity.this, onetwo.charAt(i));
            stackedLayout.push(t);
        }
        return true;
    }

    public boolean onUndo(View view) {
        /**
         **  YOUR CODE GOES HERE
         **/
        if(placedtiles.empty())
        {
            return  false;
        }
        LetterTile popped=placedtiles.pop();
        popped.moveToViewGroup(stackedLayout);


        return true;
    }
}
