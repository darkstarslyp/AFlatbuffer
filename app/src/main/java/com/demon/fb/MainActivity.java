package com.demon.fb;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.flatbuffers.FlatBufferBuilder;


import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {

    private TextView mTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTxt = findViewById(R.id.sample_text);

        FlatBufferBuilder builder = new FlatBufferBuilder();


        int symbol =  builder.createString("123456");
        int model = builder.createString(Build.MODEL);
        int osVersion = builder.createString(Build.VERSION.RELEASE);
        int brand = builder.createString(Build.BRAND);
        int cpu = builder.createString(Build.CPU_ABI);
        int processName = builder.createString("com.demon.fb");

        AppInfo.startAppInfo(builder);


        AppInfo.addSymbolId(builder, symbol);
        AppInfo.addModel(builder, model);
        AppInfo.addSystemVersion(builder, osVersion);
        AppInfo.addBrand(builder, brand);
        AppInfo.addCpuArchitecture(builder, cpu);
        AppInfo.addProcessName(builder, processName);

        AppInfo.addIsRoot(builder, false);
        AppInfo.addMemorySize(builder, 1000);
        AppInfo.addFreeMemorySize(builder, 500);


        int orc = AppInfo.endAppInfo(builder);
        builder.finish(orc);

        ByteBuffer byteBuffer = builder.dataBuffer();
        AppInfo appInfo = AppInfo.getRootAsAppInfo(byteBuffer);

        StringBuilder sb = new StringBuilder();
        sb.append("symbolId: ").append(appInfo.symbolId()).append("\n");
        sb.append("model: ").append(appInfo.model()).append("\n");
        sb.append("osVersion: ").append(appInfo.systemVersion()).append("\n");
        sb.append("brand: ").append(appInfo.brand()).append("\n");
        sb.append("cpuArchitecture: ").append(appInfo.cpuArchitecture()).append("\n");
        sb.append("processName: ").append(appInfo.processName()).append("\n");


        mTxt.setText(sb.toString());
    }


}
