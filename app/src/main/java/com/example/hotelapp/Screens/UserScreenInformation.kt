package com.example.hotelapp.Screens

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hotelapp.data.UserScreenModel
import com.example.hotelapp.ui.theme.Typography
import com.example.hotelapp.ui.theme.baseColor
import com.example.hotelapp.ui.theme.landFont
import com.example.hotelapp.viewmodels.BookedViewModel
import com.example.hotelapp.viewmodels.SharedViewModel
import java.util.*


@Composable
fun UserScreenInformation(sharedMvvm :SharedViewModel , bookedMvvm : BookedViewModel = hiltViewModel())
{


    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 56.dp)
        .verticalScroll(rememberScrollState())
    )
    {
        Text(
            text        = "User Information",
            fontSize    = 18.sp,
            fontFamily  = landFont,
            fontWeight  = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier              = Modifier.fillMaxWidth()   ,
            horizontalArrangement = Arrangement.SpaceBetween  ,
            verticalAlignment     = Alignment.CenterVertically)
        {
          Row(horizontalArrangement = Arrangement.SpaceBetween  ,
              verticalAlignment     = Alignment.CenterVertically)
          {
              ShowDatePickerStart(context = context)
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                  text        = "Select start day",
                  fontFamily  = landFont,
                  fontSize    = 15.sp,
                  color       = baseColor
              )
          }
            Text(
                text        = dateStart.value,
                fontFamily  = landFont,
                fontSize    = 15.sp,
                color       = baseColor
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier              = Modifier.fillMaxWidth()   ,
            horizontalArrangement = Arrangement.SpaceBetween  ,
            verticalAlignment     = Alignment.CenterVertically)
        {
            Row(horizontalArrangement = Arrangement.SpaceBetween  ,
                verticalAlignment     = Alignment.CenterVertically)
            {
                ShowDatePickerEnd(context = context)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text        = "Select end day",
                    fontFamily  = landFont        ,
                    fontSize    = 15.sp           ,
                    color       = baseColor
                )
            }
            Text(
                text        = dateEnd.value ,
                fontFamily  = landFont      ,
                fontSize    = 15.sp         ,
                color       = baseColor    )
        }

        Spacer(modifier = Modifier.height(20.dp))

        val userName  = rememberSaveable { mutableStateOf("") }
        TextField(
            value         =    userName.value ,
            onValueChange = {  userName.value = it },
            modifier      = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            colors =  TextFieldDefaults.textFieldColors(
                cursorColor             = baseColor,
                textColor               = baseColor,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor   = Color.Transparent,
                focusedLabelColor       = baseColor    ,
                unfocusedLabelColor     = baseColor    ,
                leadingIconColor        = baseColor)   ,
             label = { Text(text = "enter your name")} ,
             textStyle = Typography.h1
        )

        Spacer(modifier = Modifier.height(20.dp))

        val userPhone  = rememberSaveable { mutableStateOf("") }
        TextField(
            value         =    userPhone.value ,
            onValueChange = {  userPhone.value = it },
            modifier      = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
            colors =  TextFieldDefaults.textFieldColors(
                cursorColor             = baseColor,
                textColor               = baseColor,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor   = Color.Transparent,
                focusedLabelColor       = baseColor  ,
                unfocusedLabelColor     = baseColor  ,
                leadingIconColor        = baseColor),
            label = { Text(text = "enter your phone")},
            textStyle = Typography.h1
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(contentAlignment = Alignment.Center , modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    val data = sharedMvvm.sharedRoomInformationData
                    val userScreenInformation = UserScreenModel(dateStart.value  , dateEnd.value , userName.value , userPhone.value)

                   if (dateStart.value.isEmpty())
                   {
                       Toast.makeText(context, "Please select start date", Toast.LENGTH_SHORT).show()
                   }
                    else if (dateEnd.value.isEmpty())
                   {
                       Toast.makeText(context, "Please select end date", Toast.LENGTH_SHORT).show()
                   }
                    else if (userName.value.isEmpty())
                   {
                       Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
                   }
                    else if ( userPhone.value.isEmpty())
                   {
                       Toast.makeText(context, "Please enter your phone number", Toast.LENGTH_SHORT).show()
                   }
                   else if (data != null)
                   {
                       bookedMvvm.uploadBookedRoomToFirestore(data = data , userScreenInformation , context)
                    }

                          }  ,
                colors  = ButtonDefaults.buttonColors(
                    backgroundColor = baseColor
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text       =  "Continue"  ,
                    color      =   Color.White ,
                    fontFamily =   landFont,
                    fontSize   =   14.sp

                )
            }
        }

    }
}



val dateStart  =  mutableStateOf("")
val dateEnd    =  mutableStateOf("")
@Composable
fun ShowDatePickerStart(context: Context)
{

    val year  : Int
    val month : Int
    val day   : Int

    val calendar = Calendar.getInstance()
    year   = calendar.get(Calendar.YEAR)
    month  = calendar.get(Calendar.MONTH)
    day    = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()




    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month:Int, dayOfMonth:Int ->
            dateStart.value = "$dayOfMonth/$month/$year"
        },year , month , day
    )

    Box(
        contentAlignment = Alignment.Center ,
        modifier = Modifier
            .size(40.dp)
            .clickable {
                datePickerDialog.show()
            }
    )
    {
        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date" )
    }

}

@Composable
fun ShowDatePickerEnd(context: Context)
{

    val year  : Int
    val month : Int
    val day   : Int

    val calendar = Calendar.getInstance()
    year   = calendar.get(Calendar.YEAR)
    month  = calendar.get(Calendar.MONTH)
    day    = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month:Int, dayOfMonth:Int ->
            dateEnd.value = "$dayOfMonth/$month/$year"
        },year , month , day
    )

    Box(
        contentAlignment = Alignment.Center ,
        modifier = Modifier
            .size(40.dp)
            .clickable {
                datePickerDialog.show()
            }
    )
    {
        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date" )
    }

}