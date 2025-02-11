package com.dlsc.preferencesfx.util;

import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static com.dlsc.preferencesfx.util.Constants.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static javafx.collections.FXCollections.observableList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link StorageHandlerImpl}.
 *
 * @author François Martin
 * @author Marco Sanfratello
 */
public class StorageHandlerImplTest {

  private StorageHandler storageHandler;

  @Before
  public void setUp() {
    storageHandler = new StorageHandlerImpl(StorageHandlerImplTest.class);
  }

  @After
  public void tearDown() {
    storageHandler.clearPreferences();
  }

  @Test
  public void testClearPreferences() {
    assertTrue(storageHandler.clearPreferences());
  }

  @Test
  public void saveAndLoadSelectedCategory() {
    final String initialCategory = storageHandler.loadSelectedCategory();
    assertThat(initialCategory, is(nullValue()));

    storageHandler.saveSelectedCategory("foo");
    final String category = storageHandler.loadSelectedCategory();
    assertThat(category, is("foo"));
  }

  @Test
  public void saveAndLoadDividerPosition() {
    final double initialDividerPosition = storageHandler.loadDividerPosition();
    assertThat(initialDividerPosition, is(DEFAULT_DIVIDER_POSITION));

    storageHandler.saveDividerPosition(2 * DEFAULT_DIVIDER_POSITION);
    final double dividerPosition = storageHandler.loadDividerPosition();
    assertThat(dividerPosition, is(2 * DEFAULT_DIVIDER_POSITION));
  }

  @Test
  public void saveAndLoadWindowWidth() {
    final double initialWindowWidth = storageHandler.loadWindowWidth();
    assertThat(initialWindowWidth, is((double) DEFAULT_PREFERENCES_WIDTH));

    storageHandler.saveWindowWidth(2.0 * DEFAULT_PREFERENCES_WIDTH);
    final double windowWidth = storageHandler.loadWindowWidth();
    assertThat(windowWidth, is(2.0 * DEFAULT_PREFERENCES_WIDTH));
  }

  @Test
  public void saveAndLoadWindowHeight() {
    final double initialWindowHeight = storageHandler.loadWindowHeight();
    assertThat(initialWindowHeight, is((double) DEFAULT_PREFERENCES_HEIGHT));

    storageHandler.saveWindowHeight(2.0 * DEFAULT_PREFERENCES_HEIGHT);
    final double windowHeight = storageHandler.loadWindowHeight();
    assertThat(windowHeight, is(2.0 * DEFAULT_PREFERENCES_HEIGHT));
  }

  @Test
  public void saveAndLoadWindowPosX() {
    final double initialWindowPosX = storageHandler.loadWindowPosX();
    assertThat(initialWindowPosX, is((double) DEFAULT_PREFERENCES_POS_X));

    storageHandler.saveWindowPosX(2.0 * DEFAULT_PREFERENCES_POS_X);
    final double windowPosX = storageHandler.loadWindowPosX();
    assertThat(windowPosX, is(2.0 * DEFAULT_PREFERENCES_POS_X));
  }

  @Test
  public void saveAndLoadWindowPosY() {
    final double initialWindowPosY = storageHandler.loadWindowPosY();
    assertThat(initialWindowPosY, is((double) DEFAULT_PREFERENCES_POS_Y));

    storageHandler.saveWindowPosY(2.0 * DEFAULT_PREFERENCES_POS_Y);
    final double windowPosY = storageHandler.loadWindowPosY();
    assertThat(windowPosY, is(2.0 * DEFAULT_PREFERENCES_POS_Y));
  }

  @Test
  public void loadDefaultObject() {
    final Object defaultValue = storageHandler.loadObject("foo", null);
    assertThat(defaultValue, is(nullValue()));

    final Object defaultString = storageHandler.loadObject("foo", "bar");
    assertThat(defaultString, is("bar"));
    assertThat(defaultString, is(instanceOf(String.class)));

    final Object defaultInt = storageHandler.loadObject("foo", 5);
    assertThat(defaultInt, is(5));
    assertThat(defaultInt, is(instanceOf(Integer.class)));

    final Object defaultEnum = storageHandler.loadObject("foo", TestEnum.BAR);
    assertThat(defaultEnum, is(TestEnum.BAR));
    assertThat(defaultEnum, is(instanceOf(TestEnum.class)));

    final Object defaultObject = storageHandler.loadObject("foo", new SomeObject(5, "x"));
    assertThat(defaultObject, is(new SomeObject(5, "x")));
    assertThat(defaultObject, is(instanceOf(SomeObject.class)));
  }

  @Test
  public void saveAndLoadObject() {
    storageHandler.saveObject("foo", "baz");
    final Object loadedString = storageHandler.loadObject("foo", "bar");
    assertThat(loadedString, is("baz"));
    assertThat(loadedString, is(instanceOf(String.class)));

    storageHandler.saveObject("foo", 10);
    final Object loadedInt = storageHandler.loadObject("foo", 5);
    assertThat(loadedInt, is(10));
    assertThat(loadedInt, is(instanceOf(Integer.class)));

    storageHandler.saveObject("foo", TestEnum.FOO);
    final Object loadedEnum = storageHandler.loadObject("foo", TestEnum.BAR);
    assertThat(loadedEnum, is(TestEnum.FOO));
    assertThat(loadedEnum, is(instanceOf(TestEnum.class)));

    storageHandler.saveObject("foo", new SomeObject(6, "y"));
    final Object defaultObject = storageHandler.loadObject("foo", new SomeObject(5, "x"));
    assertThat(defaultObject, is(new SomeObject(6, "y")));
    assertThat(defaultObject, is(instanceOf(SomeObject.class)));
  }

  @Test
  public void saveAndLoadObjectWithNullAsDefault() {
    storageHandler.saveObject("foo", "baz");
    final Object loadedString = storageHandler.loadObject("foo", null);
    assertThat(loadedString, is("baz"));
    assertThat(loadedString, is(instanceOf(String.class)));

    storageHandler.saveObject("foo", 10);
    final Object loadedInt = storageHandler.loadObject("foo", null);
    assertThat(loadedInt, is(10.0));
    assertThat(loadedInt, is(instanceOf(Double.class)));

    storageHandler.saveObject("foo", TestEnum.FOO);
    final Object loadedEnum = storageHandler.loadObject("foo", null);
    assertThat(loadedEnum, is("FOO"));
    assertThat(loadedEnum, is(instanceOf(String.class)));
  }

  @Test
  public void saveAndLoadObjectWithType() {
    storageHandler.saveObject("foo", "baz");
    final Object loadedString = storageHandler.loadObject("foo", String.class, "bar");
    assertThat(loadedString, is("baz"));
    assertThat(loadedString, is(instanceOf(String.class)));

    storageHandler.saveObject("foo", 10);
    final Object loadedInt = storageHandler.loadObject("foo", Integer.class, 5);
    assertThat(loadedInt, is(10));
    assertThat(loadedInt, is(instanceOf(Integer.class)));

    storageHandler.saveObject("foo", TestEnum.FOO);
    final Object loadedEnum = storageHandler.loadObject("foo", TestEnum.class, TestEnum.BAR);
    assertThat(loadedEnum, is(TestEnum.FOO));
    assertThat(loadedEnum, is(instanceOf(TestEnum.class)));

    storageHandler.saveObject("foo", new SomeObject(6, "y"));
    final Object defaultObject = storageHandler.loadObject("foo", SomeObject.class, new SomeObject(5, "x"));
    assertThat(defaultObject, is(new SomeObject(6, "y")));
    assertThat(defaultObject, is(instanceOf(SomeObject.class)));
  }

  @Test
  public void saveAndLoadObjectWithTypeAndNullAsDefault() {
    storageHandler.saveObject("foo", "baz");
    final Object loadedString = storageHandler.loadObject("foo", String.class, null);
    assertThat(loadedString, is("baz"));
    assertThat(loadedString, is(instanceOf(String.class)));

    storageHandler.saveObject("foo", 10);
    final Object loadedInt = storageHandler.loadObject("foo", Integer.class, null);
    assertThat(loadedInt, is(10));
    assertThat(loadedInt, is(instanceOf(Integer.class)));

    storageHandler.saveObject("foo", TestEnum.FOO);
    final Object loadedEnum = storageHandler.loadObject("foo", TestEnum.class, null);
    assertThat(loadedEnum, is(TestEnum.FOO));
    assertThat(loadedEnum, is(instanceOf(TestEnum.class)));

    storageHandler.saveObject("foo", new SomeObject(6, "y"));
    final Object defaultObject = storageHandler.loadObject("foo", SomeObject.class, null);
    assertThat(defaultObject, is(new SomeObject(6, "y")));
    assertThat(defaultObject, is(instanceOf(SomeObject.class)));
  }

  @Test
  public void loadObservableList() {
    final ObservableList emptyList = storageHandler.loadObservableList("foo", observableList(emptyList()));
    assertThat(emptyList, is(observableList(emptyList())));

    final ObservableList nullList = storageHandler.loadObservableList("foo", observableList(asList(null, null, null)));
    assertThat(nullList, is(observableList(asList(null, null, null))));

    final ObservableList stringList = storageHandler.loadObservableList("foo", observableList(asList("a", "b", "c")));
    assertThat(stringList, is(observableList(asList("a", "b", "c"))));

    final ObservableList intList = storageHandler.loadObservableList("foo", observableList(asList(1, 2, 3)));
    assertThat(intList, is(observableList(asList(1, 2, 3))));

    final ObservableList enumList = storageHandler.loadObservableList("foo", observableList(asList(TestEnum.FOO, TestEnum.BAR)));
    assertThat(enumList, is(observableList(asList(TestEnum.FOO, TestEnum.BAR))));
  }

  @Test
  public void saveAndLoadObservableList() {
    storageHandler.saveObject("foo", asList("a", "b", "c"));
    final ObservableList stringList = storageHandler.loadObservableList("foo", observableList(asList("x")));
    assertThat(stringList, is(observableList(asList("a", "b", "c"))));

    storageHandler.saveObject("foo", asList(1, 2, 3));
    final ObservableList intList = storageHandler.loadObservableList("foo", observableList(asList(99)));
    assertThat(intList, is(observableList(asList(1, 2, 3))));

    storageHandler.saveObject("foo", asList(TestEnum.FOO, TestEnum.BAR));
    final ObservableList enumList = storageHandler.loadObservableList("foo", observableList(asList(TestEnum.BAZ)));
    assertThat(enumList, is(observableList(asList(TestEnum.FOO, TestEnum.BAR))));
  }

  @Test
  public void saveAndLoadObservableListWithEmptyDefault() {
    storageHandler.saveObject("foo", asList("a", "b", "c"));
    final ObservableList stringList = storageHandler.loadObservableList("foo", observableList(emptyList()));
    assertThat(stringList, is(observableList(asList("a", "b", "c"))));

    storageHandler.saveObject("foo", asList(1, 2, 3));
    final ObservableList intList = storageHandler.loadObservableList("foo", observableList(emptyList()));
    assertThat(intList, is(observableList(asList(1.0, 2.0, 3.0))));

    storageHandler.saveObject("foo", asList(TestEnum.FOO, TestEnum.BAR));
    final ObservableList enumList = storageHandler.loadObservableList("foo", observableList(emptyList()));
    assertThat(enumList, is(observableList(asList("FOO", "BAR"))));
  }

  @Test
  public void saveAndLoadObservableListWithType() {
    storageHandler.saveObject("foo", asList("a", "b", "c"));
    final ObservableList stringList = storageHandler.loadObservableList("foo", String.class, observableList(asList("x")));
    assertThat(stringList, is(observableList(asList("a", "b", "c"))));

    storageHandler.saveObject("foo", asList(1, 2, 3));
    final ObservableList intList = storageHandler.loadObservableList("foo", Integer.class, observableList(asList(99)));
    assertThat(intList, is(observableList(asList(1, 2, 3))));

    storageHandler.saveObject("foo", asList(TestEnum.FOO, TestEnum.BAR));
    final ObservableList enumList = storageHandler.loadObservableList("foo", TestEnum.class, observableList(asList(TestEnum.BAZ)));
    assertThat(enumList, is(observableList(asList(TestEnum.FOO, TestEnum.BAR))));
  }

  @Test
  public void saveAndLoadObservableListWithTypeAndEmptyDefault() {
    storageHandler.saveObject("foo", asList("a", "b", "c"));
    final ObservableList stringList = storageHandler.loadObservableList("foo", String.class, observableList(emptyList()));
    assertThat(stringList, is(observableList(asList("a", "b", "c"))));

    storageHandler.saveObject("foo", asList(1, 2, 3));
    final ObservableList intList = storageHandler.loadObservableList("foo", Integer.class, observableList(emptyList()));
    assertThat(intList, is(observableList(asList(1, 2, 3))));

    storageHandler.saveObject("foo", asList(TestEnum.FOO, TestEnum.BAR));
    final ObservableList enumList = storageHandler.loadObservableList("foo", TestEnum.class, observableList(emptyList()));
    assertThat(enumList, is(observableList(asList(TestEnum.FOO, TestEnum.BAR))));
  }

  @Test
  public void shaHashing() {
    StorageHandlerImpl storageHandler = new StorageHandlerImpl(StorageHandlerImplTest.class);
    final String result = storageHandler.hash("test");
    assertThat(result, is("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08"));
  }

  public enum TestEnum {
    FOO, BAR, BAZ
  }

  public static class SomeObject {
    final int foo;
    final String bar;

    SomeObject(int foo, String bar) {
      this.foo = foo;
      this.bar = bar;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      SomeObject that = (SomeObject) o;
      return foo == that.foo &&
          bar.equals(that.bar);
    }

    @Override
    public int hashCode() {
      return Objects.hash(foo, bar);
    }
  }
}
