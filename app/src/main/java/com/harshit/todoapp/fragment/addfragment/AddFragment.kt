package com.harshit.todoapp.fragment.addfragment

import android.accounts.AuthenticatorDescription
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.harshit.todoapp.R
import com.harshit.todoapp.data.Priorities
import com.harshit.todoapp.data.SharedViewModel
import com.harshit.todoapp.data.TodoData
import com.harshit.todoapp.data.TodoViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

private val todoViewModel:TodoViewModel by viewModels()
private val sharedViewModel:SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)
        //set menu
        setHasOptionsMenu(true)



        return  view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menuAdd){
            insertDataTodo()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataTodo() {
        val title = title_et.text.toString()
        val priorities = priorities_spinner.selectedItem.toString()
        val description = description_et.text.toString()
        if(sharedViewModel.verifyDataFromUser(title,description)){
            //Inseting Into Database
            val data = TodoData(
                0,
                title,
                sharedViewModel.convertPriority(priorities),
                description

            )
            todoViewModel.insertData(data)
            Toast.makeText(requireContext(),"Successfully added.",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        else{
            Toast.makeText(requireContext(),"Oops",Toast.LENGTH_SHORT).show()
        }
    }

}