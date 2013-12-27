package com.vickson.sheeeeplw;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sheeeep {
	static Bitmap sp_running[];
	double x,y;
	double image_index;
	public Sheeeep(){
		
		this(0,0);
	}
	public Sheeeep(double d, double e){
		this.x = d;
		this.y = e;
		this.image_index = 0;
	}
	public void draw(Canvas c){
		// get the width of canvas
		int screen_width = c.getWidth();
		int screen_height = c.getHeight();
		
		image_index += 0.3;
		x-=7;
		// if x crosses the width means x has reached to right edge
		if (x < 0-sp_running[(int) image_index%3].getWidth()) {
			// assign initial value to start with
			x = screen_width;
			y = Math.random()*(screen_height-sp_running[(int) image_index%3].getHeight());
		}
		
		// draw myself
		c.drawBitmap(sp_running[(int) image_index%3], (float)x, (float)y, null);
	}
}
