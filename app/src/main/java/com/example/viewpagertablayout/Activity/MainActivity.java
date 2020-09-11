package com.example.viewpagertablayout.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.viewpagertablayout.Adapter.ViewPagerAdapter;
import com.example.viewpagertablayout.Fragment.ActiveFragment;
import com.example.viewpagertablayout.Fragment.CallsFragment;
import com.example.viewpagertablayout.Fragment.ChatFragment;
import com.example.viewpagertablayout.Fragment.FriendsFragment;
import com.example.viewpagertablayout.Fragment.NotificationFragment;
import com.example.viewpagertablayout.Fragment.StoryFragment;
import com.example.viewpagertablayout.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final int CALL_PERMISSION = 1;
    private static final int CHAT_PERMISSION = 1;
    private static final int LOCATION_PERMISSION = 1;
    private static final int STORAGE_PERMISSION = 1;
    private static final int CONTACTS_PERMISSION = 1;
    private static final int REQUEST_CAMERA = 1;

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private Button buttonOne, buttonTwo;
    private int[] tabIcons = {
            R.drawable.chat,
            R.drawable.active,
            R.drawable.notifications,
            R.drawable.story,
            R.drawable.calls,
            R.drawable.friends};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonOne = findViewById(R.id.btnOne);
        buttonTwo = findViewById(R.id.btnTwo);

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogRed();
            }
        });


        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tablayoutOne);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ChatFragment(), "Chat");
        viewPagerAdapter.addFragment(new ActiveFragment(), "Active");
        viewPagerAdapter.addFragment(new NotificationFragment(), "notifaction");
        viewPagerAdapter.addFragment(new StoryFragment(), "Story");
        viewPagerAdapter.addFragment(new CallsFragment(), "Calls");
        viewPagerAdapter.addFragment(new FriendsFragment(), "Friends");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        tabLayout.getTabAt(5).setIcon(tabIcons[5]);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                checkPermission(Manifest.permission.CAMERA, REQUEST_CAMERA);

                switch (tab.getPosition()) {
                    case 0:
                        checkPermission(Manifest.permission.READ_SMS, CHAT_PERMISSION);
                        break;
                    case 1:
                    case 4:
                        checkPermission(Manifest.permission.CALL_PHONE, CALL_PERMISSION);
                        break;
                    case 2:
                        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION);
                        break;
                    case 3:
                        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION);
                        break;
                    case 5:
                        checkPermission(Manifest.permission.READ_CONTACTS, CONTACTS_PERMISSION);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemOne) {
            openCamera();
            return true;
        } else if (item.getItemId() == R.id.itemTwo) {
            Toast.makeText(this, "uftfyuf", Toast.LENGTH_SHORT).show();        } else {
            return true;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void openCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        } else {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (i.resolveActivity(getPackageManager()) != null)
                startActivityForResult(i, REQUEST_CAMERA);
        }
    }

    private void checkPermission(String callPhone, int callPermission) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, callPhone) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{callPhone}, callPermission);
        } else {
            Toast.makeText(this, "Permission Granted !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Allow !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == CHAT_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Allow !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Allow !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Allow !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == CONTACTS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Allow !", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ShowDialogRed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(MainActivity.this));
        View view = getLayoutInflater().inflate(R.layout.custome_dialog_red, null);
        Button btnDone = view.findViewById(R.id.btnDone_red);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(MainActivity.this));
        View view = getLayoutInflater().inflate(R.layout.custome_dialog, null);
        Button btnDone = view.findViewById(R.id.btnDone);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}