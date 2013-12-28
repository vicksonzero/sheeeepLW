package com.vickson.sheeeeplw;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

public class LiveWallpaperService extends WallpaperService {

	public void onCreate() {
		super.onCreate();
	}

	public void onDestroy() {
		super.onDestroy();
	}

	public Engine onCreateEngine() {
		return new MyWallpaperEngine();
	}

	class MyWallpaperEngine extends Engine {

		private final Handler handler = new Handler();
		private final Runnable drawRunner = new Runnable() {
			@Override
			public void run() {
				draw();
			}
		};
		private boolean visible = true;
		public Bitmap backgroundImage;
		public ArrayList<Sheeeep> sheeeeps;

		MyWallpaperEngine() {
			// get the fish and background image references
			Sheeeep.sp_running = new Bitmap[3];
			Sheeeep.sp_running[0]= BitmapFactory.decodeResource(getResources(),
					R.drawable.sheeeep_02);
			Sheeeep.sp_running[1]= BitmapFactory.decodeResource(getResources(),
					R.drawable.sheeeep_03);
			Sheeeep.sp_running[2]= BitmapFactory.decodeResource(getResources(),
					R.drawable.sheeeep_04);
			backgroundImage = BitmapFactory.decodeResource(getResources(),
					R.drawable.grassland);
			
			// get the device width and height
			WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
			Display display = wm.getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			int width = size.x;
			int height = size.y;

			// transform the background
			backgroundImage = Bitmap.createScaledBitmap(backgroundImage, width,
					height, true);

			// instantiate sheeeeps
			sheeeeps = new ArrayList<Sheeeep>();
			for (int i = 0; i < 20; ++i) {
				sheeeeps.add(
						new Sheeeep(
								Math.random()* (width), 
								Math.random()* (height - Sheeeep.sp_running[0].getHeight()),
								Math.random()*3+3
								)
						);
			}

		}

		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			this.visible = visible;
			// if screen wallpaper is visible then draw the image otherwise do
			// not draw
			if (visible) {
				handler.post(drawRunner);
			} else {
				handler.removeCallbacks(drawRunner);
			}
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			this.visible = false;
			handler.removeCallbacks(drawRunner);
		}

		public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
				float yStep, int xPixels, int yPixels) {
			draw();
		}

		void draw() {
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			try {
				c = holder.lockCanvas();
				// clear the canvas
				c.drawColor(Color.BLACK);
				if (c != null) {
					// draw the background image
					c.drawBitmap(backgroundImage, 0, 0, null);
					// draw the sheeeep
					for (int i = sheeeeps.size() - 1; i >= 0; --i) {
						sheeeeps.get(i).draw(c);
					}
				}
			} finally {
				if (c != null)
					holder.unlockCanvasAndPost(c);
			}

			handler.removeCallbacks(drawRunner);
			if (visible) {
				handler.postDelayed(drawRunner, 10); // delay 10 mileseconds
			}

		}
	}

}
