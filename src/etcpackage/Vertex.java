package etcpackage;


public class Vertex {
	private int x;
	private int y;

	public Vertex(int ax, int ay) {
		x = ax;
		y = ay;
	}


	public void setVertex(int ax, int ay) {
		x = ax;
		y = ay;
	}
	public void setVertex(Vertex vertex) {
		x = vertex.x;
		y = vertex.y;
	}

	public void moveLocation(int dx, int dy) {
		//
		x += dx;
		y += dy;
	}

	public void moveLocation(double dx, double dy) {
		//
		x += (int) dx;
		y += (int) dy;
	}

	public Vertex getLocation() {
		return new Vertex(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}