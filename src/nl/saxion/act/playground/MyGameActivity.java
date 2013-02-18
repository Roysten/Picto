package nl.saxion.act.playground;

import nl.saxion.act.playground.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyGameActivity extends Activity {
	private static final String TAG = "MyActivity";
	private GameView mGameView;
	private TextView tv;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        mGameView = (GameView) findViewById(R.id.game);
        mGameView.setText(tv);
        mGameView.initNewGame();
    }
}
