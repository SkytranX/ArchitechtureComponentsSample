package com.example.android.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.todolist.database.TaskDatabase;
import com.example.android.todolist.database.TaskEntry;
import com.example.android.todolist.databinding.ActivityAddTaskBinding;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constants for priority
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;
    // Constant for logging
    private static final String TAG = AddTaskActivity.class.getSimpleName();
    // Fields for views
    EditText mEditText;
    RadioGroup mRadioGroup;
    Button mButton;


    private int mTaskId = DEFAULT_TASK_ID;

    ActivityAddTaskBinding activityAddTaskBinding;
    AddTaskViewModel addTaskViewModel;

    private TaskDatabase mDb;
//    private AppExecutors roomExecutor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_task);
        activityAddTaskBinding= DataBindingUtil.setContentView(this,R.layout.activity_add_task);
//        roomExecutor=AppExecutors.getInstance();

        addTaskViewModel=ViewModelProviders.of(this).get(AddTaskViewModel.class);
        activityAddTaskBinding.setViewModel(addTaskViewModel);
        //important to add setLifecycleOwner(this)
        activityAddTaskBinding.setLifecycleOwner(this);
        initViews();

        mDb = TaskDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            mButton.setText("Update");
            if (mTaskId == DEFAULT_TASK_ID) {
                // populate the UI
                mTaskId=intent.getIntExtra(EXTRA_TASK_ID,0);
               addTaskViewModel.getTaskByTaskId(this,mTaskId);

               addTaskViewModel.getTask().observe(this, new Observer<TaskEntry>() {
                   @Override
                   public void onChanged(TaskEntry taskEntry) {
                       addTaskViewModel.getTask().removeObserver(this);
                       populateUI(taskEntry);
                   }
               });
                //Possible Issue with observer entry
//                addTaskViewModel.getTaskEntryLiveData().observe(this, entry -> {
//                    addTaskViewModel.getTaskEntryLiveData().removeObserver(this::populateUI);
//                    populateUI(entry);
//                });
            }
        }
        addTaskViewModel.getDataSaving().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    finish();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
//        mEditText = findViewById(R.id.editTextTaskDescription);
        mRadioGroup = activityAddTaskBinding.radioGroup;

        mButton = activityAddTaskBinding.saveButton;
        mButton.setOnClickListener((view) -> {onSaveButtonClicked();});
        /*    mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });*/
    }

    /**
     * populateUI would be called to populate the UI when in update mode
     *
     * @param task the taskEntry to populate the UI
     */
    private void populateUI(TaskEntry task) {
//        mEditText.setText(task.getDescription());
        if (task == null){
            return;
        }
        setPriorityInViews(task.getPriority());
    }

    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onSaveButtonClicked() {
        addTaskViewModel.saveData(getPriorityFromViews());

        // Not yet implemented

    }


    /**
     * getPriority is called whenever the selected priority needs to be retrieved
     */
    public int getPriorityFromViews() {
        int priority = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radButton1:
                priority = PRIORITY_HIGH;
                break;
            case R.id.radButton2:
                priority = PRIORITY_MEDIUM;
                break;
            case R.id.radButton3:
                priority = PRIORITY_LOW;
        }
        return priority;
    }

    /**
     * setPriority is called when we receive a task from MainActivity
     *
     * @param priority the priority value
     */
    public void setPriorityInViews(int priority) {
        switch (priority) {
            case PRIORITY_HIGH:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
                break;
            case PRIORITY_MEDIUM:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
                break;
            case PRIORITY_LOW:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton3);
        }
    }
}
