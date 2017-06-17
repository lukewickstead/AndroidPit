# Data Binding #
## Prerequisites ##

- Gradle plugin 1.3.0 or later
- Data binding - 1.0-rc1
- Android Support Repository
- Android Support Library

## Basic Data Binding ##

The simplest form of binding involves a data source. Each element can have its value bound to it by binding it an identifier of the data source.

```xml
<layout>
    <data>
        <variable
            name="dataSource"
            type="com.foo.DataSource" />
    </data>
<LinearLayout>
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{dataSource.message}"/>
    </LinearLayout>
</layout>
```

The above will match element on DataSource by getMessage(), message() or the field message, as long as it can access it.

The data source is then bound to the activity by the method setDataSourceName.

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    DataSource dataSource = DataSource.get("World");
    binding.setDataSource(dataSource);
}
```
Data binding should be enabled within the gradle build file, along with the dependencies for the binding library.

```
dataBinding {
    enabled = true
    }
```

## Fragment Binding ##

When binding to fragments the FragmentDataBinding should be used to inflate the view with binding within the onCreateView.
```java

public class DataFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentDataBinding binding = FragmentDataBinding.inflate(inflater, container, false);
        DataSource dataSource = DataSource.get("Fragment Binding");
        binding.setDataSource(dataSource);
        return binding.getRoot();
    }
```

## Binding Included Views ##

In the main layout:

```xml
<include
	layout="@layout/include_view"
	bind:dataSource="@{includeSource}"/>
```

```java
DataSource includeSource = DataSource.get("Pluralsight");
binding.setIncludeSource(includeSource);
```

For RecyclerView set the data source element on the onBindViewHolder

Three ways;

- Inflate and Bind
- Late bind
- Binding Reference

First set the content view.

```java
ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
```

Inflate the view and then bind to it.

```java
View view = inflater.inflate(R.layout.fragment_data, root, false);
FragmentDataBinding binding = DataBindingUtil.bind(view);
// OR `FragmentDataBinding.bind(view);
```

Or inflate and bind at the same time

```java
FragmentDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_data, root, false);
```

Or inflate and bind at the same time; layout file is retrieved internally from the binding class

```java
FragmentDataBinding binding = FragmentDataBinding.inflate(inflater, root, false);
```

Retrieve binding from an already inflated view

```java
FragmentDataBinding binding = DataBindingUtil.getBinding(view);
```

## Data Element ##
### Variable Element ###

```xml
<data>
    <variable
        name="dataSource"
        type="com.foo.DataSource" />
</data>
```

Data can contain any number of variable elements, each holding either value or reference types

There can be multiple variables. These can hold reference or value types, they default to the default value of that type.

Where a type is generic < should be encoded with "& l t ;"

### Import Element ###

The import tag can be used to shorten the type name space to a class.

The type element can be replaced with import similar to using.

```xml
<data>
    <import type="com.foo"/>
    <variable name="dataSource" type="dataSource"/>
</data>
```

Imports can be used to define alias classes.

```java
<import type="com.foo.dataSource"
        alias="TheDataSource"/>
```

### Data class attribute ###

By default, a Binding class is generated based on the name of the layout file; upper case and with no underscore and suffixed with Binding; activity_main becomes ActivityMainBinding within the module  names pace.

The class attribute can be used to control the name of the binding class. The following will name the class DataSourceBinding rather than based upon the activity.

```xml
<data class="DataSourceBinding">
</data>
```

'.' can be used to change the name space; . defines the module name space .xxx will put it inside the xxx node of the module.

### Layout Expression Language ###

A rich expression language exists to produce rich bindings.

- https://developer.android.com/topic/libraries/data-binding/index.html#expression_language

```xml
android:visibility="@{item.isSpecial ? View.VISIBLE : View.INVISIBLE}"
android:text="@{@stringArray/sizes[item.index]}"
android:text="@{item.map[`flavor`]}"
android:text="@{item.isSpecial ? @string/special(item.map[`price`]) : @string/price(item.map[`price`])}"
android:text='@{"" + user.age}'
```

## Custom Bindings ##

@BndingMethods and @BindingMethod can be used to override the inferred getter and setter of bound properties.

```java
@BindingMethods({
       @BindingMethod(
       	type = "android.widget.ImageView",
        attribute = "android:tint",
        method = "setImageTintList"),
})
```

BindingAdapter can be used to create new bindings with custom functionality.

```java
public class TextViewBindingAdapter {
    @BindingAdapter("bind:numberText")
    public static void setNumberText(TextView view, int number){
        view.setText(String.valueOf(number));
    }
}
```

```xml
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_alignParentRight="true"
    app:numberText="@{number}"/>
```

The following uses Picasso to load images.

```java
public class CustomImageViewBindingAdapter {
    private static Picasso picasso;
    private static Picasso getPicasso(Context context) {
        if (picasso == null){
            picasso = Picasso.with(context);
        }

        return picasso;
    }

    @BindingAdapter("bind:imageUrl")
    public static void setImage(ImageView view, String imageUrl)
    {
        Picasso picasso = getPicasso(view.getContext());
        picasso.load(imageUrl).into(view);
    }

    @BindingAdapter({"bind:imageUrl", "bind:imagePlaceholder", "bind:imageError"})
    public static void setImage(ImageView view, String imageUrl, Drawable imagePlaceholder, Drawable imageError)
    {
        Picasso picasso = getPicasso(view.getContext());
        picasso.load(imageUrl)
                .placeholder(imagePlaceholder)
                .error(imageError)
                .into(view);
    }
}
```

The binding will work  as per override. Here as we don't need placeholder we can place in a null.

```xml
<ImageView
	android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:imageUrl="@{imageUrl}"
    app:imagePlaceholder="@{null}"
    app:imageError="@{@android:drawable/stat_sys_warning}"/>
```

```java
binding.setImageUrl("https://developer.android.com/assets/images/android_logo.png");
```

Android strips all name spaces of the attributes unless it is android, here app: is arbitrary

- https://news.realm.io/news/data-binding-android-boyar-mount/

## Event Binding ##

We are binding actions and not values.

Normally we would do:

```xml
android:onClick={listener}
```

Binding to an event be done by:

```java

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    binding.setListeners(new Listeners(binding));
}

public class Listeners implements View.OnClickListener{
    private final ActivityMainBinding binding;

    public Listeners(ActivityMainBinding binding) {
        this.binding = binding;
    }

    @Override
    public void onClick(View v) {
        int number = binding.getNumber();
        binding.setNumber(++number);
    }
}
```

Layout

```xml
<variable
    name="number"
    type="int" />

<variable
    name="listeners"
    type="com.example.eventbinding.MainActivity.Listeners"/>
```

We then bind on the button by

```xml
android:onClick="@{listeners}"/>
```

## Dynamic Binding ##

Sometimes what we bind on is only known at run-time; for example lists and other repeatable data lists.

See examples. In short each layout for an item type in a list has:

```xml
<data>
    <variable
        name="person"
        type="com.example.dynamicbinding.Person" />
</data>
```

The PersonAdapter would then have:

```java
@Override
public PersonAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
ViewDataBinding binding = null;
	switch (viewType) {
	    case 0:
		binding = EmployeeItemBinding.inflate(inflater, viewGroup, false);
		break;
	    case 1:
		binding = ContractorItemBinding.inflate(inflater, viewGroup, false);
		break;
	    case 2:
		binding = VendorItemBinding.inflate(inflater, viewGroup, false);
		break;
	}

	return binding!=null ? new ViewHolder(binding.getRoot()) : null;
}

@Override
public void onBindViewHolder(PersonAdapter.ViewHolder viewHolder, int position) {
	Person person = list.get(position);
	ViewDataBinding binding = viewHolder.getBinding();
	binding.setVariable(BR.person, person);
}
```

You would then have:

```java
public class ActivityMainBinder {
    @BindingAdapter("bind:personList")
    public static void setPersonList(RecyclerView view, List<Person> list) {
        if (list == null) return;
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if (layoutManager == null){
            view.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }

        PersonAdapter adapter = (PersonAdapter) view.getAdapter();

        if (adapter == null) {
            adapter = new PersonAdapter(view.getContext());
            view.setAdapter(adapter);
        }

        adapter.update(list);
    }
}
```

## Observables ##
### Mediator vs Observer Behaviour Patterns ###
#### Mediator ####

The mediator receives a notification of a change from one element and then updates the objects peers based upon the change

The target object defines the listener object.

```java
// target defines the listener interface
public interface Listener {
    void onChanged(Data updated);
}

// Register the listener (mediator)
public void setListener(Listener listener) {
    this.listener;
}

// Notifies the lsitneer of state change
public void change(Data updated) {
    this.listener.onChnaged(updated);
}
```

The mediator registers itself with the target object and then reacts to notification from target object

```java
public void onCreate() {
    target.setListener(this);
}

@override
public void onChange(Data updated) {
    // Update sub-views
}
```

In binding the peers are defined in the layout

The mediator is no longer the activity but has been implemented by the layouts binding class

```java
@Override
public void onCretae() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    Data data = getDate();
    binding.setData(data);
    binding.setListener(this);
}

public void update(Data data) {
    binding.setData(data);
}

// The activity reacts to the event but delegates to the data binders
public void onChange(String message) {
    data.setMessage(message);
    binding.setData(data);
}
```

#### Observer ####

The observable keeps a registry of observers

```java
observers.add(observer);
observers.remove(observer);
```

Notifies observers of state changes

```java
observers.notify(fieldId, value)
```

Perhaps observable are more working on properties and can be more targeted.

Mediator allows better decoupling than observer pattern.

### Implementing Observers ###

```java
public void onCheckChange(Compoundbutton biew, boolean isChecked) {
    binding.setIsOn(isChecked);
}
```

```xml
<variable name="isOn" type="boolean" />
<variable name="listeners" type="com.foo.MainActivity" />
```

``` xml
// On element being changed
:onCheckedChanged="@{listeners.onCheckChnaged}"

// On element responding to being changed
@{isOn ? @color/yellow_on : @color/yellow_off}
```

This would be better improved by having the variable as a model. then setting on the binding the view model.

```java
viewModel = new ViewModel();
binding.setViewModel(viewqModel)

// then on changed event
viewModel.isOn = isChecked;
binding.seViewModel(viewModel);
```

Even better again; our model should be a observable model extending BaseObservable.

```java
public static class ViewModel extends BaseObservable {
    @Bindable public final ObservableBoolean isOn = new ObservableBoolean();
}

viewModel.isOn.set(isChecked);
```

There is an observable field type for each primative type as well observableField<> for reference types and ObservableParcelable<> for reference type which are parceable.

ObservableList<>, XXXArrayList, XXXMap, XXXArrayMap.

# Pitfalls #

Gist of base presenter and view class along with their concrete implementation

- http://bit.ly/data-binding-presenter