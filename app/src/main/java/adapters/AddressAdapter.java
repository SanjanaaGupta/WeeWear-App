package adapters;

import android.content.Context;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.example.weewear.R;

import model.AddressModel;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    Context context;
    List<AddressModel> addressModelList;
    SelectAddress selectAddress;
    private RadioButton selectedRadioBtn;
    public AddressAdapter (Context context,List<AddressModel>addressModelList,SelectAddress selectAddress) {
       this.context=context;
       this.addressModelList=addressModelList;
       this.selectAddress=selectAddress;


    }
        public Context getContext () {
            return context;
        }

        public void setContext (Context context){
            this.context = context;
        }

        public List<AddressModel> getAddressModelList () {
            return addressModelList;
        }

        public void setAddressModelList (List < AddressModel > addressModelList) {
            this.addressModelList = addressModelList;
        }

        public SelectAddress getSelectAddress () {
            return selectAddress;
        }

        public void setSelectAddress (SelectAddress selectAddress){
            this.selectAddress = selectAddress;
        }


    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {
        holder.address.setText(addressModelList.get(position).getUserAddress());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(AddressModel address:addressModelList){
                    address.setSelected(false);
                }
                addressModelList.get(position).setSelected(true);
                if(selectedRadioBtn!=null){
                    selectedRadioBtn.setChecked(false);
                }
                selectedRadioBtn =(RadioButton) v;
                selectedRadioBtn.setChecked(true);
                selectAddress.setAddress(addressModelList.get(position).getUserAddress());
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address_add);
             radioButton = itemView.findViewById(R.id.select_address);

        }
    }
    public interface SelectAddress{
        void setAddress(String address);
    }
}
