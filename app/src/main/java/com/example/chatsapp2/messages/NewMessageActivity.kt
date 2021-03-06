package com.example.chatsapp2.messages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.chatsapp2.R
import com.example.chatsapp2.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class NewMessageActivity : AppCompatActivity() {

  companion object {
    val USER_KEY = "USER_KEY"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_message)

    supportActionBar?.title = "Select User"

//    val adapter = GroupAdapter<ViewHolder>()
//
//    adapter.add(UserItem())
//    adapter.add(UserItem())
//    adapter.add(UserItem())
//
//    recyclerview_newmessage.adapter = adapter

    fetchUsers()
  }

  private fun fetchUsers() {
    val uid = FirebaseAuth.getInstance().uid
    val ref = FirebaseDatabase.getInstance().getReference("/friends/$uid")
    ref.addListenerForSingleValueEvent(object: ValueEventListener {

      override fun onDataChange(p0: DataSnapshot) {
        val adapter = GroupAdapter<GroupieViewHolder>()

        p0.children.`forEach` {
          Log.d("NewMessage", it.toString())
          val user = it.getValue(User::class.java)
          if (user != null) {
            adapter.add(UserItem(user))
          }
        }
        adapter.setOnItemClickListener { item, view ->

          val userItem = item as UserItem

          val intent = Intent(view.context, ChatLogActivity::class.java)
//          intent.putExtra(USER_KEY,  userItem.user.username)
          intent.putExtra(USER_KEY, userItem.user)
          startActivity(intent)

          finish()

        }
        recyclerview_newmessage.adapter = adapter
      }

      override fun onCancelled(p0: DatabaseError) {

      }
    })
  }
}

class UserItem(val user: User): Item<GroupieViewHolder>() {
  override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    viewHolder.itemView.username_textview_new_message.text = user.username

    Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageview_new_message)
  }

  override fun getLayout(): Int {
    return R.layout.user_row_new_message
  }
}

// this is super tedious

//class CustomAdapter: RecyclerView.Adapter<ViewHolder> {
//  override fun onBindViewHolder(p0:, p1: Int) {
//    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//  }
//}
