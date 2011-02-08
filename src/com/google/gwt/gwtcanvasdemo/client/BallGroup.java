/*
 * Copyright 2011 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.gwt.gwtcanvasdemo.client;

import com.google.gwt.canvas.dom.client.Context2d;

import java.util.ArrayList;

public class BallGroup {
  Vector mouse;
  ArrayList<Ball> balls;
  
  public BallGroup(double centerx, double centery) {
    balls = new ArrayList<Ball>(0);
    mouse = new Vector();
    
    // init balls (values from Google's homepage)
    balls.add(new Ball(202, 78, 0, 9, "#ed9d33"));
    balls.add(new Ball(348, 83, 0, 9, "#d44d61"));
    balls.add(new Ball(256, 69, 0, 9, "#4f7af2"));
    balls.add(new Ball(214, 59, 0, 9, "#ef9a1e"));
    balls.add(new Ball(265, 36, 0, 9, "#4976f3"));
    balls.add(new Ball(300, 78, 0, 9, "#269230"));
    balls.add(new Ball(294, 59, 0, 9, "#1f9e2c"));
    balls.add(new Ball(45, 88, 0, 9, "#1c48dd"));
    balls.add(new Ball(268, 52, 0, 9, "#2a56ea"));
    balls.add(new Ball(73, 83, 0, 9, "#3355d8"));
    balls.add(new Ball(294, 6, 0, 9, "#36b641"));
    balls.add(new Ball(235, 62, 0, 9, "#2e5def"));
    balls.add(new Ball(353, 42, 0, 8, "#d53747"));
    balls.add(new Ball(336, 52, 0, 8, "#eb676f"));
    balls.add(new Ball(208, 41, 0, 8, "#f9b125"));
    balls.add(new Ball(321, 70, 0, 8, "#de3646"));
    balls.add(new Ball(8, 60, 0, 8, "#2a59f0"));
    balls.add(new Ball(180, 81, 0, 8, "#eb9c31"));
    balls.add(new Ball(146, 65, 0, 8, "#c41731"));
    balls.add(new Ball(145, 49, 0, 8, "#d82038"));
    balls.add(new Ball(246, 34, 0, 8, "#5f8af8"));
    balls.add(new Ball(169, 69, 0, 8, "#efa11e"));
    balls.add(new Ball(273, 99, 0, 8, "#2e55e2"));
    balls.add(new Ball(248, 120, 0, 8, "#4167e4"));
    balls.add(new Ball(294, 41, 0, 8, "#0b991a"));
    balls.add(new Ball(267, 114, 0, 8, "#4869e3"));
    balls.add(new Ball(78, 67, 0, 8, "#3059e3"));
    balls.add(new Ball(294, 23, 0, 8, "#10a11d"));
    balls.add(new Ball(117, 83, 0, 8, "#cf4055"));
    balls.add(new Ball(137, 80, 0, 8, "#cd4359"));
    balls.add(new Ball(14, 71, 0, 8, "#2855ea"));
    balls.add(new Ball(331, 80, 0, 8, "#ca273c"));
    balls.add(new Ball(25, 82, 0, 8, "#2650e1"));
    balls.add(new Ball(233, 46, 0, 8, "#4a7bf9"));
    balls.add(new Ball(73, 13, 0, 8, "#3d65e7"));
    balls.add(new Ball(327, 35, 0, 6, "#f47875"));
    balls.add(new Ball(319, 46, 0, 6, "#f36764"));
    balls.add(new Ball(256, 81, 0, 6, "#1d4eeb"));
    balls.add(new Ball(244, 88, 0, 6, "#698bf1"));
    balls.add(new Ball(194, 32, 0, 6, "#fac652"));
    balls.add(new Ball(97, 56, 0, 6, "#ee5257"));
    balls.add(new Ball(105, 75, 0, 6, "#cf2a3f"));
    balls.add(new Ball(42, 4, 0, 6, "#5681f5"));
    balls.add(new Ball(10, 27, 0, 6, "#4577f6"));
    balls.add(new Ball(166, 55, 0, 6, "#f7b326"));
    balls.add(new Ball(266, 88, 0, 6, "#2b58e8"));
    balls.add(new Ball(178, 34, 0, 6, "#facb5e"));
    balls.add(new Ball(100, 65, 0, 6, "#e02e3d"));
    balls.add(new Ball(343, 32, 0, 6, "#f16d6f"));
    balls.add(new Ball(59, 5, 0, 6, "#507bf2"));
    balls.add(new Ball(27, 9, 0, 6, "#5683f7"));
    balls.add(new Ball(233, 116, 0, 6, "#3158e2"));
    balls.add(new Ball(123, 32, 0, 6, "#f0696c"));
    balls.add(new Ball(6, 38, 0, 6, "#3769f6"));
    balls.add(new Ball(63, 62, 0, 6, "#6084ef"));
    balls.add(new Ball(6, 49, 0, 6, "#2a5cf4"));
    balls.add(new Ball(108, 36, 0, 6, "#f4716e"));
    balls.add(new Ball(169, 43, 0, 6, "#f8c247"));
    balls.add(new Ball(137, 37, 0, 6, "#e74653"));
    balls.add(new Ball(318, 58, 0, 6, "#ec4147"));
    balls.add(new Ball(226, 100, 0, 5, "#4876f1"));
    balls.add(new Ball(101, 46, 0, 5, "#ef5c5c"));
    balls.add(new Ball(226, 108, 0, 5, "#2552ea"));
    balls.add(new Ball(17, 17, 0, 5, "#4779f7"));
    balls.add(new Ball(232, 93, 0, 5, "#4b78f1"));
    
    // adjust sizes for this demo
    double scale = 0.70f;
    for (int i = 0; i < balls.size(); i++) {
      Ball ball = balls.get(i);
      ball.pos.x = centerx - scale * 180 + scale * ball.pos.x;
      ball.pos.y = centery - scale * 65 + scale * ball.pos.y;
      ball.radius = scale * ball.radius;
      ball.startRadius = scale * ball.startRadius;
      ball.startPos.x = ball.pos.x;
      ball.startPos.y = ball.pos.y;
    }
  }
  
  public void update() {
    for(int i = 0; i < balls.size(); i++) {
      Ball ball = balls.get(i);
      Vector d = Vector.sub(mouse, ball.pos);
      double dist = d.mag();
      if (dist < 100) {
        ball.goal = Vector.sub(ball.pos, d);
      } else {
        ball.goal.set(ball.startPos);
      }
      
      ball.update();
    }
  }
  
  public void draw(Context2d context) {
    for(int i = 0; i < balls.size(); i++) {
      Ball ball = balls.get(i);
      ball.draw(context);
    }
  }
}
