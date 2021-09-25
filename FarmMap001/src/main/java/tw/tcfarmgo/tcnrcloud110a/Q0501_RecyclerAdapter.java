package tw.tcfarmgo.tcnrcloud110a;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Q0501_RecyclerAdapter extends RecyclerView.Adapter<Q0501_RecyclerAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<Q0501_Post> mData;
    //    -------------------------------------------------------------------
    private OnItemClickListener mOnItemClickListener = null;
    //--------------------------------------------
    public Q0501_RecyclerAdapter(Context context, ArrayList<Q0501_Post> data) {
        this.mContext = context;
        this.mData = data;
    }
    //    -------------------------------------------------------------------
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //-------------------------------------------------------------------
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.q0501_cell_post, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.img = (ImageView) view.findViewById(R.id.q0501_img);//correspond to layout
        holder.Name = (TextView) view.findViewById(R.id.q0501_t201);
        holder.Content = (TextView) view.findViewById(R.id.Content);
        holder.Add = (TextView) view.findViewById(R.id.q0501_t205);
        holder.Zipcode = (TextView) view.findViewById(R.id.q0501_t204);
        holder.TEL = (TextView) view.findViewById(R.id.q0501_t202);
//        holder.Px = (TextView) view.findViewById(R.id.Px);
//        holder.Py = (TextView) view.findViewById(R.id.Py);
        //----------------------------------------------------
        //將創建的View註冊點擊事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Q0501_Post post = mData.get(position);
        holder.Name.setText(post.FarmNm_CH);
        holder.TEL.setText("電話:"+post.TEL);
        holder.Add.setText(post.Address_CH);
        holder.Content.setText(post.Feature_CH);

        if (post.PCode.length()>0){ //如果有郵遞區號
            holder.Zipcode.setText("["+post.PCode+"]");
        }else{//沒有有郵遞區號
            holder.Zipcode.setText("[000]");
        }

        Glide.with(mContext)
                .load(post.Photo)
                .override(100, 75) //圖片重新調整大小
                .transition(withCrossFade())
                .error(
                        Glide.with(mContext)
                                .load("https://tcnr2021a11.000webhostapp.com/post_img/nopic1.jpg")) //無提供照片時
                .into(holder.img);

        //將position保存在itemView的Tag中，以便點擊時進行獲取
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意這裡使用getTag方法獲取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    //======= sub class   ==================
    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView Name;
        public TextView Add;
        public TextView Content;
        public TextView Zipcode;
        public TextView TEL;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
//-----------------------------------------------
}
