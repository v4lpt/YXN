package v4lpt.vpt.f006.yxn;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private View mainLayout;
    private View choiceLayout;
    private Button choiceButton;
    private TextView resultText;
    private Button backButton;
    private boolean canRevealBackButton = false;
    private Handler handler = new Handler();
    private Runnable enableBackButtonReveal;
    private Button infoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mainLayout = findViewById(android.R.id.content);
        choiceLayout = getLayoutInflater().inflate(R.layout.layout_choice, null);
        choiceButton = findViewById(R.id.choiceButton);
        resultText = choiceLayout.findViewById(R.id.resultText);
        backButton = choiceLayout.findViewById(R.id.backButton);
        infoButton = findViewById(R.id.infoButton);
        infoButton.setOnClickListener(v ->  openInfoFragment());
        choiceButton.setOnClickListener(v -> showChoice());

        choiceLayout.setOnClickListener(v -> {
            if (canRevealBackButton) {
                showBackButton();
            }
        });

        backButton.setOnClickListener(v -> showMainLayout());

        enableBackButtonReveal = () -> canRevealBackButton = true;
    }
    private void openInfoFragment() {
        InfoFragment infoFragment = new InfoFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, infoFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void closeInfoFragment() {
        getSupportFragmentManager().popBackStack();
    }
    private void showChoice() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(choiceLayout);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        Random random = new Random();
        boolean isYes = random.nextBoolean();

        if (isYes) {
            resultText.setText("YES.");
            resultText.setTextColor(getResources().getColor(android.R.color.white));
        } else {
            resultText.setText("NO.");
            resultText.setTextColor(getResources().getColor(android.R.color.white));
        }

        canRevealBackButton = false;
        handler.removeCallbacks(enableBackButtonReveal);
        handler.postDelayed(enableBackButtonReveal, 5000);
    }

    private void showBackButton() {
        backButton.setVisibility(View.VISIBLE);
        handler.postDelayed(() -> backButton.setVisibility(View.GONE), 2727);
    }

    private void showMainLayout() {
        setContentView(R.layout.main_layout);
        choiceButton = findViewById(R.id.choiceButton);
        choiceButton.setOnClickListener(v -> showChoice());
        infoButton = findViewById(R.id.infoButton);
        infoButton.setOnClickListener(v ->  openInfoFragment());
        canRevealBackButton = false;
        handler.removeCallbacks(enableBackButtonReveal);
        backButton.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(enableBackButtonReveal);
    }
}