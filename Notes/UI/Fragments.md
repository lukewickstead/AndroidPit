#Fragments#

- https://developer.android.com/guide/components/fragments.html

Fragments allow us to segment and reuse parts of a UI. They are like includes but they have their own in-dependant lifecycle.


##Includes##

These allow us to segment complex parts of a ui an to reuse them

```xml
<include
    android:layout_marginTop="16dp"
    android:id="@+id/fragmentLayout"
    layout="@layout/fragmentLayout"
/>
```

They allow us to reuse layouts.

## Fragments ##

Fragments are like includes but they actually have their own event lifecycle. They also help provide more dyamic UIs, particuarly where a fragment on a phone might display on its own and on a tablet next to another fragment.

```xml
    <fragment
        android:id="@+id/fragment_example_id"
        android:name="com.example.FragmentExample"
        android:layout_width="400dp"
        android:layout_height="match_parent" />
```

```java
public class FragmentExample extends Fragment {
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public MyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	final View rootView = inflater.inflate(R.layout.my_fragment, container, false);
	// ...
	return rootView;
    }
}
```
You can get UI elements in the fragment via the normal findViewById in both the owning activity and the fragment. Thought it is probably best to define an interface on the fragment to work between.

## Life Cycle ##

- onAttach
- onCreate
- onCreateView
- onActivityCreated
- onStart
- onPause
- onStop
- onDestroyView
- onDestroy
- onDetach

## Fragment Base Classes ##

- DialogFragment
- ListFragment
- PreferenceFragment

## Loading Fragments Dynamicaly ##

```xml
        <FrameLayout
            android:id="@+id/fragment_id"
            android:layout_width="match_parent"
            android:layout_height="180dp"
            android:scaleType="centerInside"/>
```


```java
 FragmentManager fragmentManager = getSupportFragmentManager();
DynamicFragment dynamicFragment = new DynamicFragment();
dynamicFragment.setOrDoSomething(somethingProvider.Get()));
fragmentManager.beginTransaction()
        .add(R.id.dynamicFragment, dynamicFragment)
        .commit();
```