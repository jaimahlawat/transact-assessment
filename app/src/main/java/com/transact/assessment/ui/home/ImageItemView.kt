package com.transact.assessment.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.transact.assessment.R
import com.transact.assessment.domain.model.ImageInfo

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageItemView(imageInfo: ImageInfo?) {
    Column (
        modifier = Modifier
            .wrapContentSize()
    ){
        GlideImage(
            model = imageInfo?.downloadUrl,
            contentScale = ContentScale.Crop,
            loading = placeholder(R.drawable.placeholder),
            contentDescription = imageInfo?.author,
            modifier = Modifier
                .aspectRatio(16f / 9f)
                .fillMaxSize()
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Author: ${imageInfo?.author}")
    }
}

@Preview(showBackground = true)
@Composable
fun ImageItemViewPreview() {
    ImageItemView(imageInfo = null)
}