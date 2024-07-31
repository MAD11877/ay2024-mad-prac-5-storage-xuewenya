package sg.edu.np.mad.madpractical5;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder>{
    ArrayList<User> data;
    Context context;

    public UserAdapter(ArrayList<User> input, Context context){
        this.context = context;
        this.data = input;
    }


    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_activity_list, parent, false);
        return new UserViewHolder(item);
    }

    public void onBindViewHolder(UserViewHolder holder, int position){
        User user = data.get(position);

        if (String.valueOf(user.getName()).endsWith("7")) {
            holder.bigImage.setVisibility(View.VISIBLE);
        }

        holder.name.setText(user.getName());
        holder.description.setText(user.getDescription());

        if (holder.bigImage != null){
            holder.bigImage.setImageResource(R.drawable.ic_launcher_foreground);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                builder.setTitle("Profile")
                        .setMessage(user.getName())
                        .setCancelable(true)
                        .setPositiveButton("View", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent goToMainActivity  = new Intent(context, MainActivity.class);
                                goToMainActivity .putExtra("name", user.getName());
                                goToMainActivity .putExtra("description", user.getDescription());
                                goToMainActivity .putExtra("followed", user.getFollowed());
                                goToMainActivity .putExtra("id", user.getId());
                                v.getContext().startActivity(goToMainActivity);
                            }
                        })
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }


        });
    }

    public int getItemCount() {
        return data.size();
    }
}