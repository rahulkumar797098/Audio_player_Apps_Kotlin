package com.example.audioplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView

class MainActivity : AppCompatActivity() {
    lateinit var btn_play : ImageView
    lateinit var mediaPlayer: MediaPlayer
    var isPlaying = false
    lateinit var img : ImageView
    lateinit var animation : LottieAnimationView

    lateinit var seekBar: SeekBar
    lateinit var runnable: Runnable
    private var handler = Handler()
    lateinit var songTitle : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        btn_play = findViewById(R.id.btn_play)

        // here we find lottie animation
        animation = findViewById(R.id.lottieAnim)


        img = findViewById(R.id.img)
//        here  we add custom rotate animation
        val rotate = AnimationUtils.loadAnimation(this,R.anim.rotate_animation)
        img.animation = rotate

        mediaPlayer = MediaPlayer.create(this, R.raw.tum)

        // here song title
        songTitle = findViewById(R.id.songTitle)
        val title = "Hum tere bin kahin reh nahi paate Tum nahi aate toh hum mar jaate Hum tere bin kahin reh nahi paate Tum nahi aate toh hum mar jaate Haaye pyar kya cheez hai yeh jaan nahi paate Hum tere bin kahin reh nahi paate Hum tere bin kahin reh nahi paate Tum nahi aate toh hum mar jaate "
        songTitle.text = title



        //        seek bar
        seekBar = findViewById(R.id.seekbar)
        seekBar.progress = 0
        seekBar.max = mediaPlayer.duration

        btn_play.setOnClickListener {
        musicPlay()
        }
    }

        fun musicPlay(){
            if (isPlaying == false){
                mediaPlayer.start()
                animation.playAnimation()
                songTitle.isSelected = true
                btn_play.setImageResource(R.drawable.pa)
                isPlaying = true
            }else{
                mediaPlayer.pause()
                animation.pauseAnimation()
                btn_play.setImageResource(R.drawable.p)
                isPlaying = false
            }

//            when change the position of seek bar change song duration
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                  if (p2){
                      mediaPlayer.seekTo(p1)
                  }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })

//            here change seekbar according to time
            runnable = Runnable {
                seekBar.progress = mediaPlayer.currentPosition
                handler.postDelayed(runnable,1000)
            }
            handler.postDelayed(runnable,1000)

//            here we add when music is end then seek bar go to start point
            mediaPlayer.setOnCompletionListener {
                btn_play.setImageResource(R.drawable.p)
                seekBar.progress = 0
            }
        }

    }
