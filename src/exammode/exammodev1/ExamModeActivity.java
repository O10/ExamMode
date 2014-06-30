
package exammode.exammodev1;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Aleksander Wojcik aleksander.k.wojcik@gmail.com
 * @since 30 cze 2014 20:07:07
 */
public class ExamModeActivity extends Activity {

    public ExamModeActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emodelayout);
    }

}
