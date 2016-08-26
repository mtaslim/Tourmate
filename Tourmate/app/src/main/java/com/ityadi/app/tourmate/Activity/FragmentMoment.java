package com.ityadi.app.tourmate.Activity;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ityadi.app.tourmate.ApiHelper.MomentApi;
import com.ityadi.app.tourmate.ApiHelper.MomentApiWithoutPhoto;
import com.ityadi.app.tourmate.Common.Config;
import com.ityadi.app.tourmate.Common.InternetConnection;
import com.ityadi.app.tourmate.Common.InternetConnectionHandler;
import com.ityadi.app.tourmate.Common.Network;
import com.ityadi.app.tourmate.Common.SpreferenceHelper;
import com.ityadi.app.tourmate.PhotoLibrary.PhotoLibrary;
import com.ityadi.app.tourmate.R;
import com.ityadi.app.tourmate.Response.MomentResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMoment extends Fragment {

    public static String EVENT_ID;
    EditText etHeadinge,etExpenseApount,etDescription;
    ImageView photoView;
    Button btnAddMoment;
    View thisLayout;
    public Uri fileUri;
    File file;
    String timeStamp,realPath;
    String heading,description,expenseAmount;
    Bitmap myBitmap;
    Call<MomentResponse> call;

    PhotoLibrary photoLibrary;
    SpreferenceHelper spreferenceHelper;
    String userName;
    Context context;


    public FragmentMoment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        //Internet checking for all Fragement
        if (!InternetConnection.checkConnection(getActivity())){
            startActivity(new Intent(getActivity(), InternetConnectionHandler.class));
        }

        spreferenceHelper = new SpreferenceHelper(getActivity());
        userName = spreferenceHelper.retrive();

        View rootView = inflater.inflate(R.layout.fragment_moment, container, false);
        Bundle bundle = this.getArguments();
        EVENT_ID = bundle.getString("eventId");
        photoLibrary = new PhotoLibrary();

        thisLayout = (View) rootView.findViewById(R.id.fragment_moment);
        etHeadinge = (EditText) rootView.findViewById(R.id.etHeadinge);
        etExpenseApount = (EditText) rootView.findViewById(R.id.etExpenseApount);
        etDescription = (EditText) rootView.findViewById(R.id.etDescription);
        photoView = (ImageView) rootView.findViewById(R.id.photoView);

        btnAddMoment = (Button) rootView.findViewById(R.id.btnAddMoment);
        btnAddMoment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                realPath = ((Dashboard)getActivity()).realPath;

                file = new File(((Dashboard) getActivity()).realPath);
                if(file.exists()){
                    myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    photoView.setImageBitmap(myBitmap);
                }

                heading = etHeadinge.getText().toString();
                expenseAmount = etExpenseApount.getText().toString();
                description = etDescription.getText().toString();

                String req="";
                if("".equals(heading)) req+= "Heading, ";
                if("".equals(expenseAmount)) req+= "Expense Amount, ";
                if("".equals(description)) req+= "Description, ";

                if(req.length()>0) {
                    req = req.substring(0,req.length()-2)+" is required";
                    Snackbar.make(thisLayout,req,Snackbar.LENGTH_LONG).show();
                }
                else {
                    final ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Please wait. Your data is storing...");
                    progressDialog.show();

                    if(realPath!=""){
                        MomentApi momentApi = Network.createService(MomentApi.class);
                        file = new File(realPath);
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part body =  MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);
                        call = momentApi.getAccessToken(Config.APP_KEY,userName,EVENT_ID,heading,expenseAmount,description,body);
                    }
                    else{
                        MomentApiWithoutPhoto momentApi = Network.createService(MomentApiWithoutPhoto.class);
                        call = momentApi.getAccessToken(Config.APP_KEY,EVENT_ID,heading,expenseAmount,description);
                    }

                    call.enqueue(new Callback<MomentResponse>() {
                        @Override
                        public void onResponse(Call<MomentResponse> call, Response<MomentResponse> response) {
                            progressDialog.dismiss();
                            MomentResponse momentResponse=response.body();

                            String msg = momentResponse.getMsg();
                            String err = momentResponse.getErr();
                            int notify = Integer.parseInt(momentResponse.getNotify());

                            if(!"".equals(msg)){
                                Snackbar.make(thisLayout,msg,Snackbar.LENGTH_SHORT).show(); //msg

                                //notification
                                if(notify ==1) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        Notification notification = new Notification.Builder(getActivity())
                                                .setContentTitle("Expense crossed half of Budget. ")
                                                .setSmallIcon(R.mipmap.ic_launcher)
                                                .setContentText("Your Budget "+momentResponse.getBudget()+" & Expense "+momentResponse.getTotalExpense())
                                                .setAutoCancel(true)
                                                .build();
                                        NotificationManager manager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
                                        manager.notify((int) System.currentTimeMillis(), notification);
                                    }
                                }
                                else  if(notify ==2) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        Notification notification = new Notification.Builder(getActivity())
                                                .setContentTitle("Expense crossed 2/3 of Budget. ")
                                                .setSmallIcon(R.mipmap.ic_launcher)
                                                .setContentText("Your Budget "+momentResponse.getBudget()+" & Expense  "+momentResponse.getTotalExpense())
                                                .setAutoCancel(true)
                                                .build();
                                        NotificationManager manager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
                                        manager.notify((int) System.currentTimeMillis(), notification);
                                    }
                                }
                                //

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(getActivity(), Dashboard.class);
                                        startActivity(i);
                                    }
                                }, 2000);
                            }
                            else Snackbar.make(thisLayout,err,Snackbar.LENGTH_LONG).show();
                            Log.e("response", momentResponse.getMsg());
                        }

                        @Override
                        public void onFailure(Call<MomentResponse> call, Throwable t) {
                            Log.e("error", t.toString());
                        }
                    });
                }


            }
        });


        photoView = (ImageView) rootView.findViewById(R.id.photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                File file = new File(((Dashboard) getActivity()).realPath);
                if(file.exists()){
                    myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    photoView.setImageBitmap(myBitmap);
                }
            }
        });

        ((Dashboard) getActivity()).setTitle("Add Moment");
        ((Dashboard) getActivity()).fab.hide();
        return rootView;
    }





}
