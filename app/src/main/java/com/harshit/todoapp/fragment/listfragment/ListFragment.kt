package com.harshit.todoapp.fragment.listfragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.harshit.todoapp.R
import com.harshit.todoapp.data.TodoViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private val todoViewModel:TodoViewModel by viewModels()

    private val adapter:ListAdapter by lazy {
        ListAdapter()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView =  view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        todoViewModel.getAllData.observe(viewLifecycleOwner, Observer {data->
            adapter.setData(data)
        })

        view.floatingActionButton.setOnClickListener {
            //this helps to move to other fragment
            findNavController().navigate(R.id.action_listFragment_to_addFragment)

        }

        //populating the option menu
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delelteAll){
            deleteEverything()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteEverything() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            todoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),"Successfully Removed", Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No"){_,_->
        }
        builder.setTitle("Delete Everything?")
        builder.setMessage("Delete will remove permanently")
        builder.create().show()
    }

}