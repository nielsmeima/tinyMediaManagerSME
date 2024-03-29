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
package org.tinymediamanager.ui.converter;

import java.util.Locale;
import java.util.ResourceBundle;

import org.jdesktop.beansbinding.Converter;
import org.tinymediamanager.ui.UTF8Control;

/**
 * The Class VoteCountConverter.
 * 
 * @author Manuel Laggner
 */
public class VoteCountConverter extends Converter<Integer, String> {
  /** @wbp.nls.resourceBundle messages */
  private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("messages", new UTF8Control()); //$NON-NLS-1$
  private Locale                      locale = Locale.getDefault();

  @Override
  public String convertForward(Integer arg0) {
    if (arg0 instanceof Integer && arg0 > 0) {
      return String.format(locale, "(%,d %s)", arg0, BUNDLE.getString("metatag.votes"));
    }
    return "";
  }

  @Override
  public Integer convertReverse(String arg0) {
    return null;
  }
}
