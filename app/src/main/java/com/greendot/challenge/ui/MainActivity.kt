package com.greendot.challenge.ui


import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Parcelable
import android.util.Log
import android.util.Pair
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.greendot.challenge.R
import com.greendot.challenge.model.Summary
import org.reactivestreams.Subscription
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private lateinit var myFibonaccAdapter: FibonacciAdapter

    var numberFib: String = ""
    var timeFib: String = ""

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBundleSaveInstanceState = outState

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Read values from the "savedInstanceState"-object and put them in your textview

    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

    }

    override fun onResume() {
        super.onResume()

    }

    public override fun onPause() {
        super.onPause()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        input_number.setOnEditorActionListener() { v, actionId, event ->
            when (actionId){
            EditorInfo.IME_ACTION_DONE -> {
                val number = input_number.text.toString()
                if(number.isNotEmpty()) {
                    numberFib = number
                    getFibonacci(number.toInt())
                }
                Log.e(TAG, "IME_ACTION_DONE")
                false }
            else -> false
            }
        }

        recycle.setNestedScrollingEnabled(false)
        recycle.setHasFixedSize(true)
        recycle.setItemViewCacheSize(100)
        val layoutManager = LinearLayoutManager(this)
        recycle.setLayoutManager(layoutManager)

        myFibonaccAdapter = FibonacciAdapter(this)
        recycle.setAdapter(myFibonaccAdapter)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == R.id.action_name) {

            Log.e(TAG, "onOptionsItemSelected" )
            include_fragment.visibility = View.VISIBLE
            include_content_main.visibility = View.INVISIBLE
            val textFragment = SummaryFragment.newInstance(Summary(numberFib,timeFib))
            // Get the support fragment manager instance
            val manager = supportFragmentManager
            // Begin the fragment transition using support fragment manager
            val transaction = manager.beginTransaction()
            // Replace the fragment on container
            transaction.replace(R.id.include_fragment,textFragment)
            transaction.addToBackStack(null)
            // Finishing the transition
            transaction.commit()
            return true
        }
        if(id == android.R.id.home){

            include_fragment.visibility = View.INVISIBLE
            include_content_main.visibility = View.VISIBLE

        }
        return super.onOptionsItemSelected(item)
    }

    private fun fibonacci(): Sequence<Pair<Int,Long>>{
        // 92 - max value - 7540113804746346429
        //MAX Long value - 9223372036854775807
        // fibonacci terms
        // 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, ...
        var count = 0
        return generateSequence(Pair(0L,1L), { Pair(it.second, it.first + it.second) }).map{
             Pair(count++,it.first)}
        //generateSequence(Pair(0,1), { Pair(it.second, it.first + it.second) }).map{it.first}
    }

    private fun getFibonacci(NumberFib: Int) {

        Log.e(TAG, "getFibonacci start")

        val tripleList: MutableList<Triple<Int,Long,Long>> = mutableListOf()

        val pairList: MutableList<Pair<Int,Long>> = mutableListOf()

        val timMilSec = measureTimeMillis { pairList.addAll(fibonacci().take(NumberFib+1).toList()) }

        var dis: Disposable? = null

            Observable.fromIterable(pairList)
                    // we want request to happen on a background thread
                    .subscribeOn(Schedulers.io())
                    // we want to be notified about completition on UI thread
                    .observeOn(AndroidSchedulers.mainThread())
                    // here we'll get notified, that operation has either successfully performed OR failed for some reason (specified by `Throwable it`)
                    .subscribe(object : Observer<Pair<Int,Long>> {
                        override fun onSubscribe(s: Disposable) {
                            dis = s
                        }
                        override fun onNext(pair: Pair<Int,Long>) {

                            tripleList.add(Triple(pair.first, pair.second, System.currentTimeMillis()))
                            Log.e(TAG, "getFibonacci number:  ${pair.first} getFibonacci value: ${pair.second}  time: ${System.currentTimeMillis()}")

                        }
                        override fun onComplete() {

                            Log.e(TAG, "getFibonacci number: ${tripleList.last().first} getFibonacci value: ${tripleList.last().second}  time: ${tripleList.last().third} timMilSec: $timMilSec ")
                            myFibonaccAdapter.addFibonaccies(tripleList)
                            timeFib = timMilSec.toString()
                            fibonacci_time.setText("measure time millis: ${timMilSec.toString()}")
                            if(dis?.isDisposed?:false) {dis?.dispose()}
                        }
                        override fun onError(e: Throwable) {
                                 val code = e.toString()
                                Log.e(TAG, "getFibonacci onError:  $code")
                        }
                    })

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {

        val TAG = MainActivity::class.java.getSimpleName()
        private var mBundleConfigurationChangedState: Bundle? = null// = new Bundle();
        private var mBundleSaveInstanceState: Bundle? = null// = new Bundle();
    }


}
