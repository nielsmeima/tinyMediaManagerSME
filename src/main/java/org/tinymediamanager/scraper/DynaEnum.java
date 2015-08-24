/*
 * Copyright 2012 - 2015 Manuel Laggner
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
package org.tinymediamanager.scraper;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Class DynaEnum.
 * 
 * @param <E>
 *          the element type
 * @author Manuel Laggner
 * @since 1.0
 */
public class DynaEnum<E extends DynaEnum<E>> {
  private static Map<Class<? extends DynaEnum<?>>, Map<String, DynaEnum<?>>> elements = new LinkedHashMap<Class<? extends DynaEnum<?>>, Map<String, DynaEnum<?>>>();
  private final String                                                       name;
  protected final int                                                        ordinal;

  public final String name() {
    return name;
  }

  /**
   * Ordinal.
   * 
   * @return the int
   */
  public final int ordinal() {
    return ordinal;
  }

  /**
   * Instantiates a new dyna enum.
   * 
   * @param name
   *          the name
   * @param ordinal
   *          the ordinal
   */
  protected DynaEnum(String name, int ordinal) {
    this.name = name;
    this.ordinal = ordinal;
    Map<String, DynaEnum<?>> typeElements = elements.get(getClass());
    if (typeElements == null) {
      typeElements = new LinkedHashMap<String, DynaEnum<?>>();
      elements.put(getDynaEnumClass(), typeElements);
    }
    typeElements.put(name, this);
  }

  /**
   * Gets the dyna enum class.
   * 
   * @return the dyna enum class
   */
  @SuppressWarnings("unchecked")
  private Class<? extends DynaEnum<?>> getDynaEnumClass() {
    return (Class<? extends DynaEnum<?>>) getClass();
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public final boolean equals(Object other) {
    return this == other;
  }

  @Override
  public final int hashCode() {
    return super.hashCode();
  }

  @Override
  protected final Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
  }

  public final int compareTo(E other) {
    DynaEnum<?> self = this;
    if (self.getClass() != other.getClass() && // optimization
        self.getDeclaringClass() != other.getDeclaringClass()) {
      throw new ClassCastException();
    }
    return self.ordinal - other.ordinal;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public final Class<E> getDeclaringClass() {
    Class clazz = getClass();
    Class zuper = clazz.getSuperclass();
    return (zuper == DynaEnum.class) ? clazz : zuper;
  }

  /**
   * Value of.
   * 
   * @param <T>
   *          the generic type
   * @param enumType
   *          the enum type
   * @param name
   *          the name
   * @return the t
   */
  @SuppressWarnings("unchecked")
  public static <T extends DynaEnum<T>> T valueOf(Class<T> enumType, String name) {
    return (T) elements.get(enumType).get(name);
  }

  /**
   * Read object.
   * 
   * @param in
   *          the in
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   * @throws ClassNotFoundException
   *           the class not found exception
   */
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    throw new InvalidObjectException("can't deserialize enum");
  }

  /**
   * Read object no data.
   * 
   * @throws ObjectStreamException
   *           the object stream exception
   */
  @SuppressWarnings("unused")
  private void readObjectNoData() throws ObjectStreamException {
    throw new InvalidObjectException("can't deserialize enum");
  }

  /**
   * Values.
   * 
   * @param <E>
   *          the element type
   * @return the dyna enum<? extends dyna enum<?>>[]
   */
  public static <E> DynaEnum<? extends DynaEnum<?>>[] values() {
    throw new IllegalStateException("Sub class of DynaEnum must implement method values()");
  }

  /**
   * Values.
   * 
   * @param <E>
   *          the element type
   * @param enumType
   *          the enum type
   * @return the e[]
   */
  @SuppressWarnings("unchecked")
  public static <E> E[] values(Class<E> enumType) {
    Collection<DynaEnum<?>> values = elements.get(enumType).values();
    int n = values.size();
    E[] typedValues = (E[]) Array.newInstance(enumType, n);
    int i = 0;
    for (DynaEnum<?> value : values) {
      Array.set(typedValues, i, value);
      i++;
    }

    return typedValues;
  }
}
