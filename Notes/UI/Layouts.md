# Layouts #

Layouts are inflated in an activity within the onCreate method via the setContentView method.

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // res/layout/activity_main.xml
    setContentView(R.layout.activity_main);
   xxxEditText = (EditText) findViewById(R.id.et_xxx_box);
}
```

## Layout Types ##

- Constraint Layout
	- Useful for complex responsive(ish) views; setting up constraints for rules of how objects should be displayed relative to each other.
- LinearLayout
	- Can be vertical or horizontal
	- android:orientation="horizontal"
- RelativeLayout
	- used to define items relative to each other
- FrameLayout
- TableLayout

## Common Attributes ##

- Size
	- android:layout\_width
	- android:layout\_height
	- match\_parent takes the size of the parent for the width/height
	- wrap\_content takes the size of the content
	- dp is device independent pixels
- Margin vs Padding
	- Margin is space between elements
	- Padding is the space between the layout and its contents/children
	- android:layout\_margin
	- android:padding
- Gravity
	- android:layout\_gravity
		- Sets its own gravity for position in its parents relative to its children 
	- android:gravity
		- It aligns the children elements
	- center\_horizontal|bottom
- Weight
	- Can be used to define how much space items can take up in a linearlayout
	- android:weightSum="3" can be used when space is required rather than setting up dummy views

```xml
<LinearLayout android:layout_width="match_parent"
android:layout_height="match_parent"
android:orientation="horizontal" >
    <TextView android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="@string/left"
        android:background="#9cf" />
    <TextView android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="2"
        android:text="@string/right"
        android:background="#c9f" />
</LinearLayout>
```

## Relative Layouts ##

Relative layout allows children to define where they are relative to their parent or other elements

### Relative to sibling ###

- android:layout_above
- android:layout_below
- android:layout_toLeftOf
- android:layout_toRightOf

### Relative to parent ###

- android:layout_alignParentTop
- android:layout_alignParentBottom
- android:layout_alignParentLeft
- android:layout_alignParentRight
- android:layout_centerHorizontal
- android:layout_centerVertical
- android:layout_centerInParent

### Relative alignment ###

- android:layout_alignTop
- android:layout_alignBottom
- android:layout_alignLeft
- android:layout_alignRight
- android:layout_alignBaseline


The following can be used to align to parent if their siblng is gone. android:layout_alignWithParentIfMissing
="true"

android:visibility="gone" // element is not visible and also takes up no space!

## FrameLayout ##

FrameLayout is useful when overlapping views or one element.
Can be used with gravity margin etc.
Has android:foreground to allow setting drawable element on the foreground of all children

android:foregroundGravity="top|left" can be used for the gravioty if the element is to be positioned

## Table Layout ##

TableLayout is used with TableRow elements and TextView to define cells.

- android:layout_column="1" can be used to position an element for when cells are missing
- android:layout_span="2" can be used for span more than one cell
- android:shrinkColumns="* can be used to ensure single cell rows do not stretch to the full width
- android:stretchColumns can be used to stretch a cell into multiple columns
- android:collapseColumns

## Hierarchy Viewer ##

This seems to be available in android studio without via the adb

Download ViewServer.java from https://github.com/romainguy/ViewServer
Add INTERNET permission to your application.
Enable ViewServer in your activity

There are merge, include, ViewStub elements to help with defining the depth of elements..