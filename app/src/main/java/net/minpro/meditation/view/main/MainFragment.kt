package net.minpro.meditation.view.main


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.fragment_main.*
import net.minpro.meditation.R
import net.minpro.meditation.databinding.FragmentMainBinding
import net.minpro.meditation.util.PlayStatus
import net.minpro.meditation.viewmodel.MainViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        val view = binding.root
        return view
        //return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        binding.apply {
            viewmodel = viewModel
            setLifecycleOwner(activity)
        }

        viewModel.initParameters()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.playStatus.observe(this, Observer { status ->

            // 見た目
            updateUi(status!!)

            // 動き
            when (status) {
                PlayStatus.BEFORE_START -> {

                }
                PlayStatus.ON_START -> {
                    viewModel.countDownBeforeStart()
                }
                PlayStatus.RUNNING -> {
                    viewModel.startMeditation()
                }
                PlayStatus.PAUSE -> {
                    viewModel.pauseMeditation()
                }
                PlayStatus.END -> {
                    viewModel.finishMeditation()
                }
            }
        })
        viewModel.themePicFileResId.observe(this, Observer { themePicResId ->
            loadBackgroundImage(this, themePicResId, meditation_screen)
        })
    }

    private fun updateUi(status: Int) {
        when(status){
            PlayStatus.BEFORE_START -> {
                binding.apply {
                    btnPlay.apply {
                        visibility = View.VISIBLE
                        setBackgroundResource(R.drawable.button_play)
                    }
                    btnFinish.visibility = View.INVISIBLE
                    txtShowMenu.visibility = View.INVISIBLE
                }
            }
            PlayStatus.ON_START -> {
                binding.apply {
                    btnPlay.visibility = View.INVISIBLE
                    btnFinish.visibility = View.INVISIBLE
                    txtShowMenu.visibility = View.VISIBLE
                }
            }
            PlayStatus.RUNNING -> {
                binding.apply {
                    btnPlay.apply {
                        visibility = View.VISIBLE
                        setBackgroundResource(R.drawable.button_pause)
                    }
                    btnFinish.visibility = View.INVISIBLE
                    txtShowMenu.visibility = View.VISIBLE
                }
            }
            PlayStatus.PAUSE -> {
                binding.apply {
                    btnPlay.apply {
                        visibility = View.VISIBLE
                        setBackgroundResource(R.drawable.button_play)
                    }
                    btnFinish.apply {
                        visibility = View.VISIBLE
                        setBackgroundResource(R.drawable.button_finish)
                    }
                    txtShowMenu.visibility = View.VISIBLE
                }
            }
            PlayStatus.END -> {

            }
        }
    }

    private fun loadBackgroundImage(mainFragment: MainFragment, themePicResId: Int?, meditation_screen: ConstraintLayout?) {
        Glide.with(mainFragment).load(themePicResId).into(object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                meditation_screen?.background = resource
            }
        })
    }

}
