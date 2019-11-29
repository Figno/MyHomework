package cn.liguanrui.myhomework;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends Activity {
    public SoundPool soundpool;

    public HashMap<Integer,Integer> soundmap = new HashMap<>();

    public Button left, right, rotate, start, speedUp;   //按钮

    public TextView score, maxScore, level, speed;       //标签

    public int scoreValue,maxScoreValue,levelValue,speedValue;     //标签值

    public String scoreString = "分数：",maxScoreString = "最高分：",levelString = "等级：",playerString = "玩家：",newname;

    public TetrisView view;

    public ShowNextBlockView nextBlockView;

    private DBManager dbManager = new DBManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取各组件和标签值

        soundpool = new SoundPool(4, AudioManager.STREAM_MUSIC,0);
        soundmap.put(1,soundpool.load(this,R.raw.ding1,1));
        soundmap.put(2,soundpool.load(this,R.raw.ding2,1));
        soundmap.put(3,soundpool.load(this,R.raw.ding3,1));
        soundmap.put(4,soundpool.load(this,R.raw.ding4,1));
        Music.play(this, R.raw.bg);


        Intent intent = getIntent();
        newname = intent.getStringExtra("name");


        view = (TetrisView)findViewById(R.id.tetrisView);
        left = (Button)findViewById(R.id.left);
        right = (Button)findViewById(R.id.right);
        rotate = (Button)findViewById(R.id.rotate);
        start = (Button)findViewById(R.id.start);
        speedUp = (Button)findViewById(R.id.speedUp);
        nextBlockView = (ShowNextBlockView)findViewById(R.id.nextBlockView);
        nextBlockView.invalidate();
        score = (TextView)findViewById(R.id.score);
        level = (TextView)findViewById(R.id.level);
        speed = (TextView)findViewById(R.id.speed);
        scoreValue = maxScoreValue =0;
//      levelValue = speedValue = 1;
        score.setText(scoreString + scoreValue);
        level.setText(levelString + 1);
        speed.setText(playerString + newname);

        //设置各按钮的监听器
        //向左移动
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundpool.play(soundmap.get(1),1,1,0,0,1);
                if (view.canMove)
                    view.getFallingBlock().move(-1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.invalidate();
                    }
                });
            }
        });
        //向右移动
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundpool.play(soundmap.get(2),1,1,0,0,1);
                if (view.canMove)
                    view.getFallingBlock().move(1);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.invalidate();
                    }
                });
            }
        });
        //顺时针旋转
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundpool.play(soundmap.get(3),1,1,0,0,1);
                if (view.canMove == false)
                    return;
                TetrisBlock copyOfFallingBlock = view.getFallingBlock().clone();
                copyOfFallingBlock.rotate();
                if (copyOfFallingBlock.canRotate()) {
                    TetrisBlock fallinBlock = view.getFallingBlock();
                    fallinBlock.rotate();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.invalidate();
                    }
                });
            }
        });
        //开始游戏
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.init();
            }
        });
        //加速向下移动(按一下多移一个格子)
        speedUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundpool.play(soundmap.get(4),1,1,0,0,1);
                if (view.canMove) {
                    view.getFallingBlock().setY(view.getFallingBlock().getY() + BlockUnit.UNITSIZE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.invalidate();
                        }
                    });
                }
            }
        });
        view.setFather(this);
        view.invalidate();
    }

public void update(){
    dbManager.Update(newname,Integer.toString(scoreValue));
}


}

