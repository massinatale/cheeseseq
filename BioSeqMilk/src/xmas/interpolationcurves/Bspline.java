package xmas.interpolationcurves;

import java.awt.*;

public class Bspline extends ControlCurve {

  // the basis function for a cubic B spline
  float b(int i, float t) {
    switch (i) {
    case -2:
      return (((-t+3)*t-3)*t+1)/6;
    case -1:
      return (((3*t-6)*t)*t+4)/6;
    case 0:
      return (((-3*t+3)*t+3)*t+1)/6;
    case 1:
      return (t*t*t)/6;
    }
    return 0; //we only get here if an invalid i is specified
  }
  
  //evaluate a point on the B spline
  Point p(int i, float t) {
    float px=0;
    float py=0;
    for (int j = -2; j<=1; j++){
      px += b(j,t)*pts.xpoints[i+j];
      py += b(j,t)*pts.ypoints[i+j];
    }
    return new Point((int)Math.round(px),(int)Math.round(py));
  }

  final int STEPS = 12;

  public void paint(Graphics g) {
    super.paint(g);
    Polygon pol = new Polygon ();
    Point q = p(2,0);
    pol.addPoint(q.x,q.y);
    for (int i = 2; i < pts.npoints-1; i++) {
      for (int j = 1; j <= STEPS; j++) {
	q = p(i,j/(float)STEPS);
	pol.addPoint(q.x,q.y);
      }
    }
    g.drawPolyline(pol.xpoints, pol.ypoints, pol.npoints);
  }

}
