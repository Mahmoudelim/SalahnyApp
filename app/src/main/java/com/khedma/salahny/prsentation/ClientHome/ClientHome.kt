package com.khedma.salahny.prsentation.ClientHome

import android.annotation.SuppressLint
import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.khedma.salahny.R
import com.khedma.salahny.data.CategoryItem

@Composable
fun CategoryItem(item: CategoryItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(32.dp))
             .drawBehind {
            // Draw the shadow
            drawRoundRect(
                color = Color.Gray, // Change this to your desired shadow color
                size = size.copy(width = size.width + 16.dp.toPx(), height = size.height + 16.dp.toPx()),
                cornerRadius = CornerRadius(16.dp.toPx(), 16.dp.toPx()),
                topLeft = Offset(32.dp.toPx(), 16.dp.toPx())
            )
        }
            .background(color = colorResource(id = R.color.white))
            .clickable(onClick = onClick)
            .padding(32.dp)
            .width(100.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
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
            Text(
                text = item.name,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun CategoryGrid(
    categories: List<CategoryItem>, // Assuming CategoryItem is the correct type
    modifier: Modifier = Modifier,
    onItemClick: (CategoryItem) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(categories.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                rowItems.forEach { item ->
                    CategoryItem(
                        item = item,
                        onClick = { onItemClick(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriesScreen(navController: NavController) {
    val categories = listOf(
        CategoryItem("Plumber", R.drawable.plumber, "Plumber"),
        CategoryItem("Electrician", R.drawable.electrician, "Electrician"),
        CategoryItem("Carpenter", R.drawable.carpenter, "Carpenter"),
        CategoryItem("Painter", R.drawable.painter, "Painter")
    )

    CategoryGrid(categories = categories) { category ->
        navController.navigate("${category.name}")
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedItem by remember { mutableStateOf("home") }

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (selectedItem == "home") colorResource(id = R.color.BLUE) else Color.Gray
                )
            },
            label = { Text("Home") },
            selected = selectedItem == "home",
            onClick = {
                selectedItem = "home"
                navController.navigate("ClientHome")
            }
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (selectedItem == "search") MaterialTheme.colorScheme.primary else Color.Gray
                )
            },
            label = { Text("Search") },
            selected = selectedItem == "search",
            onClick = {
                selectedItem = "search"
                navController.navigate("search")
            }
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (selectedItem == "profile") MaterialTheme.colorScheme.primary else Color.Gray
                )
            },
            label = { Text("Profile") },
            selected = selectedItem == "profile",
            onClick = {
                selectedItem = "profile"
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

@SuppressLint("ResourceAsColor")
@Composable
fun AppBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color = colorResource(id = R.color.white))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SALAHNY",
                style = TextStyle(
                    color =  colorResource(id = R.color.BLUE),
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun ClientHomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // First Part - Takes 20% of the screen height
        AppBar()
        Spacer(modifier = Modifier.height(50.dp))

        // Second Part - Takes 30% of the screen height
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(900.dp)
                .weight(0.8f),


        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .align(Alignment.TopCenter)
            ) {
                CategoriesScreen(navController = navController)
            }

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
