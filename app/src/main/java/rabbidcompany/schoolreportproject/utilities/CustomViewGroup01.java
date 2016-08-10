package rabbidcompany.schoolreportproject.utilities;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import rabbidcompany.schoolreportproject.R;

/**
 * Created by noneemotion on 8/8/2559.
 */
public class CustomViewGroup01 extends FrameLayout {

    TextView textView;

    public CustomViewGroup01(Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public CustomViewGroup01(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
    }

    public CustomViewGroup01(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
    }

    @TargetApi(21)
    public CustomViewGroup01(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
    }

    private void initInflate() {
        inflate(getContext(), R.layout.custom_view_group_01, this);
    }

    private void initInstance() {
        textView = (TextView) findViewById(R.id.TextViewInCustomID01);
    }

    public void setTextView(String text) {
        textView.setText(text);
    }

    public TextView getTextView() {
        return textView;
    }
}
