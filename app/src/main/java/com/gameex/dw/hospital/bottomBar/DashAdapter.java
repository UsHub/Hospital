package com.gameex.dw.hospital.bottomBar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.gameex.dw.hospital.R;
import com.gameex.dw.hospital.dao.RegistrationDao;
import com.gameex.dw.hospital.daoImpl.RegistrationDaoImpl;
import com.gameex.dw.hospital.entity.Hosregister;
import com.gameex.dw.hospital.entity.Information;
import com.gameex.dw.hospital.updateAndRegnum.UpdateRegnumActivity;

import java.io.Serializable;
import java.util.List;

import static com.gameex.dw.hospital.bottomBar.BottomBarActivity.ints;

public class DashAdapter extends RecyclerView.Adapter<DashAdapter.DashHolder> {
    private List<Information> mList;
    private Context mContext;
    private RegistrationDao mDao = new RegistrationDaoImpl();

    public DashAdapter(List<Information> list, Context context) {
        mList = list;
        mContext = context;
    }

    public class DashHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView hosR_idText, d_nameText, hosR_stateText, d_keshiText, hosR_timeText;
        Button detailBtn, updateBtn, deleteBtn;

        public DashHolder(@NonNull View itemView) {
            super(itemView);
            hosR_idText = itemView.findViewById(R.id.hosR_id);
            d_nameText = itemView.findViewById(R.id.d_name);
            hosR_stateText = itemView.findViewById(R.id.hosR_state);
            d_keshiText = itemView.findViewById(R.id.d_keshi);
            hosR_timeText = itemView.findViewById(R.id.hosR_time);
            detailBtn = itemView.findViewById(R.id.detail_btn);
            detailBtn.setOnClickListener(this);
            updateBtn = itemView.findViewById(R.id.update_btn);
            updateBtn.setOnClickListener(this);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
            deleteBtn.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.detail_btn:
                    Intent intentDetail = new Intent(mContext, UpdateRegnumActivity.class);
                    intentDetail.putExtra("flag","detail");
                    intentDetail.putExtra("Detail", (Serializable) mList.get(getAdapterPosition()));
                    mContext.startActivity(intentDetail);
                    break;
                case R.id.update_btn:
                    Intent intentUpdate = new Intent(mContext, UpdateRegnumActivity.class);
                    intentUpdate.putExtra("flag","update");
                    intentUpdate.putExtra("Update", (Serializable) mList.get(getAdapterPosition()));
                    mContext.startActivity(intentUpdate);
                    break;
                case R.id.delete_btn:
                    int result = mDao.delete(mList.get(getAdapterPosition()).getHosR_id());
                    if (result == 1) {
                        mList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    } else {
                        Toast.makeText(mContext, "退号失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @NonNull
    @Override
    public DashHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_dash_item, viewGroup, false);
        return new DashHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashHolder dashHolder, int i) {
        Information information = mList.get(i);
        dashHolder.hosR_idText.setText(String.valueOf(information.getHosR_id()));
        dashHolder.d_nameText.setText(information.getD_name());
        dashHolder.hosR_stateText.setText(String.valueOf(information.getHosR_state()));
        dashHolder.d_keshiText.setText(information.getD_keshi());
        dashHolder.hosR_timeText.setText(information.getHosR_time());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
