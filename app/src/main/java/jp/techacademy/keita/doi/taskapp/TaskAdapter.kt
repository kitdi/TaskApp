package jp.techacademy.keita.doi.taskapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(context: Context) : BaseAdapter(), Filterable {

    private val mLayoutInflater: LayoutInflater
    var mOriginTaskList = mutableListOf<Task>()
    private var mDisplayTaskList = mutableListOf<Task>()

    init {
        this.mLayoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return mDisplayTaskList.size
    }

    override fun getItem(p0: Int): Any {
        return mDisplayTaskList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return mDisplayTaskList[p0].id.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = p1 ?: mLayoutInflater.inflate(android.R.layout.simple_list_item_2, null)

        val textView1 = view.findViewById<TextView>(android.R.id.text1)
        val textView2 = view.findViewById<TextView>(android.R.id.text2)

        textView1.text = mDisplayTaskList[p0].title

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.JAPANESE)
        val date = mDisplayTaskList[p0].date
        textView2.text = simpleDateFormat.format(date)

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val filteredArrList: MutableList<Task>

                if (constraint.isNullOrEmpty()) {
                    filteredArrList = mOriginTaskList
                } else {
                    val prefix = constraint.toString().lowercase()
                    filteredArrList = mutableListOf<Task>()
                    mOriginTaskList.forEach {
                        if (it.category.contains(prefix)) {
                            filteredArrList.add(it)
                        }
                    }
                }
                results.values = filteredArrList
                results.count = filteredArrList.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mDisplayTaskList = results?.values as MutableList<Task>
                notifyDataSetChanged()
            }
        }
    }
}