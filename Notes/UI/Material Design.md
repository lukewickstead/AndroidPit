# Material Design - Android Design Fundamentals #

https://guides.codepath.com/android/Material-Design-Primer
https://guides.codepath.com/android#designing-and-styling-views
https://romannurik.github.io/AndroidAssetStudio/

## Qualifiers ##

- https://developer.android.com/guide/practices/screens_support.html#qualifiers
- https://developer.android.com/guide/topics/resources/providing-resources.html

- Resolutions
	- nodpi
	- hdpi
	- mdpi
	- xhdpi
	- xxhdpi
- API Version
	- vXX for API


##State List##

State lists are xml files that define assets to use given a state of a UI element.

Uses the first one which meets the minimum requirements. Therefore you should place more specific criteria at the top.

## Gmail Example Layout ##

- Linear Layout ( Vertical Align)
	- Toolbar; layout_width=match_parent, layout_height=56dp
	- FrameLayout; layout_weight=1, layout_height=8 
		- ListView; layout_width=match_parent, layout_height=match_parent
		- Button; layout_gravity=bottom|right

## Common Design Patterns ##
### ToolBar ###

- Navigation
- Title
- Actions with the overflow Menu

It is a view group and can take other types of views such as spinner control

ActionBarSize can be used to define the size.

- AppBar is a special case of the Toolbar. Often takes a bigger height

### List To Detail ###

List to detail; list of items where clicking on one shows more details.
Variation is showing both on larger device; list on left and details on right

### Scrolling ###

Scrolling is OK vertically by not intuitive horizontally. Horizontal scrolling is good for page navigation.

Tabs can be changed by swipe

NavigationDraw; panel slides in from left for additional navigation. Can be divided into sections

## Styles and Themes ##

- https://developer.android.com/guide/topics/resources/available-resources.html#stylesandthemes
- https://developer.android.com/reference/android/R.attr.html#textSize
- https://guides.codepath.com/android/Styles-and-Themes