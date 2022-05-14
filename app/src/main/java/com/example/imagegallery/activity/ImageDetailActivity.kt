package com.example.imagegallery.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.imagegallery.R
import com.example.imagegallery.data.ImageDetails
import com.example.imagegallery.databinding.ActivityImageDetailBinding
import com.example.imagegallery.util.Constants
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class ImageDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityImageDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setImageDetails()
    }

    private fun setImageDetails() {
        val imageDetails = intent.getSerializableExtra(Constants.IMAGE_DETAILS) as? ImageDetails

        imageDetails?.let {
            //fetch title
            binding.tvTitle.text = String.format(this.resources.getString(R.string.title),
                imageDetails.title)

            //fetch image
            Glide.with(this).load(imageDetails.media.m).into(binding.ivSearchImage)

            //fetch description
            val document: Document = Jsoup.parse(imageDetails.description)
            val para = document.select("p").last()
            para?.let {
                binding.tvDescription.text = String.format(this.resources.getString(R.string.description),
                    para.text())
            }

            //fetch image width and height
            val imgDetails = document.select("img")[0].attributes()
            binding.tvWidth.text = String.format(this.resources.getString(R.string.width),
                imgDetails.get("width"))
            binding.tvHeight.text = String.format(this.resources.getString(R.string.height),
                imgDetails.get("height"))

            //fetch author name
            var s = imageDetails.author.substring(imageDetails.author.indexOf("(\"")+1)
            var author = s.substring(1, s.indexOf("\")"));
            binding.tvAuthor.text = String.format(this.resources.getString(R.string.author),author)
        }
    }
}