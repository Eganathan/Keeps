package online.onenut.keeps.views

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import online.onenut.keeps.entity.KeepNotes

class KeepState() {

    val isEditClicked = mutableStateOf(false)
    val searchState = mutableStateOf(false)

    val searchTFS = mutableStateOf(TextFieldValue())


    val titleTFS = mutableStateOf(TextFieldValue())
    val descriptionTFS = mutableStateOf(TextFieldValue())
    val isFaviouriteFS = mutableStateOf(false)
    val currentID: MutableState<Long?> = mutableStateOf(null)

    val listOfKeep = mutableStateListOf<KeepNotes>()
    val filterdOfKeep = mutableStateListOf<KeepNotes>()

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
                        s = it
                }

                s?.apply {
                    this.title.value = titleTFS.value.text
                    this.description.value = descriptionTFS.value.text
                }

                restTextFieldValue()
            }
        }
    }

    fun getKeepWithID(ID: Long): KeepNotes? {
        listOfKeep.forEach {
            if (it.ID == ID)
                return it ?: null
        }
        return null
    }

    fun deleteKeep() {
        if (currentID.value != null) {
            val temp = getKeepWithID(currentID.value!!)
            restTextFieldValue()
            isEditClicked.value = false
            listOfKeep.remove(temp)
        }
    }

    fun getFilteredList(search: String) {
        val list = listOfKeep.filter {
            it.title.value.contains(search) || it.description.value.contains(search)
        }

        if (search.isNotEmpty())
            filterdOfKeep.addAll(listOfKeep)
        else {
            filterdOfKeep.removeAll(filterdOfKeep)
            filterdOfKeep.addAll(list)
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
        currentID.value = null
        titleTFS.value = TextFieldValue()
        descriptionTFS.value = TextFieldValue()
        isFaviouriteFS.value = false
    }

}