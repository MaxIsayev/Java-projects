package com.games.arcanoid;

import com.games.arcanoid.objects.Ball;
import com.games.arcanoid.objects.Brick;
import com.games.arcanoid.objects.Racquet;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.SurfaceHolder;

public class GameManager extends Thread {
	private static final int FIELD_WIDTH = 210;
    private static final int FIELD_HEIGHT = 230;
    
    private static final int BRICK_WIDTH = 38;
    private static final int BRICK_HEIGHTH = 25;
    private static final int BRICK_NUM = 20;
    
    /** ����� � ������ �������� ���� */
	private static final long LOSE_PAUSE = 2000;
	
	/** ����� �� ���������� �� ����� */
	private boolean mPaused;
	
	/** ������ ��� ����������� ������ */
	private DrawHelper mDrawScreen;
	 
	/** ������ ��� ��������� ���������� ����*/
	private DrawHelper mDrawGameover;	
	
    /**
     * �������, �� ������� ����� ��������
     */
    private SurfaceHolder mSurfaceHolder;
    
    /**
     * ��������� ������ (����������� ��� ���. �����, ����� ���� ������� ��������� �����, ����� �����������)
     */
    private boolean mRunning;
    
    /**
     * ����� ���������
     */
    private Paint mPaint;
    
    private Paint mScorePaint;
    private Paint mLifePaint;
    
    /**
     * ������������� �������� ����
     */
    private Rect mField;
    
    private Bitmap mBackground;
    
    //�������--------------------------------------
    //�������
    private Racquet mBit;
    //�����
    private Ball mBall;
    //������
    private Brick[] bricks = new Brick[BRICK_NUM];
   
    //---------------------------------------------
    /**
     * �����������
     * @param surfaceHolder ������� ���������
     * @param context �������� ����������
     */
    public GameManager(SurfaceHolder surfaceHolder, Context context)
    {
        mSurfaceHolder = surfaceHolder;
        mRunning = false;
        int i;
        // ������������� ������ ���������
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(2);
        Resources res = context.getResources();
        
        //������������� ��������
        mField = new Rect();
        mBall = new Ball(res.getDrawable(R.drawable.ball));
        mBit = new Racquet(res.getDrawable(R.drawable.bit));
        
        // ����� ��� ������ �����
        mScorePaint = new Paint();
        mScorePaint.setTextSize(20);
        mScorePaint.setStrokeWidth(1);
        mScorePaint.setStyle(Style.FILL);
        mScorePaint.setTextAlign(Paint.Align.RIGHT);
        
        // ����� ��� ������ ������
        mLifePaint = new Paint();
        mLifePaint.setTextSize(20);
        mLifePaint.setStrokeWidth(1);
        mLifePaint.setStyle(Style.FILL);
        mLifePaint.setTextAlign(Paint.Align.LEFT);
        
        //������������� ��������
        for (i=0; i<BRICK_NUM; i++){
        	bricks[i] = new Brick(res.getDrawable(R.drawable.brick));
        }
        
        // ������� ��� ��������� ������
        mDrawScreen = new DrawHelper()
        {
            public void draw(Canvas canvas)
            {
                refreshCanvas(canvas);
            }
        };
        
        // ������� ��� ��������� ����������� ����
        mDrawGameover = new DrawHelper()
        {
            public void draw(Canvas canvas)
            {
                // ������ ��������� ��������� ����
                refreshCanvas(canvas);
     
                // ��������� � ����� ���� ������ ��� �����
                String message = "";
                
                if (bricks[0].nOfHit>=BRICK_NUM){
                	mScorePaint.setColor(Color.GREEN);
                    message = "Clear!";
                    
                    mScorePaint.setTextSize(30);
                    canvas.drawText(message, mField.centerX()+80, mField.centerY(), mScorePaint);     
                }
                else{
                	mScorePaint.setColor(Color.RED);
                	message = "Game over";
                
                	mScorePaint.setTextSize(30);
                	canvas.drawText(message, mField.centerX()+80, mField.centerY(), mScorePaint);   
                }
             }
        };
    }
    
    /*----------------------------------*/
    private interface DrawHelper
    {
        void draw(Canvas canvas);
    }
    
    /*---------------------------------*/
    private void draw(DrawHelper helper)
    {
        Canvas canvas = null;
        try
        {
            // ���������� Canvas-�
            canvas = mSurfaceHolder.lockCanvas();
            synchronized (mSurfaceHolder)
            {
                
                    helper.draw(canvas);
                
            }
        }
        catch (Exception e) { }
        finally
        {
            if (canvas != null)
            {
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
    
    /**
     * ������� ��������� ������
     * @param running
     */
    public void setRunning(boolean running)
    {
        mRunning = running;
    }
    
    @Override
    /**
     * ��������, ����������� � ������
     */
    public void run()
    {   	
    	
        while (mRunning)
        {       
        	if (mPaused) continue;
            updateObjects(); // ��������� �������
            draw(mDrawScreen);            
        }
        draw(mDrawGameover);
    }
    
    /**
     * ������������� ��������� ��������, � ������������ � ��������� ������
     * @param screenHeight ������ ������
     * @param screenWidth ������ ������
     */
    public void initPositions(int screenHeight, int screenWidth)
    {	
    	int i, j, pos;
        int left = (screenWidth - FIELD_WIDTH) / 2;
        int top = (screenHeight - FIELD_HEIGHT) / 2;
        
        mField.set(left, top, left + FIELD_WIDTH, top + FIELD_HEIGHT);
        mBackground = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.RGB_565);
        //����������� ��������
        //���� - center �����  ������� ����
        mBall.setCenterX(mField.centerX());
        mBall.setCenterY(mField.centerY()+60); 
        
        pos = 0;         
        for (i=0; i<BRICK_NUM/5; i++){//0 to 4
        	
        	for(j=0; j<5; j++){//0 to 5
        		bricks[pos].setCenterX(mField.centerX()-86+j*BRICK_WIDTH); 
        		bricks[pos].setTop(mField.top+30+i*BRICK_HEIGHTH);
        		pos++;
        	}        	
        	
        }        
       
        
        //����/�������
        mBit.setCenterX(mField.centerX()); 
        mBit.setBottom(mField.bottom);    
    }
	
    //���������� �������� �� ������
    private void refreshCanvas(Canvas canvas){
    	//����� �������� �����������
    	canvas.drawBitmap(mBackground, 0, 0, null);
    	//������ ����
    	canvas.drawRect(mField, mPaint);
    	
    	//������ ������� �������
    	mBall.draw(canvas);
    	mBit.draw(canvas);    
    	for (int i = 0; i<BRICK_NUM; i++){
    		if(!bricks[i].getInters()){
    			bricks[i].draw(canvas);
    		}
        }
    	// ����� �����
        mScorePaint.setColor(Color.RED);
        canvas.drawText(String.valueOf(mBit.getLife()), mField.centerX(), mField.top + 20, mLifePaint);
        mScorePaint.setColor(Color.GREEN);
        canvas.drawText(String.valueOf(mBit.getScore()), mField.right-25, mField.top + 20, mScorePaint);
    }
    
    //���������� ��������� ������� ��������
    private void updateObjects(){    
    	int tempTop, tempBottom, tempLeft, tempRight;
    	
    	mBall.update();
    	mBit.update();
    	//�� ��������� ����� ������� �� ������� ������
    	placeInBounds(mBit); 
    	
    	for (int i = 0; i<BRICK_NUM; i++){
    		if(!bricks[i].getInters()){
    			bricks[i].update();
    		}
        }
    	

        // �������� ������������ ������ �� �������
    	//��������
        if (mBall.getLeft() <= mField.left)
        {
            mBall.setLeft(mField.left + Math.abs(mField.left - mBall.getLeft()));
            mBall.reflectVertical();
        }
        else if (mBall.getRight() >= mField.right)
        {
            mBall.setRight(mField.right - Math.abs(mField.right - mBall.getRight()));
            mBall.reflectVertical();
        }
        //�������������
        if (mBall.getTop() <= mField.top)
        {
            mBall.setTop(mField.top - Math.abs(mField.top - mBall.getTop()));
            mBall.reflectHorizontal();
        }
        
        // �������� ������������ ������ 
        //� �����
        if (GameObject.intersects(mBall, mBit))
        {
            mBall.setBottom(mBit.getBottom() - Math.abs(mBit.getBottom() - mBall.getBottom()));
            mBall.reflectHorizontal();
        }
        else{
        	//� ��������
        	for (int i = 0; i<BRICK_NUM; i++){
        		if (GameObject.intersects(mBall, bricks[i])){
        			if(!bricks[i].getInters()){  
        				//���� +1
        				mBit.incScore();
        				//N of bricks hit +1
        				bricks[i].nOfHit++;
        				// ��� ���������� ����
        				tempTop = bricks[i].getTop();
        				tempBottom = bricks[i].getBottom(); 
        				tempLeft = bricks[i].getLeft(); 
        				tempRight = bricks[i].getRight();
        				int centerX = (mBall.getLeft()+mBall.getRight())/2;
        				int centerY = (mBall.getTop()+mBall.getBottom())/2;
        				//��� ����� ������ ��� �����?
        				boolean aboveOrBeyond = ((mBall.getTop()<=tempBottom)&&(centerY>=tempBottom)&&(mBall.getBottom()>=tempBottom))||
        					((mBall.getBottom()>=tempTop)&&(centerY<=tempTop)&&(mBall.getTop()<=tempTop));
        				//          ������ ��� �����?
        				boolean leftOrRight = ((mBall.getLeft()<=tempRight)&&(mBall.getRight()>=tempRight)&&(centerX>=tempRight))||
        					((mBall.getRight()>=tempLeft)&&(mBall.getLeft()<=tempLeft)&&(centerX<=tempLeft));
        				//��� ���� ������� || ��� ��� ���� �������	
        				if (aboveOrBeyond&&(!leftOrRight)){	
        					mBall.reflectHorizontal();
        				}	
        				// ��� ������ ������� ||��� ����� �������
        				if (leftOrRight&&(!aboveOrBeyond))
        				{
        					mBall.reflectVertical();
        				
        				}
        				bricks[i].setInters(true);
        			}        			
        		}
        	}
        }
        
        //���������� �����
        if (mBall.getTop() > mBit.getTop())
        {
            mBit.decLife();
            reset();
        }
        
        // �������� ��������� ����
        if ((mBit.getLife() < 0)||(bricks[0].nOfHit>=BRICK_NUM))
        {
            this.mRunning = false;
        }
    }
       
    
    /**
     * �� ��������� ����� ������� �� ������� ������
     */
    private void placeInBounds(Racquet r)
    {
        if (r.getLeft() < mField.left)
            r.setLeft(mField.left);
        else if (r.getRight() > mField.right)
            r.setRight(mField.right);
    }
    
    /*
     * restart
     */
    private void reset()
    {
        // ������ ����� � ����� ������� ����
        mBall.setCenterX(mField.centerX());
        mBall.setCenterY(mField.centerY()+60);
        // ������ ��� ����� ��������� ����
        mBall.resetAngle();
     
        // ������ ������y � �����
        mBall.setCenterX(mField.centerX());
        
        // ������ ������� ����� ��������(����������� � ���� ���)
        for (int i=0; i<BRICK_NUM; i++){
        	bricks[i].setInters(false);
        	bricks[i].nOfHit=0;
        }
        
        
        // ������ �����
        try
        {
            sleep(LOSE_PAUSE);
        }
        catch (InterruptedException iex)
        {
        }
    }
    
    /**
     * ��������� ������� ������
     * @param keyCode ��� ������� ������
     * @return ���� �� ���������� �������
     */
    public boolean doKeyDown(int keyCode)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_A:// A - left
                mBit.setDirection(GameObject.DIR_LEFT);
                return true;                
            case KeyEvent.KEYCODE_DPAD_RIGHT:
            case KeyEvent.KEYCODE_D:// D - right
                mBit.setDirection(GameObject.DIR_RIGHT);
                return true;
            case KeyEvent.KEYCODE_DPAD_CENTER:// center - pause
                mPaused = !mPaused;                
                return true;
            default:
                return false;
        }   
    }
    /**
     * ��������� ���������� ������
     * @param keyCode ��� ������
     * @return ���� �� �������� ����������
     */
    
    public boolean doKeyUp(int keyCode)
    {
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT ||
            keyCode == KeyEvent.KEYCODE_A ||
            keyCode == KeyEvent.KEYCODE_DPAD_RIGHT ||
            keyCode == KeyEvent.KEYCODE_D
            )
        {
        	mBit.setDirection(GameObject.DIR_NONE);
            return true;
        }
        return false;
    }
}
