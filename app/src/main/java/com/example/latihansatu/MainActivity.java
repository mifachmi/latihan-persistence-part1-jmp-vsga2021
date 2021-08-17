package com.example.latihansatu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.latihansatu.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String FILENAME = "internalFile.txt";
    public boolean successCreated, successDeleted;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBuatFile.setOnClickListener(this);
        binding.btnBacaFile.setOnClickListener(this);
        binding.btnHapusFile.setOnClickListener(this);
        binding.btnUbahFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        doThis(v.getId());
    }

    void buatFile() {
        String isiFile = "Coba Isi Data File Text";
        File file = new File(getFilesDir(), FILENAME);
        FileOutputStream outputStream;

        try {
            successCreated = file.createNewFile();
            if (successCreated) {
                Toast.makeText(this, "Berhasil Membuat File Baru", Toast.LENGTH_SHORT).show();
            }
            outputStream = new FileOutputStream(file, true);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void ubahFile() {
        String ubah = "Update Isi Data File Text";
        File file = new File(getFilesDir(), FILENAME);
        FileOutputStream outputStream;

        try {
            if(!file.exists()) {
                Toast.makeText(this, "Tidak ada file yang perlu diedit", Toast.LENGTH_SHORT).show();
                return;
            }
            outputStream = new FileOutputStream(file, false);
            outputStream.write(ubah.getBytes());
            Toast.makeText(this, "Berhasil Mengedit Isi File", Toast.LENGTH_SHORT).show();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void bacaFile() {
        File file = new File(getFilesDir(), FILENAME);

        if(file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
            binding.tvBacaFile.setText(text.toString());
        } else {
            Toast.makeText(this, "Tidak ada file ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    void hapusFile() {
        File file = new File(getFilesDir(), FILENAME);

        if (file.exists()) {
            successDeleted = file.delete();
            if (successDeleted) {
                binding.tvBacaFile.setText(R.string.hasil_baca_file);
                Toast.makeText(this, "File " + FILENAME +" Berhasil Dihapus", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Tidak ada file yang harus dihapus", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void doThis(int id) {
        switch (id) {
            case R.id.btnBuatFile:
                buatFile();
                break;
            case R.id.btnBacaFile:
                bacaFile();
                break;
            case R.id.btnUbahFile:
                ubahFile();
                break;
            case R.id.btnHapusFile:
                hapusFile();
                break;
        }
    }
}