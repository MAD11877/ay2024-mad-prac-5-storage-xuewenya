package sg.edu.np.mad.madpractical5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnFollow;
    private boolean isFollowing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        DatabaseHandler dbHandler = new DatabaseHandler(this, null, null, 1);

        //Get the TextViews and Button from the layout
        TextView tvName = findViewById(R.id.tvName);
        TextView tvDescription = findViewById(R.id.tvDescription);
        btnFollow = findViewById(R.id.btnFollow);

        //Reading the random number
        Intent receivingEnd = getIntent();
        String name = receivingEnd.getStringExtra("name");
        String description = receivingEnd.getStringExtra("description");
        String followed = receivingEnd.getStringExtra("followed");
        String id = receivingEnd.getStringExtra("id");

        User user = dbHandler.getUser(name);

        //Set the TextViews with the User's name, description and default button message
        tvName.setText(name);
        tvDescription.setText(description);

        if (followed == "true") {
            btnFollow.setText("Unfollow");
            //Boolean to check if user is following
            isFollowing = true;
        }
        else{
            btnFollow.setText("Follow");
            //Boolean to check if user is following
            isFollowing = false;
        }

        //OnClick Event handler
        btnFollow.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             isFollowing = !isFollowing;
                                             user.setFollowed(isFollowing);
                                             dbHandler.updateUser(user);
                                             if(isFollowing){
                                                 btnFollow.setText("Follow");
                                                 Toast.makeText(getApplicationContext(), "Unfollowed", Toast.LENGTH_SHORT).show();
                                             }
                                             else{
                                                 btnFollow.setText("Unfollow");
                                                 Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     }
        );
    }

}