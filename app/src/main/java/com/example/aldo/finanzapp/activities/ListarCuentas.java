package com.example.aldo.finanzapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.aldo.finanzapp.models.Bills;
import com.example.aldo.finanzapp.models.BillsDAO;
import com.example.aldo.finanzapp.models.MyClassAdapter;
import com.example.aldo.finanzapp.R;

import java.util.ArrayList;
import java.util.Iterator;

public class ListarCuentas extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener{
    private ArrayList<Bills> billsList;
    MyClassAdapter newAdapter;
    BillsDAO billsDAO;
    private Bills bill;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cuentas);
        android.support.v7.widget.Toolbar toolbar_list = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_list);
        setSupportActionBar(toolbar_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        billsDAO = new BillsDAO(this);
        billsDAO.open();
        billsList = billsDAO.getAllTasks();

        newAdapter = new MyClassAdapter(this, billsList);
        listView = (ListView) findViewById(R.id.myListView);
        listView.setAdapter(newAdapter);
        listView.setOnItemClickListener(this);
        newAdapter.notifyDataSetChanged();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListarCuentas.this, AddExpenseActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        for (Iterator<Bills> it = billsList.iterator(); it.hasNext();) {
            Bills itg = it.next();
            if (itg == adapterView.getItemAtPosition(position)) {
                BillsDAO billsDAO = new BillsDAO(this);

                Intent intent = new Intent(ListarCuentas.this, EditExpenseActivity.class);
                // Send expense data values to EditExpenseActivity
                intent.putExtra("ExpenseId", itg.getId());
                intent.putExtra("ExpenseTitle", itg.getBillName());
                intent.putExtra("ExpenseAmount", itg.getAmount());
                intent.putExtra("ExpenseDescription", itg.getDescription());

                startActivity(intent);
            }
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        return false;
    }
}
