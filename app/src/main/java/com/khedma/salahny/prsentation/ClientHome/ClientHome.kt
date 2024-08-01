package com.khedma.salahny.prsentation.ClientHome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.lifecycleScope

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.khedma.salahny.ui.theme.blue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khedma.salahny.R
import com.khedma.salahny.data.CategoryItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.common.api.Scope
import com.khedma.salahny.data.Client
import com.khedma.salahny.prsentation.Categories.PlumberCatViewModel
import com.khedma.salahny.ui.theme.blue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale.Category


@Composable
fun CategoryItem(item: CategoryItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the photo with a description
        Box(
            modifier = Modifier
                .clickable(onClick = onClick) // Make the category item clickable
                .size(100.dp)
                .clip(shape = CircleShape)
        ) {
            Image(
                painter = painterResource(id = item.photo),
                contentDescription = item.description,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        // Display the name of the category
        Text(text = item.name, textAlign = TextAlign.Center)
    }
}
@Composable
fun CategoryList(
    categories: List<CategoryItem>, // Assuming CategoryItem is the correct type
    modifier: Modifier = Modifier,
    onItemClick: (CategoryItem) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        items(categories.size) { index ->
            CategoryItem(
                item = categories[index],
                onClick = { onItemClick(categories[index]) },

            )
        }
    }
}  @Composable
    fun CategoriesScreen(navController: NavController) {
        val vm =PlumberCatViewModel();
        val coroutineScope: CoroutineScope = rememberCoroutineScope()

        // Example data
        val categories = listOf(
            CategoryItem("Plumber", R.drawable.plumber, "Plumber"),
            CategoryItem("Electrician", R.drawable.electrician, "Electriction"),
            CategoryItem("Carpenter", R.drawable.carpenter, "Plumber"),
            CategoryItem("painter", R.drawable.painter ,"Painter")
            // Add more categories as needed
        )


        // Example statement


        CategoryList(categories = categories) { category ->
            // Handle category item click here
            // Navigate to another screen using navController
            // You can pass additional data or parameters as needed
           navController.navigate("${category.name}")




        }
    }

@Composable
fun BottomNavigationBar(navController: NavController) {
    var IsSelected by remember { mutableStateOf(false) }
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        BottomNavigationItem(
            icon = {  Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (IsSelected) MaterialTheme.colorScheme.primary else Color.Gray
            ) },
            label = { Text("Home") },
            selected = true,
            onClick = {
                IsSelected=true
                navController.navigate("ClientHome")
            },

        )

        BottomNavigationItem(
            icon = { Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (IsSelected) MaterialTheme.colorScheme.primary else Color.Gray
            ) },
            label = { Text("Search") },
            selected = IsSelected,
            onClick = {

                    IsSelected=true

                navController.navigate("search")
            }
        )
        BottomNavigationItem(
            icon = { Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = if (IsSelected) MaterialTheme.colorScheme.primary else Color.Gray
            ) },
            label = { Text("Profile") },
            selected = false,
            onClick = {
                navController.navigate("profile")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    ClientHomeScreen(navController = rememberNavController())
}

@Composable
fun appBar(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(color = Color(R.color.dark_blue))
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Text(text = "Salahny" ,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),)
            Spacer(modifier = Modifier.width(10.dp))

        }

    }
}

@Composable
fun ClientHomeScreen(navController: NavController) {
    val vm=HomeViewModel()
    val name=vm.getNameOfUser()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // First Part - Takes 20% of the screen height
       appBar()
       Spacer(modifier = Modifier.height(30.dp))

        // Second Part - Takes 30% of the screen height
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f)

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .align(Alignment.TopCenter)



            ){ CategoriesScreen(navController = navController)}


            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
            ) {
               BottomNavigationBar(navController = navController)
            }
        }
    }
}
