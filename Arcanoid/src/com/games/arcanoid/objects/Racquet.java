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
    /** Количество заработанных очков */
    private int mScore;
    
    /** Количество жизней */
    private int mLife;
    
    /** Направление движения */
    private int mDirection;
    
    /** Задание направления движения*/
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
        mLife = 5; //5 жизней вначале
        mDirection = DIR_NONE; // Направление по умолчанию - нет
        mScore = 0; // Очков пока не заработали
        mSpeed = DEFAULT_SPEED; // Задали скорость по умолчанию
    }

    
    @Override
    protected void updatePoint()
    {
        mPoint.x += mDirection * mSpeed; // двигаем ракетку по оси Ox в соответствующую сторону
    }
    
    /** Увеличить количество очков игрока */
    public void incScore(){mScore++;}
    
    /** Уменьшить количество жизней игрока */
    public void decLife(){mLife--;}
    
    /** Узнать количество жизней игрока */
    public int getLife(){ return mLife;}

	public int getScore() {return mScore;}
}
