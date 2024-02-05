package com.games.arcanoid.objects;

import java.util.Random;

import com.games.arcanoid.GameObject;

import android.graphics.drawable.Drawable;

public class Ball extends GameObject
{
    private static final int DEFAULT_SPEED = 5;
    private static final int PI = 180;

    /** Угол, который составляет направление полета шарика с осью Ox */
    private int mAngle;

    
    public Ball(Drawable image)
    {
        super(image);
        
        mSpeed = DEFAULT_SPEED; // задали скорость по умолчанию
        mAngle = getRandomAngle(); // задали случайный начальный угол
    }

    
    @Override
    protected void updatePoint()
    {
        double angle = Math.toRadians(mAngle);
        
        mPoint.x += (int)Math.round(mSpeed * Math.cos(angle));
        mPoint.y -= (int)Math.round(mSpeed * Math.sin(angle));
    }
    
    /** Генерация случайного угла в промежутке [95, 110]U[275,290] */
    private int getRandomAngle()
    {
        Random rnd = new Random(System.currentTimeMillis());
        
        return rnd.nextInt(1) * PI + PI / 2 + rnd.nextInt(45) + 20;
    }
    
    /** Отражение мячика от вертикали */
    public void reflectVertical()
    {
        if (mAngle > 0 && mAngle < PI)
            mAngle = PI - mAngle;
        else
            mAngle = 3 * PI - mAngle;
    }
    
    /** Отражение мячика от горизонтали */
    public void reflectHorizontal()
    {
        mAngle = 2 * PI - mAngle;
    }
    
    /** угол при restart level */
    public void resetAngle()
    {
        mAngle = getRandomAngle();
    }
}
