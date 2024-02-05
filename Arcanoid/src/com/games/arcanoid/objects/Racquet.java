/**
 * 
 */
package com.games.arcanoid.objects;

import com.games.arcanoid.GameObject;

import android.graphics.drawable.Drawable;

/**
 * @author max
 *
 */
public class Racquet extends GameObject
{
    private static final int DEFAULT_SPEED = 3;
    /** ���������� ������������ ����� */
    private int mScore;
    
    /** ���������� ������ */
    private int mLife;
    
    /** ����������� �������� */
    private int mDirection;
    
    /** ������� ����������� ��������*/
    public void setDirection(int direction)
    {
        mDirection = direction;
    }

    /** 
     * @see com.android.pingpong.objects.GameObject#GameObject(Drawable) 
     */
    public Racquet(Drawable image)
    {
        super(image);
        mLife = 5; //5 ������ �������
        mDirection = DIR_NONE; // ����������� �� ��������� - ���
        mScore = 0; // ����� ���� �� ����������
        mSpeed = DEFAULT_SPEED; // ������ �������� �� ���������
    }

    
    @Override
    protected void updatePoint()
    {
        mPoint.x += mDirection * mSpeed; // ������� ������� �� ��� Ox � ��������������� �������
    }
    
    /** ��������� ���������� ����� ������ */
    public void incScore(){mScore++;}
    
    /** ��������� ���������� ������ ������ */
    public void decLife(){mLife--;}
    
    /** ������ ���������� ������ ������ */
    public int getLife(){ return mLife;}

	public int getScore() {return mScore;}
}
