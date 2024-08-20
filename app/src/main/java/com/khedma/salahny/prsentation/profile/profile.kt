package com.khedma.salahny.prsentation.profile
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.khedma.salahny.R
import com.khedma.salahny.SalahlyApplication
import com.khedma.salahny.data.SharedPreferencesManager


@Composable
fun ProfileScreen(navController: NavController) {
    val context = SalahlyApplication.getApplicationContext()
    val name = SharedPreferencesManager.name
    val email = SharedPreferencesManager.email
    val phone = SharedPreferencesManager.phone
    val state = SharedPreferencesManager.State
    val neghborhood = SharedPreferencesManager.neghborhood


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 14.dp)
        )
        {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = colorResource(id = R.color.Blue)

            )
            Spacer(modifier = Modifier.width(12.dp))



            Text(
                text = "$name",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterVertically)
            )


            // Email


        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = colorResource(id = R.color.GRAY)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$email",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = colorResource(id = R.color.GRAY)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$phone",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = colorResource(id = R.color.GRAY)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$state , $neghborhood",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

        }

        Divider(
            color = Color.LightGray,
            thickness = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp, start = 24.dp)
                    .clickable {
                        navController.navigate("favourite")
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = colorResource(id = R.color.Blue)

                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Your Favourite Workers",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black ,
                    modifier = Modifier.align(Alignment.CenterVertically)

                )

                // Edit Profile Button

            }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 24.dp)
                .clickable {
                    navController.navigate("setting")
                }
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = colorResource(id = R.color.Blue)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Setting",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black ,
                modifier = Modifier.align(Alignment.CenterVertically))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 24.dp)
                .clickable {
                    navController.navigate("favourites")
                }
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = colorResource(id = R.color.Blue)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Edit",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black ,
                modifier = Modifier.align(Alignment.CenterVertically))
        }



                        Spacer(modifier = Modifier.height(60.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 24.dp)
                .clickable {
                    navController.navigate("ClientLogin")
                }
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = Color.Red

            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Log out",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black ,
                modifier = Modifier.align(Alignment.CenterVertically)

            )

            // Edit Profile Button

        }
    }
}
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}

