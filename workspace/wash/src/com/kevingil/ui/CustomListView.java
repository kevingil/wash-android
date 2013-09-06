package com.kevingil.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.kevingil.wash.R;

public class CustomListView extends ListView {

	private final ImageView underscrollEdge;
	private final ImageView underscrollGlow;
	private final ImageView overscrollGlow;
	private final ImageView overscrollEdge;
	private AbsListView listView;

	private final static float MAX_EDGE_SIZE = 11f;
	private final static float MAX_GLOW_SIZE = 93f;

	protected static final float FLING_BUMP_HEIGHT = 160;

	private float scrollDistanceSinceBoundary = 0;

	private final Rect paddingRectangle = new Rect();

	GestureDetector listViewGestureDetector;

	// Gives the option of short circuiting the overscroll glow fade (Such as by
	// scrolling away from the overscrolled edge)
	boolean interruptFade = false;

	private GlowShrinker animator;

	@SuppressLint("NewApi")
	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);

		listViewGestureDetector = new GestureDetector(
				new ListViewGestureDetector());

		if (getTag() == null
				|| getTag().toString().equalsIgnoreCase("ListView")) {
			listView = new ListView(context) {
				@Override
				protected void onOverScrolled(int scrollX, int scrollY,
						boolean clampedX, boolean clampedY) {
					if (listIsAtTop()) {
						scrollDistanceSinceBoundary += FLING_BUMP_HEIGHT;
						scaleEdges(underscrollEdge, underscrollGlow,
								scrollDistanceSinceBoundary);
						reset();
					} else if (listIsAtBottom()) {
						scrollDistanceSinceBoundary += FLING_BUMP_HEIGHT;
						scaleEdges(overscrollEdge, overscrollGlow,
								scrollDistanceSinceBoundary);
						reset();
					}
				}
			};
		} else if (getTag().toString().equalsIgnoreCase("GridView")) {
			listView = new GridView(context, attrs);
			((GridView) listView).getSelector().getPadding(paddingRectangle);
		}
		listView.setId(android.R.id.list);
		if (Integer.parseInt(Build.VERSION.SDK) >= 9) {
			listView.setOverScrollMode(OVER_SCROLL_NEVER);
		}
		addView(listView, new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		underscrollEdge = new ImageView(context);
		underscrollEdge.setImageResource(R.drawable.overscroll_glow_bottom);
		underscrollEdge.setScaleType(ScaleType.FIT_XY);
		underscrollGlow = new ImageView(context);
		underscrollGlow.setImageResource(R.drawable.overscroll_glow_bottom);
		underscrollGlow.setScaleType(ScaleType.FIT_XY);
		overscrollGlow = new ImageView(context);
		overscrollGlow.setImageResource(R.drawable.overscroll_glow_top);
		overscrollGlow.setScaleType(ScaleType.FIT_XY);
		overscrollEdge = new ImageView(context);
		overscrollEdge.setImageResource(R.drawable.overscroll_glow_top);
		overscrollEdge.setScaleType(ScaleType.FIT_XY);

		addView(underscrollGlow);
		addView(underscrollEdge);

		addView(overscrollGlow);
		addView(overscrollEdge);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			interruptFade = true;
		}
		listViewGestureDetector.onTouchEvent(ev);

		if (ev.getAction() == MotionEvent.ACTION_UP) {
			reset();
		}
		return super.dispatchTouchEvent(ev);
	}

	private RelativeLayout.LayoutParams getWideLayout(int alignment) {
		RelativeLayout.LayoutParams returnLayout = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0);
		returnLayout.addRule(alignment);
		return returnLayout;
	}

	public void reset() {
		interruptFade = false;
		if (animator != null && !animator.isFinished()) {
			animator.cancel(true);
		}
		animator = new GlowShrinker();
		animator.execute(scrollDistanceSinceBoundary);
		scrollDistanceSinceBoundary = 0;
	}

	private boolean listIsAtTop() {
		if (listView.getChildCount() == 0) {
			// No action for empty lists
			return false;
		}
		if (listView.getFirstVisiblePosition() > 0) {
			return false;
		}
		return listView.getChildAt(0).getTop() - paddingRectangle.top == 0;
	}

	private boolean listIsAtBottom() {
		if (listView.getChildCount() == 0) {
			// No action for empty lists
			return false;
		}
		int index = listView.getChildCount() - 1;
		if (listView.getLastVisiblePosition() < index) {
			return false;
		}
		return listView.getChildAt(index).getBottom()
				+ paddingRectangle.bottom == listView.getHeight();
	}

	private class ListViewGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent downMotionEvent,
				MotionEvent currentMotionEvent, float distanceX, float distanceY) {
			float distanceTraveled = downMotionEvent.getY()
					- currentMotionEvent.getY();
			boolean atTop = listIsAtTop();
			boolean atBottom = listIsAtBottom();

			if (atTop && distanceTraveled < 0) {
				// At top and finger moving down
				scrollDistanceSinceBoundary -= distanceY;
				scaleEdges(underscrollEdge, underscrollGlow,
						scrollDistanceSinceBoundary);
			} else if (atTop && distanceTraveled > 0
					&& scrollDistanceSinceBoundary > 0) {
				// At top and finger moving up while in overscroll
				scrollDistanceSinceBoundary -= distanceY;
				scaleEdges(underscrollEdge, underscrollGlow,
						scrollDistanceSinceBoundary);
			} else if (atBottom && distanceTraveled > 0) {
				// At bottom and finger moving up
				scrollDistanceSinceBoundary += distanceY;
				scaleEdges(overscrollEdge, overscrollGlow,
						scrollDistanceSinceBoundary);
			} else if (atBottom && distanceTraveled < 0
					&& scrollDistanceSinceBoundary > 0) {
				// At bottom and finger moving up while in overscroll
				scrollDistanceSinceBoundary += distanceY;
				scaleEdges(overscrollEdge, overscrollGlow,
						scrollDistanceSinceBoundary);
			} else if (scrollDistanceSinceBoundary != 0) {
				// Neither over scrolling or under scrolling but was at last
				// check. Reset both graphics.
				reset();
			}

			return false;
		}

	}

	private class GlowShrinker extends AsyncTask<Float, Integer, Void> {
		ImageView glow;
		ImageView edge;

		private final int SHRINK_SPEED = 4;
		private final int SHRINK_INCREMENT = 50;

		private boolean finished;

		@Override
		protected void onPreExecute() {
			if (underscrollGlow.getHeight() > 0) {
				glow = underscrollGlow;
				edge = underscrollEdge;
			} else if (overscrollGlow.getHeight() > 0) {
				glow = overscrollGlow;
				edge = overscrollEdge;
			} else {
				return;
			}
		}

		@Override
		protected Void doInBackground(Float... scrollDistanceSinceBoundary) {
			if (glow != null && edge != null) {
				int currentSize = (int) scrollDistanceSinceBoundary[0]
						.floatValue();
				int shrinkRate = currentSize / SHRINK_INCREMENT;

				for (int i = 0; i < SHRINK_INCREMENT; i++) {
					if (isCancelled()) {
						break;
					}
					if (interruptFade) {
						publishProgress(0);
						return null;
					}
					currentSize -= shrinkRate;
					publishProgress(currentSize);

					try {
						Thread.sleep(SHRINK_SPEED);
					} catch (InterruptedException e) {
					}
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			CustomListView.scaleEdges(edge, glow, values[0]);
		}

		@Override
		protected void onPostExecute(Void result) {
			if (glow != null && edge != null) {
				CustomListView.scaleEdges(edge, glow, 0);
			}
			finished = true;
		}

		public boolean isFinished() {
			return finished;
		}

	}

	private static void scaleEdges(ImageView scrollEdge, ImageView scrollGlow,
			float scrollBy) {
		float edgeSize = scrollBy / 20;
		float glowSize = scrollBy / 2;
		if (edgeSize > MAX_EDGE_SIZE) {
			edgeSize = MAX_EDGE_SIZE;
		}
		if (glowSize > MAX_GLOW_SIZE) {
			glowSize = MAX_GLOW_SIZE;
		}
		setHeight(scrollEdge, edgeSize);
		setHeight(scrollGlow, glowSize);
	}

	private static void setHeight(ImageView viewIn, float height) {
		ViewGroup.LayoutParams params = viewIn.getLayoutParams();
		params.height = (int) height;
		viewIn.setLayoutParams(params);
	}

	public AbsListView getListView() {
		return listView;
	}
}