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
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.ArrayList;

public class LogoGroup {
  Vector mouse;
  Vector canvasCenter;
  Image image = new Image("gwtlogo40_40.png");
  ArrayList<Logo> logos;
  boolean imageLoaded;
  int numLogos = 18;
  double logoRadius = 165;
  double imageWidth = 40;
  double imageHeight = 40;
  double k;
  
  public LogoGroup(final double centerx, final double centery) {
    mouse = new Vector();
    canvasCenter = new Vector();
    
    // init logos array
    logos = new ArrayList<Logo>(numLogos);
    
    // init image
    imageLoaded = false;
    image.addLoadHandler(new LoadHandler() {
      public void onLoad(LoadEvent event) {
        imageLoaded = true;
        // once image is loaded, init logo objects
        ImageElement imageElement = (ImageElement) image.getElement().cast();
        for (int i=0; i<numLogos; i++) {
          Logo logo = new Logo(imageElement, imageWidth, imageHeight);
          logo.pos.x = centerx;
          logo.pos.y = centery;
          logos.add(logo);
        }
      }
    });
    image.setVisible(false);
    RootPanel.get().add(image); // image must be on page to fire load
  }
  
  public void update(double centerx, double centery) {
    if (!imageLoaded) {
      return;
    }
    
    k = (k + Math.PI/2.0* 0.009);
    
    for (int i=0; i<numLogos; i++) {
      Logo logo = logos.get(i);
      double logoPerTPi = 2 * Math.PI * i / numLogos;
      Vector goal = new Vector(centerx + logoRadius * Math.cos(k + logoPerTPi), 
          centery + logoRadius * Math.sin(k + logoPerTPi));
      logo.goal.set(goal);
      logo.rot = k + logoPerTPi + Math.PI / 2.0;
      
      Vector d = Vector.sub(mouse, logo.pos);
      double dist = d.mag();
      if (dist < 50) {
        logo.goal = Vector.sub(logo.pos, d);
      }
      
      logo.update();
    }
  }
  
  public void draw(Context2d context) {
    if (!imageLoaded) {
      return;
    }
    
    for (int i=0; i<numLogos; i++) {
      Logo logo = logos.get(i);
      logo.draw(context);
    }
  }
}
