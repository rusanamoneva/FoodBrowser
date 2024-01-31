package com.example.foodbrowser.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.foodbrowser.response.FoodItem
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.foodbrowser.R

@Composable
fun SearchFoodItemsScreen(
    loadFoodItemsState: SearchFoodItemsViewModel.LoadFoodItems,
    searchValue: String,
    setSearchValue: (String) -> Unit) {
    val context = LocalContext.current
    var message = stringResource(id = R.string.no_items_found)

    when (loadFoodItemsState) {
        SearchFoodItemsViewModel.LoadFoodItems.Error -> {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
        SearchFoodItemsViewModel.LoadFoodItems.Loading -> {
            IndeterminateCircularIndicator()
        }
        SearchFoodItemsViewModel.LoadFoodItems.None -> {
            LoadFoodItems(
                foodItems = arrayListOf(),
                searchValue = searchValue,
                setSearchValue = setSearchValue,
                message = stringResource(id = R.string.search_for_food)
            )
        }
        is SearchFoodItemsViewModel.LoadFoodItems.Success -> {
            message = if (loadFoodItemsState.foodItemsList.isNullOrEmpty()) {
                stringResource(id = R.string.no_items_found)
            } else {
                ""
            }
            LoadFoodItems(
                foodItems = loadFoodItemsState.foodItemsList ?: arrayListOf(),
                searchValue = searchValue,
                setSearchValue = setSearchValue,
                message = message
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadFoodItems(
    foodItems: ArrayList<FoodItem>,
    searchValue: String,
    setSearchValue: (String) -> Unit,
    message: String
) {
    val scroll = rememberScrollState(0)
    val context = LocalContext.current
    val typeThreeOrMoreCharacters = stringResource(id = R.string.type_three_or_more_characters)

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center) {
        Card(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 8.dp)
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(30.dp),
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                val (text, removeText) = createRefs()

                TextField(
                    value = searchValue,
                    onValueChange = {
                        setSearchValue(it)
                    },
                    placeholder = {
                        Text(stringResource(id = R.string.search))
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    modifier = Modifier
                        .horizontalScroll(scroll)
                        .constrainAs(text) {
                            start.linkTo(parent.start, 10.dp)
                            end.linkTo(parent.end, 42.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                        },
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (searchValue.length < 3) {
                                Toast.makeText(context, typeThreeOrMoreCharacters, Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                )

                Image(
                    painter = if (searchValue.isEmpty()) {
                        painterResource(id = R.drawable.baseline_search_24)
                    } else {
                        painterResource(id = R.drawable.baseline_close_24)
                    },
                    contentDescription = "search",
                    modifier = Modifier
                        .clickable {
                            if (searchValue.isNotEmpty()) {
                                setSearchValue("")
                            }
                        }
                        .constrainAs(removeText) {
                            end.linkTo(parent.end, 15.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
        }

        if (foodItems.isEmpty()) {
            Text(text = message, textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxSize(),
                fontSize = 22.sp,
                color = Color.Black)
        } else {
            LazyColumn(modifier = Modifier
                .padding(top = 35.dp)
                .fillMaxWidth()) {
                items(foodItems) {
                    FoodRow(foodItem = it)
                }
            }
        }
    }
}

@Composable
fun FoodRow(foodItem: FoodItem) {
    val context = LocalContext.current

    Spacer(modifier = Modifier.height(2.dp))
    Row(modifier = Modifier
        .background(Color.Gray, RoundedCornerShape(6.dp))
        .padding(top = 10.dp, bottom = 10.dp, start = 6.dp, end = 6.dp)
        .fillMaxWidth()
        .clickable {
            Toast
                .makeText(context, foodItem.name, Toast.LENGTH_SHORT)
                .show()
        }) {
        Text(text = foodItem.name, modifier = Modifier.padding(vertical = 10.dp))
    }
}

@Composable
fun IndeterminateCircularIndicator() {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center)
    {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
