/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.mlkit.vision.demo;

import android.content.Context;
import static java.lang.Math.max;
import static java.lang.Math.min;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.Log;

import com.example.pantryplanner.Grocery;
import com.example.pantryplanner.GroceryHelper;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.Text.Element;
import com.google.mlkit.vision.text.Text.Line;
import com.google.mlkit.vision.text.Text.Symbol;
import com.google.mlkit.vision.text.Text.TextBlock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
public class TextGraphic extends GraphicOverlay.Graphic {

  private static final String TAG = "TextGraphic";
  private static final String TEXT_WITH_LANGUAGE_TAG_FORMAT = "%s:%s";

  private static final int TEXT_COLOR = Color.BLACK;
  private static final int MARKER_COLOR = Color.WHITE;
  private static final float TEXT_SIZE = 54.0f;
  private static final float STROKE_WIDTH = 4.0f;
  private Context context;

  private final Paint rectPaint;
  private final Paint textPaint;
  private final Paint labelPaint;
  private final Text text;
  private final boolean shouldGroupTextInBlocks;
  private final boolean showLanguageTag;
  private final boolean showConfidence;

  TextGraphic(
      Context context,
      GraphicOverlay overlay,
      Text text,
      boolean shouldGroupTextInBlocks,
      boolean showLanguageTag,
      boolean showConfidence) {
    super(overlay);

    this.context = context;
    this.text = text;
    this.shouldGroupTextInBlocks = shouldGroupTextInBlocks;
    this.showLanguageTag = showLanguageTag;
    this.showConfidence = showConfidence;

    rectPaint = new Paint();
    rectPaint.setColor(MARKER_COLOR);
    rectPaint.setStyle(Paint.Style.STROKE);
    rectPaint.setStrokeWidth(STROKE_WIDTH);

    textPaint = new Paint();
    textPaint.setColor(TEXT_COLOR);
    textPaint.setTextSize(TEXT_SIZE);

    labelPaint = new Paint();
    labelPaint.setColor(MARKER_COLOR);
    labelPaint.setStyle(Paint.Style.FILL);
    // Redraw the overlay, as this graphic has been added.
    postInvalidate();
  }

  /** Draws the text block annotations for position, size, and raw value on the supplied canvas. */
  @Override
  public void draw(Canvas canvas) {
    Log.d(TAG, "Text is: " + text.getText());
    for (TextBlock textBlock : text.getTextBlocks()) {
      // Renders the text at the bottom of the box.
      Log.d(TAG, "TextBlock text is: " + textBlock.getText());
      Log.d(TAG, "TextBlock boundingbox is: " + textBlock.getBoundingBox());
      Log.d(TAG, "TextBlock cornerpoint is: " + Arrays.toString(textBlock.getCornerPoints()));
      if (shouldGroupTextInBlocks) {
        String text =
            showLanguageTag
                ? String.format(
                    TEXT_WITH_LANGUAGE_TAG_FORMAT,
                    textBlock.getRecognizedLanguage(),
                    textBlock.getText())
                : textBlock.getText();
        drawText(
            text,
            new RectF(textBlock.getBoundingBox()),
            TEXT_SIZE * textBlock.getLines().size() + 2 * STROKE_WIDTH,
            canvas);
      } else {
        for (Line line : textBlock.getLines()) {
          Log.d(TAG, "Line text is: " + line.getText());
          Log.d(TAG, "Line boundingbox is: " + line.getBoundingBox());
          Log.d(TAG, "Line cornerpoint is: " + Arrays.toString(line.getCornerPoints()));
          Log.d(TAG, "Line confidence is: " + line.getConfidence());
          Log.d(TAG, "Line angle is: " + line.getAngle());
          String text =
              showLanguageTag
                  ? String.format(
                      TEXT_WITH_LANGUAGE_TAG_FORMAT, line.getRecognizedLanguage(), line.getText())
                  : line.getText();
          text =
              showConfidence
                  ? String.format(Locale.US, "%s (%.2f)", text, line.getConfidence())
                  : text;
          drawText(text, new RectF(line.getBoundingBox()), TEXT_SIZE + 2 * STROKE_WIDTH, canvas);

          for (Element element : line.getElements()) {
            Log.d(TAG, "Element text is: " + element.getText());
            Log.d(TAG, "Element boundingbox is: " + element.getBoundingBox());
            Log.d(TAG, "Element cornerpoint is: " + Arrays.toString(element.getCornerPoints()));
            Log.d(TAG, "Element language is: " + element.getRecognizedLanguage());
            Log.d(TAG, "Element confidence is: " + element.getConfidence());
            Log.d(TAG, "Element angle is: " + element.getAngle());
            for (Symbol symbol : element.getSymbols()) {
              Log.d(TAG, "Symbol text is: " + symbol.getText());
              Log.d(TAG, "Symbol boundingbox is: " + symbol.getBoundingBox());
              Log.d(TAG, "Symbol cornerpoint is: " + Arrays.toString(symbol.getCornerPoints()));
              Log.d(TAG, "Symbol confidence is: " + symbol.getConfidence());
              Log.d(TAG, "Symbol angle is: " + symbol.getAngle());
            }
          }
        }
      }
    }

    Log.d(TAG, "Sofia: " + checkPhotoForProducts());
  }

  private ArrayList<String> loadProductsFromCSV(Context context) {
    ArrayList<String> products = new ArrayList<>();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new InputStreamReader(context.getAssets().open("groceries.csv")));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] product = line.split(";");
        if (product.length > 0) {
          products.add(product[0].replace("\"", "")); // Add first item and remove quotes
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return products;
  }

  private String checkPhotoForProducts(){
    ArrayList<String> products = new ArrayList<String>();// Listes Product to check receipt
    products = loadProductsFromCSV(context);

    List<String> matchedProducts = new ArrayList<>();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      matchedProducts = products.stream().filter(text.getText()::contains).collect(Collectors.toList());
    }

    if (matchedProducts.size() > 0) {
      GroceryHelper groceryHelper = new GroceryHelper(context);
      HashMap<String, List<Grocery>> groceries = groceryHelper.loadGroceriesFromDatabase();
      for (String product : matchedProducts) {
        Grocery grocery = groceryHelper.createGrocery(product);
        if (groceries.containsKey(grocery.getGroceryType())) {
          groceries.get(grocery.getGroceryType()).add(grocery);
        } else {
          groceries.put(grocery.getGroceryType(), new ArrayList<>(Arrays.asList(grocery)));
        }
      }
      groceryHelper.writeGroceries(groceries);
    }

    return null;
  }

  private void drawText(String text, RectF rect, float textHeight, Canvas canvas) {
    // If the image is flipped, the left will be translated to right, and the right to left.
    float x0 = translateX(rect.left);
    float x1 = translateX(rect.right);
    rect.left = min(x0, x1);
    rect.right = max(x0, x1);
    rect.top = translateY(rect.top);
    rect.bottom = translateY(rect.bottom);
    canvas.drawRect(rect, rectPaint);
    float textWidth = textPaint.measureText(text);
    canvas.drawRect(
        rect.left - STROKE_WIDTH,
        rect.top - textHeight,
        rect.left + textWidth + 2 * STROKE_WIDTH,
        rect.top,
        labelPaint);
    // Renders the text at the bottom of the box.
    canvas.drawText(text, rect.left, rect.top - STROKE_WIDTH, textPaint);
  }
}
