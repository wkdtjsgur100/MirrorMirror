package etcpackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;

public class TracknBall {
	public enum Direction {
		DOWN, UP, RIGHT, LEFT, CENTER, NEAR_CENTER
	};

	private int returning_velocity = 30;
	private static int FRAME_NUMBER_PER_SECOND = 1000 / 90;

	int velocity;
	int collisionLength;
	int nodeImageLength;
	private long startingTime;
	Direction currentDirectionInReturn;
	private long initialForceSkipTime;
	boolean hasDirection;

	Context context;
	boolean isPicked;
	Vertex startingVertex;
	Vertex leavingVertex;
	public BatteryPosition leftTopPosition;
	Resources res;
	BitmapDrawable bd;
	Bitmap bitBall;
	Bitmap bitTrack;

	int tnbWidth;
	int tnbHeight;

	int ballWidth;
	int ballHeight;
	int trackWidth;
	int trackHeight;

	int screenWidth;
	int screenHeight;

	Vertex tnbCenter;

	Bitmap upImage;
	Bitmap downImage;
	Bitmap rightImage;
	Bitmap leftImage;

	Paint Pnt;

	public TracknBall(Bitmap bitBall, Bitmap bitTrack, int tnbCenterX,
			int tnbCenterY, int screenWidth, int screenHeight, Context context) {

		returning_velocity = 30 * screenWidth / 1080;
		isPicked = false;
		Pnt = new Paint();
		this.context = context;
		velocity = 0;
		startingTime = System.currentTimeMillis();
		currentDirectionInReturn = Direction.CENTER;
		hasDirection = false;
		
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		
		tnbCenter = new Vertex(tnbCenterX, tnbCenterY);

		leavingVertex = new Vertex(tnbCenter.getX(), tnbCenter.getY());
		startingVertex = new Vertex(tnbCenter.getX(), tnbCenter.getY());
		leftTopPosition = new BatteryPosition(tnbCenter.getX(),
				tnbCenter.getY());

		

		res = context.getResources();

		this.bitBall = bitBall;
		this.bitTrack = bitTrack;

		ballWidth = bitBall.getWidth();
		ballHeight = bitBall.getHeight();
		trackWidth = bitTrack.getWidth();
		trackHeight = bitTrack.getHeight();

		collisionLength = trackWidth / 2 - ballWidth / 2;

		nodeImageLength = (trackWidth / 2 - ballWidth / 2);
	}

	public Vertex getCollisionVertex(int nodeIndex) {
		Vertex tempVertex;
		if (nodeIndex == 1) { // RIGHT
			tempVertex = new Vertex(tnbCenter.getX() + collisionLength,
					tnbCenter.getY());

		} else if (nodeIndex == 2) { // DOWN
			tempVertex = new Vertex(tnbCenter.getX(), tnbCenter.getY()
					+ collisionLength);
		} else if (nodeIndex == 3) { // LEFT
			tempVertex = new Vertex(tnbCenter.getX() - collisionLength,
					tnbCenter.getY());
		} else if (nodeIndex == 0) { // UP
			tempVertex = new Vertex(tnbCenter.getX(), tnbCenter.getY()
					- collisionLength);
		} else {
			tempVertex = null;
		}

		return tempVertex;

	}

	public void update() throws InterruptedException {
		if (System.currentTimeMillis() - startingTime >= FRAME_NUMBER_PER_SECOND) {
			applyReturningVelocity();
			startingTime = System.currentTimeMillis();
		}

	}

	private void applyReturningVelocity() throws InterruptedException {

		if (isPicked == false) { // ¸ðµç ÀÏÀº ¾È ÀâÇôÀÖÀ» ¶§ ¹ú¾îÁ®¾ß.

			if (this.leftTopPosition.getCurrentBatteryWay() != Direction.CENTER) { // °¡¿îµ¥°¡
																					// ¾Æ´Ò¶§
																					// ½ÃÀÛµÇ´Â°Í.
																					// Áß°£¿¡
																					// °¡¿îµ¥
																					// ¾Æ´Ò
																					// ¼öµµ
																					// ÀÖÀ½.
				if (Math.abs(velocity) != 0) {
					Direction currentBatteryWay = leftTopPosition
							.getCurrentBatteryWay();
					if (hasDirection == false) {
						currentDirectionInReturn = currentBatteryWay;
						hasDirection = true;

					}

					if (currentBatteryWay == leftTopPosition
							.getCurrentBatteryWay()) {

						if (currentBatteryWay == Direction.LEFT) {
							leftTopPosition.batteryPoint.moveLocation(velocity,
									0);

						} else if (currentBatteryWay == Direction.DOWN) {
							leftTopPosition.batteryPoint.moveLocation(0,
									-velocity);

						} else if (currentBatteryWay == Direction.UP) {
							leftTopPosition.batteryPoint.moveLocation(0,
									velocity);

						} else if (currentBatteryWay == Direction.RIGHT) {
							leftTopPosition.batteryPoint.moveLocation(
									-velocity, 0);

						} else {

						}
					}

				}
			}
			if (currentDirectionInReturn != leftTopPosition
					.getCurrentBatteryWay()) { // ¾ðÁ¦µç°£¿¡ °¡¿îµ¥¸é °¡¿îµ¥ °íÁ¤ÇØ¾ß.

				leftTopPosition.batteryPoint.setVertex(tnbCenter.getX(),
						tnbCenter.getY());
				hasDirection = false;
				velocity = 0;
			}
		}

	}

	public void Draw(Canvas canvas) {

		canvas.drawBitmap(bitTrack, tnbCenter.getX() - trackWidth / 2-2,
				tnbCenter.getY() - trackHeight / 2 -3, Pnt);

		if (leftImage != null)
			canvas.drawBitmap(leftImage, tnbCenter.getX() - nodeImageLength
					- (int) (leftImage.getWidth() / 1.5), tnbCenter.getY()
					- leftImage.getHeight() / 2, Pnt);
		if (upImage != null)
			canvas.drawBitmap(upImage, tnbCenter.getX() - upImage.getWidth()
					/ 2,
					tnbCenter.getY() - nodeImageLength - upImage.getHeight()
							/ 2, Pnt);
		if (downImage != null)
			canvas.drawBitmap(downImage,
					tnbCenter.getX() - downImage.getWidth() / 2,
					tnbCenter.getY() + nodeImageLength - downImage.getHeight()
							/ 2, Pnt);
		if (rightImage != null)
			canvas.drawBitmap(rightImage, tnbCenter.getX() + nodeImageLength
					- (int) (rightImage.getWidth() / 2.5), tnbCenter.getY()
					- rightImage.getHeight() / 2, Pnt);

		canvas.drawBitmap(bitBall, leftTopPosition.batteryPoint.getX()
				- ballWidth / 2, leftTopPosition.batteryPoint.getY()
				- ballHeight / 2, Pnt);

	}

	public boolean onTouch(MotionEvent event, int screenWidth, int screenHeight)
			throws InterruptedException {

		Direction OverWhichLineResult = leftTopPosition.OverWhichLine();
		if (OverWhichLineResult != Direction.CENTER
				&& OverWhichLineResult != Direction.NEAR_CENTER) {
			leftTopPosition.locateCollisionPoint();

		}

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			int x = leftTopPosition.batteryPoint.getX();
			int y = leftTopPosition.batteryPoint.getY();

			if (x - ballWidth / 2 <= (int) event.getX()
					&& x <= (int) event.getX() + ballWidth / 2) {
				if (y - ballHeight / 2 <= (int) event.getY()
						&& (int) event.getY() <= y + ballHeight / 2) {

					isPicked = true;

					startingVertex.setVertex((int) event.getX(),
							(int) event.getY());

				}

			}
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			if (isPicked == false) {
				int x = leftTopPosition.batteryPoint.getX();
				int y = leftTopPosition.batteryPoint.getY();

				if (x - ballWidth / 2 <= (int) event.getX()
						&& x <= (int) event.getX() + ballWidth / 2) {
					if (y - ballHeight / 2 <= (int) event.getY()
							&& (int) event.getY() <= y + ballHeight / 2) {

						isPicked = true;

						startingVertex.setVertex((int) event.getX(),
								(int) event.getY());

					}

				}
				return true;
			}

			if (isPicked == true) {
				leavingVertex.setVertex((int) event.getX(), (int) event.getY());

				if ((tnbCenter.getX() - ballWidth / 2 < leavingVertex.getX() && leavingVertex
						.getX() < tnbCenter.getX() + ballWidth / 2)
						|| (tnbCenter.getY() - ballHeight / 2 < leavingVertex
								.getY() && leavingVertex.getY() < tnbCenter
								.getY() + ballHeight / 2)) {

					int ax = leavingVertex.getX() - startingVertex.getX();
					int ay = leavingVertex.getY() - startingVertex.getY();
					int tempTouchMoveLength = (int) Math
							.sqrt((double) (ax * ax + ay * ay));

					// When the battery is at the center point.
					if (leftTopPosition.getCurrentBatteryWay() == Direction.CENTER) {

						leftTopPosition.determineWayToGo(tempTouchMoveLength,
								ax, ay, screenWidth, screenHeight);

						leftTopPosition.batteryMoves(ax, ay);

					} else {
						leftTopPosition.batteryMoves(ax, ay);

					}

					startingVertex.setVertex((int) event.getX(),
							(int) event.getY());

				} else {
					isPicked = false;
					leftTopPosition.returnToCenter();

				}

			}

		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (isPicked == true) {

				isPicked = false;

				leftTopPosition.returnToCenter();

			}

		}
		return false;

	}

	public void setImageOnTheNode(Bitmap bit, Direction direction) {
		if (direction == Direction.UP) {
			upImage = bit;
		} else if (direction == Direction.LEFT) {
			leftImage = bit;
		} else if (direction == Direction.DOWN) {
			downImage = bit;
		} else if (direction == Direction.RIGHT) {
			rightImage = bit;
		} else {
			// what the hell..
		}

	}

	public class BatteryPosition {

		final static int MIN_STARTING_FORCE = 5;
		final static double ATTENUATION_RATIO = 1.4;
		final static double BATTERY_MASS = 1;
		final static int MINIMUM_THRESHOLD = 2;
		
		final static double INVALID_ANGLE_START = 30.0;
		final static double INVALID_ANGLE_END = 60.0;
		final static int MINIMUM_ACCEL_FORCE_THRESHOLD = 20;
		double screenSizeCompensation = screenWidth/200.0;
		Vertex batteryPoint;
		private Direction ways;
		int lastCollisionWay;

		BatteryPosition(int initXPoint, int initYPoint) {
			lastCollisionWay = -1;
			batteryPoint = new Vertex(initXPoint, initYPoint);
			ways = Direction.CENTER; // not determined
			
		}

		private void updateBatteryWay() {
			// check NOW POSITION
			// update ways
			ways = getCurrentBatteryWay();
		}

		private void AccelerationMove(int xForce, int yForce)
				throws InterruptedException {
			// When force is above Threshold value, the battery should be
			// accelerated
			// this function implements how much and which direction the
			// battery should be accelerated.

			while (Math.abs(xForce) > MINIMUM_THRESHOLD
					&& Math.abs(yForce) > MINIMUM_THRESHOLD) {

				
				if (ways == Direction.CENTER) { 
					break;
				}
				
				
				if (ways == Direction.DOWN) { // DOWN
					batteryPoint.moveLocation(0, yForce*screenSizeCompensation  / BATTERY_MASS);
					yForce /= ATTENUATION_RATIO;
				} else if (ways == Direction.UP) { // UP
					batteryPoint.moveLocation(0, yForce*screenSizeCompensation  / BATTERY_MASS);
					yForce /= ATTENUATION_RATIO;
				} else if (ways == Direction.RIGHT) { // RIGHT
					batteryPoint.moveLocation(+xForce*screenSizeCompensation/ BATTERY_MASS, 0);
					xForce /= ATTENUATION_RATIO;
				} else if (ways == Direction.LEFT) { // LEFT
					batteryPoint.moveLocation(+xForce*screenSizeCompensation/ BATTERY_MASS, 0);
					xForce /= ATTENUATION_RATIO;
				}
				if (OverWhichLine() != Direction.CENTER
						&& OverWhichLine() != Direction.NEAR_CENTER) {
					locateCollisionPoint();

				}

			}
			updateBatteryWay();
		}

		private void DraggingMove(int xForce, int yForce)
				throws InterruptedException {
			// When force is below threshold force, it looks like fingertip
			// is on the battery.
			// the battery will look moving at the same speed of your
			// fingertip's moving.

			if (ways == Direction.DOWN) { // DOWN
				batteryPoint.moveLocation(0, +yForce*screenSizeCompensation);
			} else if (ways == Direction.UP) { // UP
				batteryPoint.moveLocation(0, +yForce*screenSizeCompensation);

			} else if (ways == Direction.RIGHT) { // RIGHT
				batteryPoint.moveLocation(+xForce*screenSizeCompensation, 0);

			} else if (ways == Direction.LEFT) { // LEFT
				batteryPoint.moveLocation(+xForce*screenSizeCompensation, 0);

			} else {
				// CENTER
			}
			if (OverWhichLine() != Direction.CENTER
					&& OverWhichLine() != Direction.NEAR_CENTER) {
				locateCollisionPoint();

			}

			updateBatteryWay();
		}

		private Direction determineWayToGo(int minForce, int xForce,
				int yForce, int screenWidth, int screenHeight) {

			// Ignore the unintended force

			if (minForce < MIN_STARTING_FORCE) {
				ways = Direction.CENTER;
				return ways;
			}

			// Filtering the invalid angleRegion

			double angle = Math.toDegrees(Math.atan(Math.abs((double) yForce)
					/ Math.abs((double) xForce)));
			if (INVALID_ANGLE_START < angle && angle < INVALID_ANGLE_END) {
				ways = Direction.CENTER;
				return ways;
			}

			if (xForce > 0 && yForce > 0) { // 4st
				if (Math.abs(xForce * screenWidth) > Math.abs(yForce
						* screenHeight)) {
					ways = Direction.RIGHT;
					return ways; // RIGHT
				} else {
					ways = Direction.DOWN;
					return ways; // DOWN
				}
			} else if (xForce > 0 && yForce < 0) { // 1th
				if (Math.abs(xForce * screenWidth) > Math.abs(yForce
						* screenHeight)) {
					ways = Direction.RIGHT;
					return ways; // RIGHT
				} else {
					ways = Direction.UP;
					return ways; // UP
				}
			} else if (xForce < 0 && yForce > 0) { // 3th
				if (Math.abs(xForce * screenWidth) > Math.abs(yForce
						* screenHeight)) {
					ways = Direction.LEFT;
					return ways; // LEFT
				} else {
					ways = Direction.DOWN;
					return ways; // DOWN
				}
			} else if (xForce < 0 && yForce < 0) { // 2th
				if (Math.abs(xForce * screenWidth) > Math.abs(yForce
						* screenHeight)) {
					ways = Direction.LEFT;
					return ways; // LEFT
				} else {
					ways = Direction.UP;
					return ways; // UP
				}
			}
			ways = Direction.CENTER;
			return ways; // CENTER

		}

		private Direction getCurrentBatteryWay() {

			if (batteryPoint.getX() == tnbCenter.getX()
					&& batteryPoint.getY() < tnbCenter.getY()) { // UP
				return Direction.UP;
			} else if (batteryPoint.getX() == tnbCenter.getX()
					&& batteryPoint.getY() > tnbCenter.getY()) { // DOWN
				return Direction.DOWN;
			} else if (batteryPoint.getX() > tnbCenter.getX()
					&& batteryPoint.getY() == tnbCenter.getY()) { // RIGHT
				return Direction.RIGHT;
			} else if (batteryPoint.getX() < tnbCenter.getX()
					&& batteryPoint.getY() == tnbCenter.getY()) { // LEFT
				return Direction.LEFT;
			} else
				return Direction.CENTER; // CENTER

		}

		private void batteryMoves(int xForce, int yForce)
				throws InterruptedException {
			if (OverWhichLine() != Direction.CENTER
					&& OverWhichLine() != Direction.NEAR_CENTER) {
				locateCollisionPoint();
				return;
			}
			if (Math.abs(xForce) > MINIMUM_ACCEL_FORCE_THRESHOLD
					|| Math.abs(yForce) > MINIMUM_ACCEL_FORCE_THRESHOLD) {
				
				AccelerationMove(xForce, yForce);
			} else
				// basic reaction
				
				DraggingMove(xForce, yForce);
		}

		private void returnToCenter() {

			velocity = returning_velocity;
			updateBatteryWay();

		}

		private void locateCollisionPoint() throws InterruptedException {
			Direction currentWay = getCurrentBatteryWay();

			if (currentWay == Direction.UP) {
				lastCollisionWay = 2;
				batteryPoint.setVertex(tnbCenter.getX(), tnbCenter.getY()
						- collisionLength);
			} else if (currentWay == Direction.RIGHT) {
				lastCollisionWay = 1;
				batteryPoint.setVertex(tnbCenter.getX() + collisionLength,
						tnbCenter.getY());
			} else if (currentWay == Direction.DOWN) {
				lastCollisionWay = 4;
				batteryPoint.setVertex(tnbCenter.getX(), tnbCenter.getY()
						+ collisionLength);
			} else if (currentWay == Direction.LEFT) {
				lastCollisionWay = 3;
				batteryPoint.setVertex(tnbCenter.getX() - collisionLength,
						tnbCenter.getY());
			} else {
				return;
			}

		}

		public int getLastCollisionWay() {

			return lastCollisionWay;
		}

		public Direction OverWhichLine() {
			int dx = batteryPoint.getX() - tnbCenter.getX();
			int dy = batteryPoint.getY() - tnbCenter.getY();

			int length = (int) Math.sqrt((double) dx * dx + dy * dy);
			if (length >= collisionLength) {
				Direction d = getCurrentBatteryWay();
				if (d == Direction.RIGHT) {
					return Direction.RIGHT;
				} else if (d == Direction.UP) {
					return Direction.UP;
				} else if (d == Direction.LEFT) {
					return Direction.LEFT;
				} else
					return Direction.DOWN;

			} else if (dx == 0 && dy == 0)
				return Direction.CENTER;
			else
				return Direction.NEAR_CENTER;

		}
	}

	public Vertex getTNBCenterPosition() {
		// TODO Auto-generated method stub
		return this.tnbCenter;
	}
}