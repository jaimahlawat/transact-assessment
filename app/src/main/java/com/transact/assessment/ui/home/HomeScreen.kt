package com.transact.assessment.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.transact.assessment.R
import com.transact.assessment.domain.model.Filter
import com.transact.assessment.domain.model.ImageInfo
import com.transact.assessment.ui.common.DropDownSelectorView
import com.transact.assessment.ui.common.ErrorView
import com.transact.assessment.util.rememberLazyListState
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = getViewModel()
) {
    val images = viewModel.imageList.collectAsLazyPagingItems()
    val filters by viewModel.filters.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.selectedFilter.collectAsStateWithLifecycle()

    Box (modifier = Modifier.fillMaxSize()){
        when (images.loadState.refresh) {
            is LoadState.Error -> {
                ErrorView(modifier = Modifier.fillMaxSize()) {
                    images.retry()
                }
            }
            LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is LoadState.NotLoading -> {
                ImageListingView (
                    images = images,
                    filters = filters,
                    selectedFilter = selectedFilter,
                    onItemSelected = {
                        viewModel.updateFilter(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun ImageListingView(
    images: LazyPagingItems<ImageInfo>,
    filters: List<Filter>,
    selectedFilter: String?,
    onItemSelected: (Filter?) -> Unit,
) {
    val listState = images.rememberLazyListState()

    Column {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropDownSelectorView (
                modifier = Modifier
                    .weight(1f),
                list = filters,
                selectedFilter = selectedFilter,
            ) { filter ->
                onItemSelected(filter)
            }

            Button(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(end = 10.dp),
                content = {
                    Text(text = stringResource(id = R.string.text_clear_filter))
                },
                onClick =  {
                    onItemSelected(null)
                }
            )
        }

        LazyColumn (
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(horizontal = 8.dp),
            state = listState
        ) {
            items(count = images.itemCount) { index ->
                ImageItemView(images[index])
            }

            if (images.loadState.append is LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            } else if(images.loadState.append is LoadState.Error) {
                item {
                    ErrorView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        images.retry()
                    }
                }
            }
        }
    }
}