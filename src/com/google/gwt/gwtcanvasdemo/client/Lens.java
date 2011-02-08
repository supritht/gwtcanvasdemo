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

import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.core.client.GWT;

import java.util.ArrayList;

public class Lens {
  final int radius = 35;
  final Vector pos = new Vector(320, 150);
  final int mag = 15;
  int[][] lensArray;
  Vector vel = new Vector(1, 1);
  
  public Lens() {
    // calculate lensArray
    ArrayList<int[]> calcLensArray = new ArrayList<int[]>(0);
    int a, b;
    double s = Math.sqrt(radius*radius - mag*mag);
    for (int y = -radius; y < radius; y++) {
      for (int x = -radius; x < radius; x++) {
        if(x*x + y*y < s*s) {
          double z = Math.sqrt(radius*radius - x*x - y*y);
          a = (int)(x * mag / z + 0.5);
          b = (int)(y * mag / z + 0.5);
          int dstIdx = (y + radius) * 2 * radius + (x + radius);
          int srcIdx = (b + radius) * 2 * radius + (a + radius);
          calcLensArray.add(new int[]{dstIdx, srcIdx});
        }
      }
    }
    // store in an int array
    lensArray = new int[calcLensArray.size()][2];
    for(int i = 0; i < calcLensArray.size(); i++) {
      int[] fromTo = calcLensArray.get(i);
      lensArray[i][0] = fromTo[0];
      lensArray[i][1] = fromTo[1];
    }
  }
  
  public void update(int width, int height) {
    if (pos.x + radius + vel.x > width || pos.x - radius + vel.x < 0) {
      vel.x *= -1;
    }
    if (pos.y + radius + vel.y > height || pos.y - radius + vel.y < 0) {
      vel.y *= -1;
    }
    
    pos.x += vel.x;
    pos.y += vel.y;
  }
  
  public void draw(Context2d back, Context2d front) {
    front.drawImage(back.getCanvas(), 0, 0);
    
    if (GWT.isScript()) {
      // do lens effect
      ImageData frontData = front.getImageData(pos.x - radius, pos.y - radius, 2 * radius, 2 * radius);
      CanvasPixelArray frontPixels = frontData.getData();
      ImageData backData = back.getImageData(pos.x - radius, pos.y - radius, 2 * radius, 2 * radius);
      CanvasPixelArray backPixels = backData.getData();
      int srcIdx, dstIdx;
      for(int i = lensArray.length - 1; i >= 0 ; i--) {
        dstIdx = 4 * lensArray[i][0];
        srcIdx = 4 * lensArray[i][1];
        frontPixels.set(dstIdx + 0, backPixels.get(srcIdx + 0));
        frontPixels.set(dstIdx + 1, backPixels.get(srcIdx + 1));
        frontPixels.set(dstIdx + 2, backPixels.get(srcIdx + 2));
      }
      front.putImageData(frontData, pos.x - radius, pos.y - radius);
    } else {
      // in devmode, don't do lens effect
    }
    
    front.setStrokeStyle("#333300");
    front.beginPath();
    front.arc(pos.x, pos.y, radius, 0, Math.PI * 2.0, true);
    front.stroke();
  }
}
