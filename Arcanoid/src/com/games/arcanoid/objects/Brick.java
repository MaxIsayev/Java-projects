package com.games.arcanoid.objects;

import com.games.arcanoid.GameObject;

import android.graphics.drawable.Drawable;

public class Brick extends GameObject {
	
	/*���� ����������� ����������� � ��������*/
	private boolean intersected=false; 
	
	/*N of hit bricks*/
	public static int nOfHit = 0;
	
	public Brick(Drawable image) {
		super(image);
	}

	@Override
	protected void updatePoint() {}
	
	/*	����������� ���� ��� �� ����*/
	public void setInters(boolean val){
		intersected = val;
	}
	
	public boolean getInters(){
		return intersected;
	}
}
