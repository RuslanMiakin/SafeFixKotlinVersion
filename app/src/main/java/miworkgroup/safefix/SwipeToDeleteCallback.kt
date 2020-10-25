package miworkgroup.safefix

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class SwipeToDeleteCallback(private val adapter: CaptionedImagesAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        viewHolder1: ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: ViewHolder, i: Int) {
        val position = viewHolder.adapterPosition
        adapter.deleteItem(position)
    }
}