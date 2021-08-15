package com.harshit.todoapp.fragment.updatefragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.harshit.todoapp.R
import com.harshit.todoapp.data.SharedViewModel
import com.harshit.todoapp.data.TodoData
import com.harshit.todoapp.data.TodoViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel:SharedViewModel by viewModels()
    private val todoViewModel:TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        setHasOptionsMenu(true)
        view.current_title_et.setText(args.currentItem.title)
        view.current_description_et.setText(args.currentItem.description)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menuSave){
            updateItem()
        }else if(item.itemId == R.id.menuDelete){
            deleteItem()
        }
        return  super.onOptionsItemSelected(item)
    }

    private fun deleteItem() {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes"){_,_->
                todoViewModel.deleteItem(args.currentItem)
                Toast.makeText(
                    requireContext(),"Successfully Removed",Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }
            builder.setNegativeButton("No"){_,_->
            }
            builder.setTitle("Delete ${args.currentItem.title}?")
            builder.setMessage("Delete will remove permanently")
            builder.create().show()
    }

    private fun updateItem() {
        val title = current_title_et.text.toString()
        val description = current_description_et.text.toString()
        val priorities = current_priorities_spinner.selectedItem.toString()

        val validation = sharedViewModel.verifyDataFromUser(title,description)

        if (validation) {
            val updateItem = TodoData(
                args.currentItem.id,
                title,
                sharedViewModel.convertPriority(priorities),
                description
            )
            todoViewModel.updateData(updateItem)
            Toast.makeText(requireContext(),"Successfully updated!!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Oops",Toast.LENGTH_SHORT).show()

        }

    }
}