package com.example.expensetracker.Views.Dashboard;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.Models.Expense;
import com.example.expensetracker.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ExpensesViewHolder> {
    private final List<Expense> expenseList;

    public DashboardAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    @NotNull
    @Override
    public ExpensesViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expenses_row_item, parent, false);
        return new ExpensesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ExpensesViewHolder holder, int position) {
        Expense expense = expenseList.get(position);

        holder.expensesItemName.setText(expense.getName());
        holder.expensesItemAmount.setText(String.valueOf(expense.getAmount()));
        String date = expense.getDate().toString();

        String toDisplay = date.substring(0, 3) + " " + date.substring(4, 7);
        holder.expensesItemDate.setText(toDisplay);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    private void setUpdateExpensesDialog(Expense expense, View view) {
        // Animate the clicked item
        int clickedPosition = expenseList.indexOf(expense);
        RecyclerView recyclerView = (RecyclerView) view.getParent();
        RecyclerView.ViewHolder clickedHolder = recyclerView.findViewHolderForAdapterPosition(clickedPosition);

        if (clickedHolder != null) {
            createItemAnimator(clickedHolder.itemView).start();
        }
    }

    private AnimatorSet createItemAnimator(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 0.95f, 1.0f),
                ObjectAnimator.ofFloat(view, "scaleY", 0.95f, 1.0f)
        );
        return animatorSet;
    }

    public static class ExpensesViewHolder extends RecyclerView.ViewHolder {
        private final TextView expensesItemName;
        private final TextView expensesItemAmount;
        private final TextView expensesItemDate;

        public ExpensesViewHolder(View view) {
            super(view);
            expensesItemName = view.findViewById(R.id.name_expense_row);
            expensesItemAmount = view.findViewById(R.id.amount_expense_row);
            expensesItemDate = view.findViewById(R.id.date_label_expense_row);
        }
    }
}
