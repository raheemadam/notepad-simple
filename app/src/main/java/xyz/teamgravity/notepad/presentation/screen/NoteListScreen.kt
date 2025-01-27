package xyz.teamgravity.notepad.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xyz.teamgravity.notepad.R
import xyz.teamgravity.notepad.core.util.Helper
import xyz.teamgravity.notepad.presentation.component.button.NoteFloatingActionButton
import xyz.teamgravity.notepad.presentation.component.card.CardNote
import xyz.teamgravity.notepad.presentation.component.dialog.NoteAlertDialog
import xyz.teamgravity.notepad.presentation.component.pinlock.NotePinLock
import xyz.teamgravity.notepad.presentation.component.text.TextPlain
import xyz.teamgravity.notepad.presentation.component.topbar.TopBar
import xyz.teamgravity.notepad.presentation.component.topbar.TopBarMoreMenuNoteList
import xyz.teamgravity.notepad.presentation.navigation.MainNavGraph
import xyz.teamgravity.notepad.presentation.screen.destinations.*
import xyz.teamgravity.notepad.presentation.viewmodel.NoteListViewModel

@MainNavGraph(start = true)
@Destination
@Composable
fun NoteListScreen(
    navigator: DestinationsNavigator,
    viewmodel: NoteListViewModel = hiltViewModel()
) {
    if (viewmodel.pinLockShown) {
        NotePinLock(onPinCorrect = viewmodel::onPinCorrect)
    } else {
        val context = LocalContext.current

        Scaffold(
            topBar = {
                TopBar(
                    title = { TextPlain(id = R.string.app_name) },
                    actions = {
                        TopBarMoreMenuNoteList(
                            expanded = viewmodel.menuExpanded,
                            onExpand = viewmodel::onMenuExpand,
                            onDismiss = viewmodel::onMenuCollapse,
                            autoSave = viewmodel.autoSave,
                            onAutoSave = viewmodel::onAutoSaveChange,
                            onPinLock = {
                                navigator.navigate(PinLockScreenDestination)
                                viewmodel.onMenuCollapse()
                            },
                            onDeleteAll = viewmodel::onDeleteAllDialogShow,
                            onSupport = {
                                navigator.navigate(SupportScreenDestination)
                                viewmodel.onMenuCollapse()
                            },
                            onShare = {
                                Helper.shareApp(context)
                                viewmodel.onMenuCollapse()
                            },
                            onRate = {
                                Helper.rateApp(context)
                                viewmodel.onMenuCollapse()
                            },
                            onSourceCode = {
                                Helper.viewSourceCode(context)
                                viewmodel.onMenuCollapse()
                            },
                            onAbout = {
                                navigator.navigate(AboutScreenDestination)
                                viewmodel.onMenuCollapse()
                            }
                        )
                    }
                )
            },
            floatingActionButton = {
                NoteFloatingActionButton(
                    onClick = { navigator.navigate(NoteAddScreenDestination) },
                    icon = Icons.Default.Add,
                    contentDescription = R.string.cd_add_note
                )
            }
        ) { padding ->
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                contentPadding = padding,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, top = 10.dp, end = 10.dp)
            ) {
                items(
                    items = viewmodel.notes,
                    key = { note -> note.id!! }
                ) { note ->
                    CardNote(
                        note = note,
                        onClick = { navigator.navigate(NoteEditScreenDestination(note = it)) }
                    )
                }
            }
            if (viewmodel.deleteAllDialogShown) {
                NoteAlertDialog(
                    title = R.string.confirm_deletion,
                    message = R.string.wanna_delete_all,
                    onDismiss = viewmodel::onDeleteAllDialogDismiss,
                    onConfirm = viewmodel::onDeleteAll
                )
            }
        }
    }
}