package com.example.unnatiandroidpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    protected void AskAndFinishActivity(String message, Activity activity, final boolean fromTop) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.alert_dialogue_view, null);

        TextView title_tv = view.findViewById(R.id.alert_title);
        TextView msg_tv = view.findViewById(R.id.alert_message);
        Button btn_alrt_cancel = view.findViewById(R.id.alert_btn_cancel);
        Button btn_alrt_submit = view.findViewById(R.id.alert_btn_submit);

        btn_alrt_submit.setVisibility(View.VISIBLE);
        btn_alrt_submit.setText(activity.getString(R.string.yes_value));
        btn_alrt_cancel.setText(activity.getString(R.string.no_value));

        final Dialog dialog = new Dialog(activity, R.style.MaterialDialogSheet);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        msg_tv.setText(message);


        btn_alrt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
                if (fromTop)
                    overridePendingTransition(R.anim.enter_from_top, R.anim.exit_in_bottom);
                else
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_in_left);
            }
        });

        btn_alrt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}