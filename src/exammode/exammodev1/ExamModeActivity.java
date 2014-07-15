
package exammode.exammodev1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * @author Aleksander Wojcik aleksander.k.wojcik@gmail.com
 * @since 30 cze 2014 20:07:07
 */
public class ExamModeActivity extends Activity {

    private static final String INTENT_ACTION = "Action.Request.Exam.Mode";

    static final String PREFS_NAME = "EModePreferences";

    CheckBox extScrTimCheck, disableSoundCheck;

    EditText timeoutET;

    Button startEMB, stopEMB;

    public ExamModeActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.emodelayout);
        disableSoundCheck = (CheckBox)findViewById(R.id.disableSoundCheck);
        extScrTimCheck = (CheckBox)findViewById(R.id.extScrTimCheck);
        timeoutET = (EditText)findViewById(R.id.timeoutET);
        startEMB = (Button)findViewById(R.id.startEMB);
        stopEMB = (Button)findViewById(R.id.stopEMB);

        extScrTimCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    timeoutET.setEnabled(true);
                    timeoutET.setText("10");
                } else {
                    timeoutET.setEnabled(false);
                    timeoutET.setText("");
                }

            }
        });

        startEMB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("disableSound", disableSoundCheck.isChecked());
                editor.putBoolean("extTimeout", extScrTimCheck.isChecked());
                editor.putString("timeout", timeoutET.getText().toString());
                editor.commit();

                Intent intent = new Intent(INTENT_ACTION);
                intent.putExtra("start", true);
                sendBroadcast(intent);
            }
        });

        stopEMB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(INTENT_ACTION);
                intent.putExtra("start", false);
                sendBroadcast(intent);

            }
        });

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        disableSoundCheck.setChecked(settings.getBoolean("disableSound", false));
        extScrTimCheck.setChecked(settings.getBoolean("extTimeout", false));
        timeoutET.setText(settings.getString("timeout", ""));

    }
}
