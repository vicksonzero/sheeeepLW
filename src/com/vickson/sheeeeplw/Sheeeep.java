package com.vickson.sheeeeplw;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sheeeep {
	static Bitmap sp_running[];
	double x,y;
	double speed;
	double image_index;
	
	// constructor
	public Sheeeep(){ this(0,0); }
	public Sheeeep(double d, double e){
		this(d,e,0);
	}
	public Sheeeep(double x, double y, double speed){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.image_index = (int)(Math.random()*3);
	}
	/**
	 * updates position, updates image_index, and draws the sheeeep into the stage
	 * @param c the canvas to draw into
	 */
	public void draw(Canvas c){
		// get the width of canvas
		int screen_width = c.getWidth();
		int screen_height = c.getHeight();
		
		image_index += 0.1;
		x-=this.speed;
		// if x crosses the width means x has reached to right edge
		if (x < 0-sp_running[0].getWidth()) {
			// assign initial value to start with
			x = screen_width;
			y = Math.random()*(screen_height-sp_running[0].getHeight());
		}
		
		// draw myself
		c.drawBitmap(sp_running[(int) image_index%3], (float)x, (float)y, null);
	}
}
