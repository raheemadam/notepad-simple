package xyz.teamgravity.notepad.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import xyz.teamgravity.notepad.R
import xyz.teamgravity.notepad.core.util.Helper
import xyz.teamgravity.notepad.data.model.NoteModel
import xyz.teamgravity.notepad.presentation.component.button.IconButtonPlain
import xyz.teamgravity.notepad.presentation.component.button.NoteFloatingActionButton
import xyz.teamgravity.notepad.presentation.component.dialog.NoteAlertDialog
import xyz.teamgravity.notepad.presentation.component.text.TextPlain
import xyz.teamgravity.notepad.presentation.component.topbar.TopBar
import xyz.teamgravity.notepad.presentation.component.topbar.TopBarMoreMenuNoteEdit
import xyz.teamgravity.notepad.presentation.navigation.MainNavGraph
import xyz.teamgravity.notepad.presentation.component.textfield.NotepadTextField
import xyz.teamgravity.notepad.presentation.viewmodel.NoteEditViewModel

@MainNavGraph
@Destination(navArgsDelegate = NoteEditScreenNavArgs::class)
@Composable
fun NoteEditScreen(
    navigator: DestinationsNavigator,
    viewmodel: NoteEditViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = viewmodel.event) {
        viewmodel.event.collectLatest { event ->
            when (event) {
                NoteEditViewModel.NoteEditEvent.NoteUpdated -> {
                    navigator.popBackStack()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = { TextPlain(id = R.string.app_name) },
                navigationIcon = {
                    IconButtonPlain(
                        onClick = navigator::popBackStack,
                        icon = Icons.Default.ArrowBackIos,
                        contentDescription = R.string.cd_back_button
                    )
                },
                actions = {
                    TopBarMoreMenuNoteEdit(
                        expanded = viewmodel.menuExpanded,
                        onExpand = viewmodel::onMenuExpand,
                        onDismiss = viewmodel::onMenuCollapse,
                        onDelete = viewmodel::onDeleteDialogShow,
                        onShare = {
                            Helper.shareNote(context, viewmodel.sharedNote)
                            viewmodel.onMenuCollapse()
                        }
                    )
                }
            )
        },
        floatingActionButton = {
            if (!viewmodel.autoSave) {
                NoteFloatingActionButton(
                    onClick = viewmodel::onUpdateNote,
                    icon = Icons.Default.Done,
                    contentDescription = R.string.cd_done_button
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            NotepadTextField(
                title = viewmodel.title,
                onTitleChange = viewmodel::onTitleChange,
                body = viewmodel.body,
                onBodyChange = viewmodel::onBodyChange
            )
        }
        if (viewmodel.deleteDialogShown) {
            NoteAlertDialog(
                title = R.string.confirm_deletion,
                message = R.string.wanna_delete_note,
                onDismiss = viewmodel::onDeleteDialogDismiss,
                onConfirm = viewmodel::onDeleteNote
            )
        }
    }
}

data class NoteEditScreenNavArgs(
    val note: NoteModel
)