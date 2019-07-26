package com.greendot.challenge.ui

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greendot.challenge.R
import kotlinx.android.synthetic.main.fibonacci_item.view.*

class FibonacciAdapter



(private val mActivity: Activity) : RecyclerView.Adapter<FibonacciAdapter.ViewHolder>() {
    private val mFibonaccies: MutableList<Triple<Int,Long,Long>> = mutableListOf<Triple<Int,Long,Long>>()


    fun deleteFibonaccies() {
        mFibonaccies.clear()
        notifyDataSetChanged()
    }

    fun addFibonaccies(fibonaccies: MutableList<Triple<Int,Long,Long>>) {
        mFibonaccies.clear()
        val rangeStart = mFibonaccies.size
        val rangeEnd = rangeStart + fibonaccies.size
        mFibonaccies.addAll(fibonaccies)

        Log.e(TAG, "mProducts: " + mFibonaccies.size)

        notifyDataSetChanged()
    }

    fun addFibonacci(fibonacci: Triple<Int,Long,Long>) {

        if (!mFibonaccies.contains(fibonacci)) {
            mFibonaccies.add(fibonacci)
            notifyItemInserted(mFibonaccies.size)
        } else {
            notifyItemChanged(mFibonaccies.indexOf(fibonacci))

        }

    }

    fun updateFibonacci(fibonacci: Triple<Int,Long,Long>) {
        if (mFibonaccies.contains(fibonacci)) {
            notifyItemChanged(mFibonaccies.indexOf(fibonacci))
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(mActivity)
        val contactView = inflater.inflate(R.layout.fibonacci_item, parent, false)
        return ViewHolder(contactView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fibonacci = mFibonaccies[position]

        holder.fibonacciNumber.setText(fibonacci.first.toString())
        holder.fibonacciValue.setText(fibonacci.second.toString())

    }

    override fun getItemCount(): Int {
        return mFibonaccies.size
    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val fibonacciNumber: TextView
        val fibonacciValue: TextView

        init {
            fibonacciNumber = itemView.fibonacci_number
            fibonacciValue = itemView.fibonacci_value
        }
    }



    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)

    }

    companion object {

        private val TAG = FibonacciAdapter::class.java.simpleName
    }
}
