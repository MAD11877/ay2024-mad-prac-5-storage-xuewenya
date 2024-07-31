package sg.edu.np.mad.madpractical5;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UserViewHolder extends RecyclerView.ViewHolder {

    ImageView smallImage, bigImage;

    TextView name;

    TextView description;

    public UserViewHolder(View itemView){
        super(itemView);
        //location of the xml (the ids)
        bigImage = itemView.findViewById(R.id.profileLarge);
        smallImage = itemView.findViewById(R.id.profileSmall);
        name = itemView.findViewById(R.id.name);
        description = itemView.findViewById(R.id.description);
    }

}
