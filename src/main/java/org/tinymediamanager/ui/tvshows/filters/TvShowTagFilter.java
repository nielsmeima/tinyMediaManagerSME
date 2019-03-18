/*
 * Copyright 2012 - 2019 Manuel Laggner
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
package org.tinymediamanager.ui.tvshows.filters;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;

import org.tinymediamanager.core.Constants;
import org.tinymediamanager.core.tvshow.TvShowList;
import org.tinymediamanager.core.tvshow.entities.TvShow;
import org.tinymediamanager.core.tvshow.entities.TvShowEpisode;
import org.tinymediamanager.ui.components.TmmLabel;

/**
 * This class implements a tag filter for the TV show tree
 * 
 * @author Manuel Laggner
 */
public class TvShowTagFilter extends AbstractCheckComboBoxTvShowUIFilter<String> {
  private TvShowList tvShowList = TvShowList.getInstance();

  public TvShowTagFilter() {
    super();
    buildAndInstallTagsArray();
    PropertyChangeListener propertyChangeListener = evt -> buildAndInstallTagsArray();
    tvShowList.addPropertyChangeListener(Constants.TAG, propertyChangeListener);
  }

  @Override
  public String getId() {
    return "tvShowTag";
  }

  @Override
  protected boolean accept(TvShow tvShow, List<TvShowEpisode> episodes, boolean invert) {
    List<String> tags = checkComboBox.getSelectedItems();

    // search tags of the show
    for (String tag : tags) {
      boolean containsTags = tags.contains(tag);
      if (!invert && containsTags) {
        return true;
      }
      else if (invert && containsTags) {
        return false;
      }

      for (TvShowEpisode episode : episodes) {
        if (invert ^ episode.getTags().contains(tag)) {
          return true;
        }
      }
    }

    return false;
  }

  @Override
  protected JLabel createLabel() {
    return new TmmLabel(BUNDLE.getString("movieextendedsearch.tag")); //$NON-NLS-1$
  }

  private void buildAndInstallTagsArray() {
    List<String> tags = new ArrayList<>(tvShowList.getTagsInTvShows());
    tags.addAll(tvShowList.getTagsInEpisodes());
    Collections.sort(tags);

    setValues(tags);
  }

  @Override
  protected String parseTypeToString(String type) throws Exception {
    return type;
  }

  @Override
  protected String parseStringToType(String string) throws Exception {
    return string;
  }
}
