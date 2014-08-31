package fgeExamples;

import fge.App;
import fge.Color;
import fge.Misc;
import fge.Mouse;
import fge.Mouse.MouseButton;
import fge.Render;
import fge.Service;
import fge.Window;

public class AirHockeyExample extends Service {

	private float puckX;
	private float puckY;
	private float puckRadius;
	private float puckAccel;
	private float puckFriction;
	private float[] puckDirection;
	
	private float limitLeft;
	private float limitRight;
	private float limitTop;
	private float limitBottom;

	public static void main(String[] args) {
		App.run(new AirHockeyExample(), 800, 600);
	}

	@Override
	protected void onStart() {
		puckX = 100;
		puckY = 300;
		puckRadius = 32;
		puckAccel = 800.0f;
		puckFriction = 128.0f;
		puckDirection = new float[2];
		puckDirection[0] = 0.8f;
		puckDirection[1] = 0.4f;
		
		limitLeft = puckRadius;
		limitRight = Window.getW() - puckRadius;
		limitTop = puckRadius;
		limitBottom = Window.getH() - puckRadius;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMove() {
		float nextPuckX = puckX + puckDirection[0] * puckAccel * App.getFTime();
		float nextPuckY = puckY + puckDirection[1] * puckAccel * App.getFTime();
		
		if (nextPuckX < limitLeft) {
			nextPuckX = limitLeft;
			puckDirection[0] = -puckDirection[0];
		}
		if (nextPuckX > limitRight) {
			nextPuckX = limitRight;
			puckDirection[0] = -puckDirection[0];
		}
		if (nextPuckY < limitTop){
			nextPuckY = limitTop;
			puckDirection[1] = -puckDirection[1];
		}
		if (nextPuckY > limitBottom) {
			nextPuckY = limitBottom;
			puckDirection[1] = -puckDirection[1];
		}
		
		puckX = nextPuckX;
		puckY = nextPuckY;
		
		puckAccel -= puckFriction * App.getFTime();
		if (puckAccel < 0) puckAccel = 0;
	}

	@Override
	protected void onDraw() {
		
		Render.DrawFilledCircle(puckX, puckY, puckRadius, new Color(255,0,0));
		
		float x1 = puckX;
		float y1 = puckY;
		float puckLineLength = 1024.0f;
		float[] puckLineDirection = { puckDirection[0], puckDirection[1] };
		
		while (puckLineLength > 1) {
			System.out.println(puckLineLength);
			float x2 = x1 + puckLineDirection[0] * puckLineLength;
			float y2 = y1 + puckLineDirection[1] * puckLineLength;
			
			if (x2 < 0) {
				x2 = 0;
				puckLineDirection[0] = -puckLineDirection[0];
			}
			if (x2 > Window.getW()) {
				x2 = Window.getW();
				puckLineDirection[0] = -puckLineDirection[0];
			}
			if (y2 < 0) {
				y2 = 0;
				puckLineDirection[1] = -puckLineDirection[1];
			}
			if (y2 > Window.getH()) {
				y2 = Window.getH();
				puckLineDirection[1] = -puckLineDirection[1];
			}
			
			puckLineLength -= Misc.getDistance(x1, y1, x2, y2);
			
			Render.DrawLine(x1, y1, x2, y2, new Color(0,255,0));
			
			x1 = x2;
			y1 = y2;
		}
		
		Render.DrawLine(puckX, puckY, puckX + puckDirection[0] * puckRadius, puckY + puckDirection[1] * puckRadius, new Color(0,0,0));
		
	}

}
