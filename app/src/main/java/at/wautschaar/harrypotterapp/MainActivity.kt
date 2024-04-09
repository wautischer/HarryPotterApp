package at.wautschaar.harrypotterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import at.wautschaar.harrypotterapp.network.HPAPI
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

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
                    HogwardsStudentList(HogwardsStudents = studentList)
                }
            }
        }
    }
}

@Composable
fun HogwardsStudentCard(hogwardsStudent: HogwardsStudent, modifier: Modifier = Modifier) {
    Card (modifier = modifier) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "Student Picture")
            Text (text = hogwardsStudent.name, color = Color.White)
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
fun HogwardsStudentCardPreview(hogwardsStudent: HogwardsStudent = HogwardsStudent("1", "Hary Potter", ""), modifier: Modifier = Modifier) {
    Card (modifier = modifier) {
        Column (modifier = Modifier
            .background(Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "Student Picture")
            Text (text = hogwardsStudent.name, color = Color.White)
        }
    }
}
