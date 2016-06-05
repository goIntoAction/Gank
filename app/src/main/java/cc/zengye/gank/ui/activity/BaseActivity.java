package cc.zengye.gank.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import cc.zengye.gank.R;

public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private int mMenuRes = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initToolbar();
    }

    public abstract int getContentView();

    public void initToolbar () {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        if(mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    public void setTitle(String strTitle,boolean showHome){
        setTitle(strTitle);
        getSupportActionBar().setDisplayShowHomeEnabled(showHome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHome);
    }

    public void setMenuRes(int mMenuRes) {
        this.mMenuRes = mMenuRes;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mMenuRes > 0) {
            getMenuInflater().inflate(mMenuRes,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
