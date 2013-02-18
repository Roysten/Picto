package nl.saxion.act.playground;

import android.app.Activity;
import android.os.Bundle;

public class MyGameActivity extends Activity {
	private GameView mGameView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        mGameView = (GameView) findViewById(R.id.game);
        mGameView.initNewGame();
    }
}
