package com.marianunez.todoapp

import android.os.Parcel
import android.os.Parcelable

// we need to add members and add parcelable implementation
class TaskList(val title: String, val taskList: ArrayList<String> = ArrayList()): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    )

    /** Why we put = 0
     * porque esta función se utiliza para lo que se llaman file descriptors
     * y como aquí no estamos manejando file descriptors, ponemos 0
     * si estuviesemos manejandolos pondríamos 1
     */
    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeStringList(taskList)
    }

    /** este companion object se crea a la misma vez que el constructor
     * y está implementando la Parcelable Creator interface
     * no hay que tocar nada de él
    */
    companion object CREATOR : Parcelable.Creator<TaskList> {
        override fun createFromParcel(parcel: Parcel): TaskList = TaskList(parcel)
        override fun newArray(size: Int): Array<TaskList?> = arrayOfNulls(size)
    }
}

// una vez tenemos implementado nuestro Parcelable,
// el error del putExtra del intent de nuestra MainActivity desaparecerá solo
