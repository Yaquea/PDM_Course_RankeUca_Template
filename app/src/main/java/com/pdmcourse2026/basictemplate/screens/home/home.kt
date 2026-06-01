package com.pdmcourse2026.basictemplate.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOptionClick: (String) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val optionsList by viewModel.uiState.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadOptions()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("RankeUca - Vota") },
            )
        }
    ) { innerPadding ->
        if (loading) {
            Text("Cargando...", modifier = Modifier.padding(innerPadding))
        } else if (error != null) {
            Text("Error: $error", modifier = Modifier.padding(innerPadding))
        } else {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(optionsList) { option ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable { viewModel.vote(option.id) }
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            AsyncImage(
                                model = option.imageUrl,
                                contentDescription = option.name,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = option.name,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                            Text(text = "Votos: ${option.votes}")
                        }
                    }
                }
            }
        }
    }
}
