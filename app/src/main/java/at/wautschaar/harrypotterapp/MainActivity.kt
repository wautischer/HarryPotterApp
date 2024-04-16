package at.wautschaar.harrypotterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import at.wautschaar.harrypotterapp.model.HogwardsStudent
import at.wautschaar.harrypotterapp.ui.theme.HarrypotterAppTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import at.wautschaar.harrypotterapp.network.HPAPI
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.wautschaar.harrypotterapp.model.RoomMates
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HarrypotterAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var studentList by remember {
                        mutableStateOf<List<HogwardsStudent>>(emptyList())
                    }
                    LaunchedEffect(Unit){
                        studentList = HPAPI.retrofitService.getStudents()

                    }


                    var roomMateList by remember {
                        mutableStateOf<List<RoomMates>>(emptyList())
                    }
                    LaunchedEffect(Unit) {
                        roomMateList = HPAPI.retrofitService.getRoomMatesHufflepuff()
                    }

                    HogwardsStudentList(HogwardsStudents = studentList)
                    //RoomMatesList(RoomMates = roomMateList)
                }
            }
        }
    }
}

@Composable
fun RoomMatesCard (roomMate: RoomMates, modifier: Modifier = Modifier) {
    Card(modifier = Modifier) {
        Column {
            Text(text = roomMate.name)
        }
    }
}

@Composable
fun RoomMatesList (RoomMates: List<RoomMates>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = Modifier) {
        items(RoomMates) {tempRoomMate ->
            RoomMatesCard(roomMate = tempRoomMate, modifier = Modifier)
        }
    }
}

@Composable
fun HogwardsStudentCard(hogwardsStudent: HogwardsStudent, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.background(Color.DarkGray)){
        Card (modifier = modifier
            .padding(top = 50.dp, start = 10.dp, end = 10.dp)) {
            Column (modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = hogwardsStudent.image,
                    contentDescription = "Student Image",
                    modifier = Modifier
                        .fillMaxWidth(),
                    error = painterResource(R.drawable.hpimageonerror),
                    contentScale = ContentScale.FillWidth //damit aufjedenfall die gesamte Width verwendet wird (egal ob das Bild abgeschnitten wird)
                )
            }
            Column (modifier = Modifier
                .padding(start = 10.dp, bottom = 10.dp)) {
                Text (
                    text = hogwardsStudent.name,
                    color = Color.Black,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp, bottom = 10.dp),
                )
                Text(text = "Species: "+hogwardsStudent.species, fontSize = 20.sp)

                val HouseText
                        = if (hogwardsStudent.house.equals("")){
                    "Unknown"
                } else {
                    hogwardsStudent.house
                }
                Text(text = "House: $HouseText", fontSize = 20.sp)

                val wizardText
                        = if (hogwardsStudent.wizard){
                    "Wizard"
                }else{
                    "No Wizard"
                }
                Text(text = "Wizard status: $wizardText", fontSize = 20.sp)
                Row (modifier = Modifier.padding(top = 10.dp)){
                    Button(
                        onClick = { System.out.println(hogwardsStudent.house) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp),
                        colors = ButtonDefaults.buttonColors(Color.DarkGray)
                    ) {
                        Text(text = "Housemates!")
                    }
                }
            }
        }
    }
}

@Composable
fun HogwardsStudentList(HogwardsStudents: List<HogwardsStudent>, modifier: Modifier = Modifier){
    LazyColumn(modifier = Modifier) {
        items(HogwardsStudents) { tempHogwardsStudent ->
            HogwardsStudentCard(hogwardsStudent = tempHogwardsStudent, modifier = Modifier)
        }
    }
}

@Preview
@Composable
fun HogwardsStudentCardPreview(hogwardsStudent: HogwardsStudent = HogwardsStudent("1", "Hary Potter", "", "", "", false), modifier: Modifier = Modifier) {
    Card (modifier = modifier) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "Student Picture")
            Text (text = hogwardsStudent.name, color = Color.White)
        }
    }
}