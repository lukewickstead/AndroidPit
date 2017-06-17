#Material Design - Surfaces#

##Surfaces##

Allow segmentation; think of areas defining their own shadow.

Try not to have more than 5 on a screen

android:elevation="4dp"
Higher numbers cause bigger shadows

We can get the standard preferred elevation and shadows for all element types here:

https://material.io/guidelines/material-design/elevation-shadows.html#objects-in-3d-space-elevation

##Fab - Floating Action Button##

Floats above all content and should be higher. Can scroll with content. Should have standard size and elevation; 56dp/40p diameter. Elevation of resting 6dp and pressed 12dp. Sits well on seems of two surfaces and corers Should only use a maximum of one per screen. if you are struggling to identify which action should be a fab then consider having none.


https://material.io/guidelines/material-design/elevation-shadows.html#objects-in-3d-space-elevation

Add design and appCompat to the dependencies. use the latest and greatest
android:layout_gravity="end" for one of the XML attributes for the FAB.

```xml
<android.support.design.widget.FloatingActionButton
        android:layout_height="wrap_content"
        android:layout_width="wrap_content"
        android:src="@drawable/fab_plus"
        app:fabSize="normal"
        app:elevation="6dp"
        app:pressedTranslationZ="12dp"
        app:borderWidth="0dp"
        app:layout_anchorGravity="bottom|right|end"
        android:layout_marginLeft="16dp"
        android:layout_marginRight="16dp"
        android:layout_alignTop="@+id/fab"
android:layout_toEndOf="@+id/fab" />
```

- fabSize can be normal or mini; 40dp/56dp
- layout_gravity; end is right for left to right orientations.
- pressedTransaltionZ is the elevation when pressed.

When adding in appCompat you should change the AppThem from a material one to an appCompat one.

##Surface Reaction##

Ripple of action when you touch something.

Button; android:background="@drawable/oval_ripple.xml" -> <ripple><item><shape>

Then use a stateListAnimator for animating upon state. We animate transalationZ and not elevation. These are added to the actual ones.

##Creating Paper Transformations##

https://github.com/udacity/ud862-samples/tree/master/SimplePaperTransformations

Circular Review Effect - when selecting highlight it comes in as a circle.

onClick. ViewAnimationUtils.createCircularReveal(view, centerX, centgerY, startRadius, endRadius ).

vuew.setBackgroundColor
anim.start();

##Responding To Scroll Events##

- https://developer.android.com/reference/android/support/design/widget/AppBarLayout.LayoutParams.html#setScrollFlags(int)
- https://developer.android.com/reference/android/support/design/widget/CoordinatorLayout.LayoutParams.html#setBehavior(android.support.design.widget.CoordinatorLayout.Behavior)

Seam to step transition. Two surfaces form a seam, scroll together and then one raises up for the other to move under. Think toolbar which minimises until its minimum and then the list view goes under neath

AppBarLayout and CollapsingToolbarLauyout can do this with CoordinatorLayout which is in the android design support library..

https://github.com/udacity/ud862-samples/tree/master/ScrollEventsDemo

- CoordinatorLayout
	- AppBarLayout
		- CollapsingToolbarLayout. Set app:layout_scrollFlags = "scroll|exitUntilCollapsed
	- RecyclerView. set app:layout_Behaviour to @string/appbar_scrolling_view_behaviour