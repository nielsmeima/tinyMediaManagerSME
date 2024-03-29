/*
 * Copyright 2012 - 2020 Manuel Laggner
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
package org.tinymediamanager.ui.movies.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.tinymediamanager.core.movie.entities.Movie;
import org.tinymediamanager.ui.IconManager;
import org.tinymediamanager.ui.MainWindow;
import org.tinymediamanager.ui.UTF8Control;
import org.tinymediamanager.ui.actions.TmmAction;
import org.tinymediamanager.ui.movies.MovieUIModule;
import org.tinymediamanager.ui.movies.dialogs.MovieEditorDialog;

/**
 * MovieEditAction - edit movies
 * 
 * @author Manuel Laggner
 */
public class MovieEditAction extends TmmAction {
  private static final long           serialVersionUID = -8473181347332963044L;
  private static final ResourceBundle BUNDLE           = ResourceBundle.getBundle("messages", new UTF8Control()); //$NON-NLS-1$

  @Deprecated
  public MovieEditAction(boolean withTitle) {
    if (withTitle) {
      putValue(NAME, BUNDLE.getString("movie.edit")); //$NON-NLS-1$
    }
    putValue(LARGE_ICON_KEY, IconManager.EDIT);
    putValue(SMALL_ICON, IconManager.EDIT);
    putValue(SHORT_DESCRIPTION, BUNDLE.getString("movie.edit")); //$NON-NLS-1$
  }

  public MovieEditAction() {
    putValue(NAME, BUNDLE.getString("movie.edit")); //$NON-NLS-1$
    putValue(LARGE_ICON_KEY, IconManager.EDIT);
    putValue(SMALL_ICON, IconManager.EDIT);
    putValue(SHORT_DESCRIPTION, BUNDLE.getString("movie.edit")); //$NON-NLS-1$
    putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
  }

  @Override
  protected void processAction(ActionEvent e) {
    List<Movie> selectedMovies = new ArrayList<>(MovieUIModule.getInstance().getSelectionModel().getSelectedMovies());

    int selectedCount = selectedMovies.size();
    int index = 0;

    if (selectedMovies.isEmpty()) {
      JOptionPane.showMessageDialog(MainWindow.getActiveInstance(), BUNDLE.getString("tmm.nothingselected")); //$NON-NLS-1$
      return;
    }

    do {
      Movie movie = selectedMovies.get(index);
      MovieEditorDialog dialogMovieEditor = new MovieEditorDialog(movie, index, selectedCount);
      dialogMovieEditor.setVisible(true);

      if (!dialogMovieEditor.isContinueQueue()) {
        break;
      }

      if (dialogMovieEditor.isNavigateBack()) {
        index -= 1;
      }
      else {
        index += 1;
      }

    } while (index < selectedCount);
  }
}
