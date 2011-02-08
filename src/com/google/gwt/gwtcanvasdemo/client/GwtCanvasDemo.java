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

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.GestureStartEvent;
import com.google.gwt.event.dom.client.GestureStartHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class GwtCanvasDemo implements EntryPoint {
  static final String canvasHolderDivId = "canvasholder";

  static final String upgradeMessage = "Your browser does not support the HTML5 Canvas. Please upgrade your browser to view this demo.";

  static final int refreshRate = 25; // timer refresh rate, in milliseconds
  
  final Canvas frontCanvas = Canvas.createIfSupported();
  final Canvas backCanvas = Canvas.createIfSupported();
  LogoGroup logoGroup;
  BallGroup ballGroup;
  Lens lens;
  int height = 400;
  int width = 400;

  public void onModuleLoad() {
    if (frontCanvas == null) {
      RootPanel.get(canvasHolderDivId).add(new Label(upgradeMessage));
      return;
    }

    frontCanvas.setCoordinateSpaceWidth(width);
    frontCanvas.setCoordinateSpaceHeight(height);
    frontCanvas.setWidth(width + "px");
    frontCanvas.setHeight(height + "px");

    backCanvas.setCoordinateSpaceWidth(width);
    backCanvas.setCoordinateSpaceHeight(height);

    RootPanel.get(canvasHolderDivId).add(frontCanvas);

    logoGroup = new LogoGroup(width / 2.0, height / 2.0);
    ballGroup = new BallGroup(width / 2.0, height / 2.0);

    lens = new Lens();

    frontCanvas.addMouseMoveHandler(new MouseMoveHandler() {
      public void onMouseMove(MouseMoveEvent event) {
        int mousex = event.getRelativeX(frontCanvas.getElement());
        int mousey = event.getRelativeY(frontCanvas.getElement());
        logoGroup.mouse.x = mousex;
        logoGroup.mouse.y = mousey;
        ballGroup.mouse.x = mousex;
        ballGroup.mouse.y = mousey;
      }
    });

    frontCanvas.addMouseOutHandler(new MouseOutHandler() {
      public void onMouseOut(MouseOutEvent event) {
        logoGroup.mouse.x = -200;
        logoGroup.mouse.y = -200;
        ballGroup.mouse.x = -200;
        ballGroup.mouse.y = -200;
      }
    });

    frontCanvas.addTouchMoveHandler(new TouchMoveHandler() {
      public void onTouchMove(TouchMoveEvent event) {
        if (event.getTouches().length() > 0) {
          Touch touch = event.getTouches().get(0);
          int touchx = touch.getRelativeX(frontCanvas.getElement());
          int touchy = touch.getRelativeY(frontCanvas.getElement());
          logoGroup.mouse.x = touchx;
          logoGroup.mouse.y = touchy;
          ballGroup.mouse.x = touchx;
          ballGroup.mouse.y = touchy;
        }
        event.preventDefault();
      }
    });

    frontCanvas.addTouchEndHandler(new TouchEndHandler() {
      public void onTouchEnd(TouchEndEvent event) {
        logoGroup.mouse.x = -200;
        logoGroup.mouse.y = -200;
        ballGroup.mouse.x = -200;
        ballGroup.mouse.y = -200;
        event.preventDefault();
      }
    });

    frontCanvas.addGestureStartHandler(new GestureStartHandler() {
      public void onGestureStart(GestureStartEvent event) {
        event.preventDefault();
      }
    });

    final Timer timer = new Timer() {
      @Override
      public void run() {
        doRender();
      }
    };
    timer.scheduleRepeating(refreshRate);
  }

  private void doRender() {
    // update the back canvas
    Context2d backContext = backCanvas.getContext2d();
    backContext.setFillStyle("rgba(255,255,255,0.6)");
    backContext.fillRect(0, 0, width, height);
    logoGroup.update(width / 2.0, height / 2.0);
    logoGroup.draw(backContext);
    ballGroup.update();
    ballGroup.draw(backContext);

    // update the front canvas
    lens.update(width, height);
    Context2d frontContext = frontCanvas.getContext2d();
    lens.draw(backContext, frontContext);
  }
}
