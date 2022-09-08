package online.onenut.keeps.views

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import online.onenut.keeps.entity.KeepNotes

class KeepState() {

    val isEditClicked = mutableStateOf(false)

    val searchTFS = mutableStateOf(TextFieldValue())


    val titleTFS = mutableStateOf(TextFieldValue())
    val descriptionTFS = mutableStateOf(TextFieldValue())
    val isFaviouriteFS = mutableStateOf(false)
    val currentID: MutableState<Long?> = mutableStateOf(null)

    val listOfKeep = mutableStateListOf<KeepNotes>()
    var lastID: Long = 1

    private fun generateID(): Long {
        lastID++
        return lastID
    }

    fun loadKeep(keep: KeepNotes) {
        titleTFS.value = TextFieldValue(keep.title.value)
        descriptionTFS.value = TextFieldValue(keep.description.value)
        currentID.value = keep.ID
        isEditClicked.value = true
    }

    fun updateOrCreateKeep() {
        if (currentID.value == null) {
            if (descriptionTFS.value.text.isNotBlank())
                createKeep()
        } else {
            if (listOfKeep.isNotEmpty() || listOfKeep.map { it.ID == currentID.value }
                    .isNotEmpty()) {
                var s: KeepNotes? = null

                listOfKeep.forEach {
                    if (it.ID == currentID.value)
                        s = it ?: null
                }

                s?.apply {
                    this.title.value = titleTFS.value.text
                    this.description.value = descriptionTFS.value.text
                }

                restTextFieldValue()
            }
        }
    }

    private fun createKeep() {
        val keep = KeepNotes(
            ID = generateID(),
            title = mutableStateOf(titleTFS.value.text),
            description = mutableStateOf(descriptionTFS.value.text),
            createdTime = "",
            modifiedTime = ""
        )
        listOfKeep.add(keep)
        restTextFieldValue()
    }

    private fun restTextFieldValue() {
        titleTFS.value = TextFieldValue()
        descriptionTFS.value = TextFieldValue()
        isFaviouriteFS.value = false
    }

}