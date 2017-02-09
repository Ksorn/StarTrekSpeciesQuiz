package com.example.android.startrekspeciesquiz;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean answeredAll;
    private boolean editTextCorrect;
    private int countCorrectAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //**Required for using custom Font**
        // Set the font's path
        String fontPath = "fonts/TNG_Title.ttf";
        // Get the customFont TextView
        TextView textView = (TextView) findViewById(R.id.quiz_title);
        // Load the font as a TypeFace object
        Typeface typeFace = Typeface.createFromAsset(getAssets(), fontPath);
        // Set the extra fancy font in the customFont TextView
        textView.setTypeface(typeFace);

    }

    //Activity of clicked button
    public void clickedButton(View view) {

        answeredAll = true;
        editTextCorrect = false;
        countCorrectAnswers = 0;

        //RadioGroup helpers
        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radio_group_1);
        RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.radio_group_2);
        RadioGroup radioGroup4 = (RadioGroup) findViewById(R.id.radio_group_4);
        RadioGroup radioGroup5 = (RadioGroup) findViewById(R.id.radio_group_5);
        RadioGroup radioGroup6 = (RadioGroup) findViewById(R.id.radio_group_6);
        RadioGroup radioGroup8 = (RadioGroup) findViewById(R.id.radio_group_8);
        RadioGroup radioGroup9 = (RadioGroup) findViewById(R.id.radio_group_9);
        RadioGroup radioGroup10 = (RadioGroup) findViewById(R.id.radio_group_10);

        //Calls method ifAnswered() to check if all are radioGroups answered
        ifAnswered(radioGroup1);
        ifAnswered(radioGroup2);
        ifAnswered(radioGroup4);
        ifAnswered(radioGroup5);
        ifAnswered(radioGroup6);
        ifAnswered(radioGroup8);
        ifAnswered(radioGroup9);
        ifAnswered(radioGroup10);

        //Calls method checkIfEditTextHasAnswer() to check if EditText is answered and if it is correct
        checkIfEditTextHasAnswer();

        //Calls method checkCheckBoxes() to check if any checkbox is selected
        checkCheckBoxes();


        //Show result
        if (answeredAll) {

            //Locks current device's orientation
            int value = getResources().getConfiguration().orientation;
            if (value == Configuration.ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }

            //Hides button
            Button button = (Button) findViewById(R.id.button);
            button.setVisibility(View.GONE);

            //Calls method setRadioButtonBackgroundColor() to color the backgrounds of selected radio buttons and corect answers
            setRadioButtonBackgroundColor(radioGroup1, findViewById(R.id.radio_q1_4).getId());
            setRadioButtonBackgroundColor(radioGroup2, findViewById(R.id.radio_q2_2).getId());
            setRadioButtonBackgroundColor(radioGroup4, findViewById(R.id.radio_q4_3).getId());
            setRadioButtonBackgroundColor(radioGroup5, findViewById(R.id.radio_q5_4).getId());
            setRadioButtonBackgroundColor(radioGroup6, findViewById(R.id.radio_q6_3).getId());
            setRadioButtonBackgroundColor(radioGroup8, findViewById(R.id.radio_q8_3).getId());
            setRadioButtonBackgroundColor(radioGroup9, findViewById(R.id.radio_q9_1).getId());
            setRadioButtonBackgroundColor(radioGroup10, findViewById(R.id.radio_q10_2).getId());

            //Calls method colorEditTextBackground() to color background of EditText to green/red depending on answer
            colorEditTextBackground();

            //Calls method colorCheckBoxBackground() to color background of CheckBoxes
            colorCheckBoxBackground();

            //Calls method checkCorrectCheckBoxes()
            checkCorrectCheckBoxes();

            //Toast - quiz completed
            Context context = getApplicationContext();
            String text = getString(R.string.toast_part_one) + countCorrectAnswers + getString(R.string.toast_part_two);
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            //Unhides TextView result
            TextView textViewResult = (TextView) findViewById(R.id.text_result);
            textViewResult.setVisibility(View.VISIBLE);
            String textResult = countCorrectAnswers + getString(R.string.text_final);
            textViewResult.setText(textResult);

            //Unhides GifImageView result
            GifImageView gifImageView = (GifImageView) findViewById(R.id.GifImageView);
            gifImageView.setGifImageResource(getIdOfGif());
            gifImageView.setVisibility(View.VISIBLE);

        } else {
            //Toast - not answered all questions
            Context context = getApplicationContext();
            String text = getString(R.string.toast_not_answered);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }


    }

    /**
     * Checks if selected CheckBoxes are correct
     */
    private void checkCorrectCheckBoxes() {
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkbox_1);
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkbox_2);
        CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkbox_3);
        CheckBox checkBox4 = (CheckBox) findViewById(R.id.checkbox_4);
        if (checkBox1.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked() && checkBox4.isChecked()) {
            countCorrectAnswers++;
        }
    }

    /**
     * Checks if radio group has selected radio button
     *
     * @param radioGroup - current radio group in checking if selected any radio button
     */
    private void ifAnswered(RadioGroup radioGroup) {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            answeredAll = false;
        }
    }

    /**
     * Checks if editText has corect answer
     */
    private void checkIfEditTextHasAnswer() {
        EditText editText = (EditText) findViewById(R.id.edit_text_answer);
        if (editText.getText().toString().equals("")) {
            answeredAll = false;
        } else if (editText.getText().toString().toUpperCase().equals(getString(R.string.text_vulcan))) {
            editTextCorrect = true;
            countCorrectAnswers++;
        }
    }

    /**
     * Checks if any checkbox is selected
     */
    private void checkCheckBoxes() {
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkbox_1);
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkbox_2);
        CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkbox_3);
        CheckBox checkBox4 = (CheckBox) findViewById(R.id.checkbox_4);
        if (!checkBox1.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked() && !checkBox4.isChecked()) {
            answeredAll = false;
        }
    }

    /**
     * Sets background color for selected radio button and corect answer
     *
     * @param radioGroup       - current radio group of selected radio button
     * @param radioBtnCorectId - id of correct radio button
     */
    private void setRadioButtonBackgroundColor(RadioGroup radioGroup, int radioBtnCorectId) {
        if (radioGroup.getCheckedRadioButtonId() != radioBtnCorectId) {
            RadioButton radioButtonChecked = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            radioButtonChecked.setBackgroundColor(Color.RED);
        } else {
            countCorrectAnswers++;
        }
        findViewById(radioBtnCorectId).setBackgroundColor(Color.GREEN);
    }

    /**
     * Colors background of EditText to green/red depending on answer
     */
    private void colorEditTextBackground() {
        EditText editText = (EditText) findViewById(R.id.edit_text_answer);
        if (editTextCorrect) {
            editText.setBackgroundColor(Color.GREEN);
        } else {
            editText.setBackgroundColor(Color.RED);
        }
    }

    /**
     * Colors background of CheckBoxes to green/red depending on answers
     */
    private void colorCheckBoxBackground() {
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkbox_1);
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkbox_2);
        CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkbox_3);
        CheckBox checkBox4 = (CheckBox) findViewById(R.id.checkbox_4);

        if (checkBox1.isChecked()) {
            checkBox1.setBackgroundColor(Color.GREEN);
        }
        if (checkBox2.isChecked()) {
            checkBox2.setBackgroundColor(Color.RED);
        }
        if (checkBox3.isChecked()) {
            checkBox3.setBackgroundColor(Color.RED);
        }
        if (checkBox4.isChecked()) {
            checkBox4.setBackgroundColor(Color.GREEN);
        }
    }

    /**
     * Gets id of the drawable resource gif depending on number of correct answers
     *
     * @return id of the drawable resource gif
     */
    private int getIdOfGif() {

        int idOfGif = 0;

        if (countCorrectAnswers <= 3) {
            idOfGif = R.drawable.kirk_pain;
        } else if (countCorrectAnswers <= 6) {
            idOfGif = R.drawable.krik_disapointed;
        } else if (countCorrectAnswers <= 9) {
            idOfGif = R.drawable.kirk_statisfied;
        } else if (countCorrectAnswers == 10) {
            idOfGif = R.drawable.kirk_impressed;
        }

        return idOfGif;
    }

    /**
     * Fragment's state saved before clicking button.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //EditText's state saved
        final EditText editText = (EditText) findViewById(R.id.edit_text_answer);
        outState.putString("edit_text", editText.getText().toString());

        //CheckBoxes's states saved
        final CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkbox_1);
        outState.putBoolean("checkBox1", checkBox1.isChecked());

        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkbox_2);
        outState.putBoolean("checkBox2", checkBox2.isChecked());

        final CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkbox_3);
        outState.putBoolean("checkBox3", checkBox3.isChecked());

        final CheckBox checkBox4 = (CheckBox) findViewById(R.id.checkbox_4);
        outState.putBoolean("checkBox4", checkBox4.isChecked());

        //Radio Groups 1,2,4,5,6,8,9,10 - states saved
        final RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radio_group_1);
        outState.putInt("radioGroup1", radioGroup1.getCheckedRadioButtonId());

        final RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.radio_group_2);
        outState.putInt("radioGroup2", radioGroup2.getCheckedRadioButtonId());

        final RadioGroup radioGroup4 = (RadioGroup) findViewById(R.id.radio_group_4);
        outState.putInt("radioGroup4", radioGroup4.getCheckedRadioButtonId());

        final RadioGroup radioGroup5 = (RadioGroup) findViewById(R.id.radio_group_5);
        outState.putInt("radioGroup5", radioGroup5.getCheckedRadioButtonId());

        final RadioGroup radioGroup6 = (RadioGroup) findViewById(R.id.radio_group_6);
        outState.putInt("radioGroup6", radioGroup6.getCheckedRadioButtonId());

        final RadioGroup radioGroup8 = (RadioGroup) findViewById(R.id.radio_group_8);
        outState.putInt("radioGroup8", radioGroup8.getCheckedRadioButtonId());

        final RadioGroup radioGroup9 = (RadioGroup) findViewById(R.id.radio_group_9);
        outState.putInt("radioGroup9", radioGroup9.getCheckedRadioButtonId());

        final RadioGroup radioGroup10 = (RadioGroup) findViewById(R.id.radio_group_10);
        outState.putInt("radioGroup10", radioGroup10.getCheckedRadioButtonId());
    }

    /**
     * Fragment's state restore before clicking button.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);

        //EditText's state restore
        final EditText editText = (EditText) findViewById(R.id.edit_text_answer);
        editText.setText(savedState.getString("edit_text"));

        //CheckBoxes's states restore
        final CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkbox_1);
        checkBox1.setChecked(savedState.getBoolean("checkBox1"));

        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkbox_2);
        checkBox2.setChecked(savedState.getBoolean("checkBox2"));

        final CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkbox_3);
        checkBox3.setChecked(savedState.getBoolean("checkBox3"));

        final CheckBox checkBox4 = (CheckBox) findViewById(R.id.checkbox_4);
        checkBox4.setChecked(savedState.getBoolean("checkBox4"));

        //Radio Groups 1,2,4,5,6,8,9,10 - states restore
        final RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.radio_group_1);
        radioGroup1.check(savedState.getInt("radioGroup1"));

        final RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.radio_group_2);
        radioGroup2.check(savedState.getInt("radioGroup2"));

        final RadioGroup radioGroup4 = (RadioGroup) findViewById(R.id.radio_group_4);
        radioGroup4.check(savedState.getInt("radioGroup4"));

        final RadioGroup radioGroup5 = (RadioGroup) findViewById(R.id.radio_group_5);
        radioGroup5.check(savedState.getInt("radioGroup5"));

        final RadioGroup radioGroup6 = (RadioGroup) findViewById(R.id.radio_group_6);
        radioGroup6.check(savedState.getInt("radioGroup6"));

        final RadioGroup radioGroup8 = (RadioGroup) findViewById(R.id.radio_group_8);
        radioGroup8.check(savedState.getInt("radioGroup8"));

        final RadioGroup radioGroup9 = (RadioGroup) findViewById(R.id.radio_group_9);
        radioGroup9.check(savedState.getInt("radioGroup9"));

        final RadioGroup radioGroup10 = (RadioGroup) findViewById(R.id.radio_group_10);
        radioGroup10.check(savedState.getInt("radioGroup10"));
    }

}
