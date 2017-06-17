# RecyclerView #

RecyclerView can be used as a list view when there is a large number of items in the list.


```java
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private static final String TAG = GreenAdapter.class.getSimpleName();
    final private ListItemClickListener mOnClickListener;
    private static int viewHolderCount;
    private int mNumberItems;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public RecyclerViewAdapter(int numberOfItems, ListItemClickListener listener) {
        mNumberItems = numberOfItems;
        mOnClickListener = listener;
        viewHolderCount = 0;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        // Set data/items on viewholder
        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder
        implements OnClickListener {

        public NumberViewHolder(View itemView) {
            super(itemView);
            // SetUp UI items
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            listItemNumberView.setText(String.valueOf(listIndex));
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}

public class MainActivity extends AppCompatActivity
        implements RecylerAdapter.ListItemClickListener {

    private static final int NUM_LIST_ITEMS = 100;

    private RecylerAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_numbers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new RecylerAdapter(NUM_LIST_ITEMS, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_refresh:
                mAdapter = new RecylerAdapter(NUM_LIST_ITEMS, this);
                mRecyclerView.setAdapter(mAdapter);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
```

## RecyclerView Swiping ##

The following shows how we can handle moving and swiping on a RecyclerView

```java
new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        long id = (long) viewHolder.itemView.getTag();
        removeXXX(id);
        mAdapter.swapCursor(getAllXXX());
    }

}).attachToRecyclerView(xxxRecyclerView);
```