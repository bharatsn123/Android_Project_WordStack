

package com.google.engedu.wordstack;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Stack;

public class StackedLayout extends LinearLayout {

    private Stack<View> tiles = new Stack();

    public StackedLayout(Context context) {
        super(context);
    }

    public void push(View tile) {
        /**
         **  YOUR CODE GOES HERE
         **/

          if(!tiles.empty())
              removeView(peek());

        tiles.push(tile);
        addView(tile);
    }

    public View pop() {
        View popped = tiles.pop();
        /**
         **  YOUR CODE GOES HERE
         **/
        removeView(popped);
        if(!tiles.empty())
            addView(tiles.peek());

        return popped;
    }

    public View peek() {
        return tiles.peek();
    }

    public boolean empty() {
        return tiles.empty();
    }

    public void clear() {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
    }
}
