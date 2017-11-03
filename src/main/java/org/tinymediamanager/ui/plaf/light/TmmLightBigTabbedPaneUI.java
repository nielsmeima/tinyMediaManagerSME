/*
 * Copyright 2012 - 2017 Manuel Laggner
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
package org.tinymediamanager.ui.plaf.light;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseTabbedPaneUI;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * The Class TmmLightTabbedPaneUI.
 * 
 * @author Manuel Laggner
 */
public class TmmLightBigTabbedPaneUI extends BaseTabbedPaneUI {
  protected static int BORDER_RADIUS = 15;

  private boolean      roundEdge     = true;

  public static ComponentUI createUI(JComponent c) {
    Object prop = c.getClientProperty("class");
    if (prop != null && prop instanceof String && "big".equals(prop.toString())) {
      return new TmmLightBigTabbedPaneUI(c);
    }
    return new TmmLightTabbedPaneUI();
  }

  public TmmLightBigTabbedPaneUI(JComponent c) {
    super();
  }

  @Override
  public void installDefaults() {
    super.installDefaults();

    // defaults
    tabAreaBackground = new Color(41, 41, 41);
    tabInsets = new Insets(5, 20, 5, 20);
    tabAreaInsets = new Insets(0, 20, 15, 20);
    contentBorderInsets = new Insets(0, 20, 20 + BORDER_RADIUS, 20);
    roundedTabs = false;

    // overrides
    if (Boolean.FALSE.equals(this.tabPane.getClientProperty("rightBorder"))) {
      tabAreaInsets.right = 0;
      contentBorderInsets.right = 0;
    }

    if ("half".equals(this.tabPane.getClientProperty("rightBorder"))) {
      tabAreaInsets.right = tabAreaInsets.right / 2;
      contentBorderInsets.right = contentBorderInsets.right / 2;
    }

    if (Boolean.FALSE.equals(this.tabPane.getClientProperty("leftBorder"))) {
      tabAreaInsets.left = 0;
      contentBorderInsets.left = 0;
    }

    if ("half".equals(this.tabPane.getClientProperty("leftBorder"))) {
      tabAreaInsets.left = tabAreaInsets.left / 2;
      contentBorderInsets.left = contentBorderInsets.left / 2;
    }

    if (Boolean.FALSE.equals(this.tabPane.getClientProperty("bottomBorder"))) {
      tabAreaInsets.bottom = 0;
      contentBorderInsets.bottom = 0;
    }

    if (Boolean.FALSE.equals(this.tabPane.getClientProperty("roundEdge"))) {
      roundEdge = false;
    }

  }

  @Override
  protected Insets getContentBorderInsets(int tabPlacement) {
    return contentBorderInsets;
  }

  @Override
  protected Font getTabFont(boolean isSelected) {
    return tabPane.getFont().deriveFont(14f).deriveFont(Font.BOLD);
  }

  @SuppressWarnings("deprecation")
  @Override
  protected FontMetrics getFontMetrics() {
    Font font = getTabFont(false);
    return Toolkit.getDefaultToolkit().getFontMetrics(font);
  }

  @Override
  protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
    Graphics2D g2D = (Graphics2D) g;
    RenderingHints savedRenderingHints = g2D.getRenderingHints();
    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    if (isSelected) {
      g.setColor(new Color(141, 165, 179));
    }
    else {
      g.setColor(new Color(41, 41, 41));
    }

    g.fillRect(x, y, w, h);

    if (isSelected) {
      int[] xPoints = { x + (w / 2 + 10), x + (w / 2 - 10), x + (w / 2) };
      int[] yPoints = { y + h, y + h, y + h + 10 };
      g.fillPolygon(xPoints, yPoints, xPoints.length);
    }
    g2D.setRenderingHints(savedRenderingHints);
  }

  @Override
  protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
    Graphics2D g2D = (Graphics2D) g.create();
    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int xt = contentBorderInsets.left;
    int yt = 0;
    int wt = w - contentBorderInsets.left - contentBorderInsets.right;
    int ht = h - contentBorderInsets.bottom + BORDER_RADIUS;

    g2D.setColor(AbstractLookAndFeel.getBackgroundColor());

    if (roundEdge) {
      g2D.fillRoundRect(xt, yt, wt, ht, BORDER_RADIUS, BORDER_RADIUS);
    }
    else {
      g2D.fillRect(xt, yt, wt, ht);
    }
  }

  @Override
  protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect,
      boolean isSelected) {
  }

  @Override
  protected void paintTopTabBorder(int tabIndex, Graphics g, int x1, int y1, int x2, int y2, boolean isSelected) {
    if (!isSelected) {
      g.setColor(new Color(23, 23, 23));
      g.drawLine(x2 - 1, y1, x2 - 1, y2 - 1);
    }
    g.setColor(new Color(56, 56, 56));
    g.drawLine(x2, y1, x2, y2 - 1);
  }

  @Override
  protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect,
      boolean isSelected) {
    Graphics2D g2D = (Graphics2D) g;
    Object savedRenderingHint = null;
    if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
      savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
      g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
    }

    // plain text
    g.setFont(font);
    int mnemIndex = -1;
    if (JTattooUtilities.getJavaVersion() >= 1.4) {
      mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);
    }

    if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
      if (isSelected) {
        g.setColor(new Color(240, 240, 240));
      }
      else {
        g.setColor(new Color(110, 110, 110));
        // g.setColor(AbstractLookAndFeel.getTheme().getForegroundColor());
      }
      JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
    }
    else { // tab disabled
      g.setColor(tabPane.getBackgroundAt(tabIndex).brighter());
      JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
      g.setColor(tabPane.getBackgroundAt(tabIndex).darker());
      JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x - 1, textRect.y + metrics.getAscent() - 1);
    }

    if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
      g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
    }

  }

  @Override
  protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {
    return 0;
  }

  @Override
  protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
    // redraw the black border
    Rectangle clipRect = g.getClipBounds();
    if (clipRect.y < maxTabHeight) {
      g.setColor(tabAreaBackground);
      g.fillRect(0, 0, tabPane.getWidth(), maxTabHeight);
    }

    super.paintTabArea(g, tabPlacement, selectedIndex);
  }

  @Override
  public void paint(Graphics g, JComponent c) {
    super.paint(g, c);

    // redraw the black border
    Rectangle clipRect = g.getClipBounds();
    if (clipRect.y < maxTabHeight) {
      g.setColor(tabAreaBackground);
      g.fillRect(0, 0, tabPane.getWidth(), maxTabHeight);
    }
  }
}
