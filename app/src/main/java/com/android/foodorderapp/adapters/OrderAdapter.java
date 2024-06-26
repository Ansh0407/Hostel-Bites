package com.android.foodorderapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.foodorderapp.Order;
import com.android.foodorderapp.R;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orders = new ArrayList<>();

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView orderIdTextView;
        private TextView customerNameTextView;
        private TextView deliveryCityTextView;
        private TextView restaurantNameTextView;
        private TextView itemsTextView;
        private TextView totalAmountTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.orderIdTextView);
            customerNameTextView = itemView.findViewById(R.id.customerNameTextView);
            deliveryCityTextView = itemView.findViewById(R.id.deliveryCityTextView);
            restaurantNameTextView = itemView.findViewById(R.id.restaurantNameTextView);
            itemsTextView = itemView.findViewById(R.id.itemsTextView);
            totalAmountTextView = itemView.findViewById(R.id.totalAmountTextView);
        }

        public void bind(Order order) {
            orderIdTextView.setText(String.valueOf(order.getId()));
            customerNameTextView.setText(order.getCustomerName());
            deliveryCityTextView.setText(order.getDeliveryCity());
            restaurantNameTextView.setText(order.getRestaurantName());
            itemsTextView.setText(order.getItems());
            displayItems(order.getItems());
            totalAmountTextView.setText(String.valueOf(order.getTotalAmount()));
        }
        private void displayItems(String itemsJson) {
            try {
                JSONArray jsonArray = new JSONArray(itemsJson);
                StringBuilder itemsText = new StringBuilder();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject itemObject = jsonArray.getJSONObject(i);
                    String itemName = itemObject.getString("name");
                    int itemQuantity = itemObject.getInt("quantity");
                    itemsText.append(itemName).append(" x").append(itemQuantity).append("\n");
                }
                itemsTextView.setText(itemsText.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

