package com.example.capstonproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class TagSearchAddActivity extends AppCompatActivity {

    private BluetoothSPP bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_search_add);

        Button btnConnect = findViewById(R.id.BluetoothConnectbtn1); //연결시도


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (this.checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("블루투스에 대한 액세스가 필요합니다");
                builder.setMessage("어플리케이션이 블루투스를 감지 할 수 있도록 위치 정보 액세스 권한을 부여하십시오.");
                builder.setPositiveButton(android.R.string.ok, null);

                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2 );
                    }
                });
                builder.show();
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (this.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("블루투스에 대한 액세스가 필요합니다");
                builder.setMessage("어플리케이션이 블루투스를 연결 할 수 있도록 위치 정보 액세스 권한을 부여하십시오.");
                builder.setPositiveButton(android.R.string.ok, null);

                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 3 );
                    }
                });
                builder.show();
            }
        }

        bt = new BluetoothSPP(this); //Initializing

        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신


            public void onDataReceived(byte[] data, String message) {
                String[] array = message.split(",");
                if(array[0].equals("$GPGGA")) {
                    String lat1 = array[2].substring(0,2);
                    String lat2 = array[2].substring(2);
                    String lon1 = array[4].substring(0,3);
                    String lon2 = array[4].substring(3);
                    double LatF = Double.parseDouble(lat1) + Double.parseDouble(lat2)/60;
                    float LongF = Float.parseFloat(lon1) + Float.parseFloat(lon2)/60;
                    String latitude = Double.toString(LatF);
                    String longitude = Float.toString(LongF);
                    String tagID = bt.getConnectedDeviceAddress();

                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if(success){

                                }else{
                                    Toast.makeText(getApplicationContext(),"친구 등록 실패",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                    };

                    AddTagLocationRequest addTagLocationRequest = new AddTagLocationRequest(tagID,latitude ,longitude,  responseListener);
                    RequestQueue queue4 = Volley.newRequestQueue(TagSearchAddActivity.this);
                    queue4.add(addTagLocationRequest);

                    //AddTagLocation Request 클래스 사용
                    //ㅏ데이터베이스에 넘겨주는 코드
                }


            }
        });

        bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() { //데이터 수신


            public void onDataReceived(byte[] data, String message) {


            }
        });
        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() { //연결됐을 때
            public void onDeviceConnected(String name, String address) {

                PersonalTagActivity personalTagActivity = new PersonalTagActivity();            // 연결되면 그 태그의 아이디를 personaltagActivity화면에 태그 아이디 칸에 아이디 입력
                String deviceID = bt.getConnectedDeviceAddress();
                personalTagActivity.ptagid = deviceID;

                Toast.makeText(getApplicationContext()
                        , "Connected to " + name + "\n" + address
                        , Toast.LENGTH_SHORT).show();
            }

            public void onDeviceDisconnected() { //연결해제
                Toast.makeText(getApplicationContext()
                        , "Connection lost", Toast.LENGTH_SHORT).show();
            }

            public void onDeviceConnectionFailed() { //연결실패
                Toast.makeText(getApplicationContext()
                        , "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });



        btnConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       try {
           switch (requestCode) {
               case 1: {
                   if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                       Log.d("디버깅", "coarse location permission granted");
                   } else {
                       final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                       builder.setTitle("권한 제한");
                       builder.setMessage("위치 정보 및 액세스 권한이 허용되지 않았으므로 블루투스를 검색 및 연결할수 없습니다.");
                       builder.setPositiveButton(android.R.string.ok, null);
                       builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                           @Override
                           public void onDismiss(DialogInterface dialog) {
                           }

                       });
                       builder.show();
                   }
                   break;
               }
               case 2: {
                   if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                       Log.d("디버깅", "coarse location permission granted");
                   } else {
                       final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                       builder.setTitle("권한 제한");
                       builder.setMessage("블루투스 스캔권한이 허용되지 않았습니다.");
                       builder.setPositiveButton(android.R.string.ok, null);
                       builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                           @Override
                           public void onDismiss(DialogInterface dialog) {
                           }

                       });
                       builder.show();
                   }
                   break;
               }
               case 3: {
                   if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                       Log.d("디버깅", "coarse location permission granted");
                   } else {
                       final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                       builder.setTitle("권한 제한");
                       builder.setMessage("블루투스 연결 권한이 허용되지 않았습니다.");
                       builder.setPositiveButton(android.R.string.ok, null);
                       builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                           @Override
                           public void onDismiss(DialogInterface dialog) {
                           }

                       });
                       builder.show();
                   }
                   break;
               }
           }
       }
       catch (SecurityException ex){
           final AlertDialog.Builder builder = new AlertDialog.Builder(this);
           builder.setTitle("권한 제한");
           builder.setMessage("위치 정보 및 액세스 권한이 허용되지 않았으므로 블루투스를 검색 및 연결할수 없습니다.");
           builder.setPositiveButton(android.R.string.ok, null);
           builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

               @Override
               public void onDismiss(DialogInterface dialog) {
               }

           });
           builder.show();
       }

        return;
    }



    public void onDestroy() {
        super.onDestroy();
        bt.stopService(); //블루투스 중지
    }

    public void onStart() {
        super.onStart();
        if (!bt.isBluetoothEnabled()) { //
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);

        } else {
            if (!bt.isServiceAvailable()) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER); //DEVICE_ANDROID는 안드로이드 기기 끼리
                setup();
            }
        }
    }
 //버튼 클릭시 태그에서 소리나게 하는 리스너
  /*  public void setup() {
        Button btnSend = findViewById(R.id.btnSend); //데이터 전송
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                bt.send("o",true);
            }
        });
    }
*/

    public void setup() {
        bt.send("o",true);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}

