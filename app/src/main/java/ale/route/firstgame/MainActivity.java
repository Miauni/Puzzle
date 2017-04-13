package ale.route.firstgame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import ale.route.firstgame.model.Game;
import ale.route.firstgame.model.GameEngine;

import static java.lang.Integer.parseInt;

public class MainActivity extends Activity implements View.OnClickListener, Observer {
    GameEngine game;
    Button[][] buttons;
    int count=0;

    int x,y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        game = new GameEngine(4);
        game.addObserver(this);

        super.onCreate(savedInstanceState);

        buttons=new Button[4][4];
        buttons = new Button[][]{
                {
                        buttons[0][0] = (Button) findViewById(R.id.button1),
                        buttons[0][1] = (Button) findViewById(R.id.button2),
                        buttons[0][2] = (Button) findViewById(R.id.button3),
                        buttons[0][3] = (Button) findViewById(R.id.button4)
                },
                {
                        buttons[1][0] = (Button) findViewById(R.id.button5),
                        buttons[1][1] = (Button) findViewById(R.id.button6),
                        buttons[1][2] = (Button) findViewById(R.id.button7),
                        buttons[1][3] = (Button) findViewById(R.id.button8),
                },
                {
                        buttons[2][0] = (Button) findViewById(R.id.button9),
                        buttons[2][1] = (Button) findViewById(R.id.button10),
                        buttons[2][2] = (Button) findViewById(R.id.button11),
                        buttons[2][3] = (Button) findViewById(R.id.button12)
                },
                {
                        buttons[3][0] = (Button) findViewById(R.id.button13),
                        buttons[3][1] = (Button) findViewById(R.id.button14),
                        buttons[3][2] = (Button) findViewById(R.id.button15),
                        buttons[3][3] = (Button) findViewById(R.id.button16)
                }
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setTag(new Integer(i * 4 + j + 1));

                buttons[i][j].setOnClickListener(this);
            }
        }
        gameChanged();

    }

    public void play(View view){
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(i);
    }

    public void gameChanged()
    {
        int[][] data = game.getData();
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
                if(data[i][j] == 0)
                    buttons[i][j].setText("");
                else {
                    buttons[i][j].setText("" + data[i][j]);

                }
    }

    @Override
    public void onClick(View view) {
        //view.animate().rotation(360);
        int tag = Integer.parseInt(view.getTag().toString()) - 1;
        x=tag/4;
        y=tag%4;
        if(game.isEmpty(x,y)){
            Toast.makeText(getBaseContext(), "Empty cell", Toast.LENGTH_SHORT).show();
        }
        if(!game.possibleMove(x,y)){
            Toast.makeText(getBaseContext(), "Move not possible", Toast.LENGTH_SHORT).show();
        }
        if(!game.isEmpty(x,y) && game.possibleMove(x,y))
            count++;
        game.makeMove(x, y);

    }

    @Override
    public void update(Observable o, Object arg) {
        if (game.isFinished()) {
            Toast.makeText(getBaseContext(), "YOU WON THE GAME !!!", Toast.LENGTH_SHORT).show();
        }

        gameChanged();
        TextView tw = (TextView) findViewById(R.id.textView);
        tw.setText("Nr. of moves: "+count);


    }
}
