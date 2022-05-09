package com.apps.dbrah_delivery.uis.activity_login;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;

import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.adapter.CountryAdapter;
import com.apps.dbrah_delivery.databinding.ActivityLoginBinding;
import com.apps.dbrah_delivery.databinding.DailogVerificationCodeBinding;
import com.apps.dbrah_delivery.databinding.DialogCountriesBinding;
import com.apps.dbrah_delivery.model.CountryModel;
import com.apps.dbrah_delivery.model.LoginModel;
import com.apps.dbrah_delivery.model.UserModel;
import com.apps.dbrah_delivery.mvvm.ActivityLoginMvvm;
import com.apps.dbrah_delivery.uis.activity_base.BaseActivity;
import com.apps.dbrah_delivery.uis.activity_home.HomeActivity;
import com.apps.dbrah_delivery.uis.activity_sign_up.SignUpActivity;
import com.apps.dbrah_delivery.uis.activity_verification_code.VerificationCodeActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private String phone_code = "";
    private String phone = "";
    private LoginModel model;
    private ActivityResultLauncher<Intent> launcher;
    private AlertDialog dialog;
    private List<CountryModel> countryModelList = new ArrayList<>();
    private CountryAdapter countriesAdapter;
    private ActivityLoginMvvm mvvm;
    private DailogVerificationCodeBinding dailogVerificationCodeBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }


    private void initView() {
        countryModelList=new ArrayList<>();
        model = new LoginModel();
        binding.setModel(model);
        mvvm = ViewModelProviders.of(this).get(ActivityLoginMvvm.class);
        countriesAdapter = new CountryAdapter(this);
        binding.spinner.setAdapter(countriesAdapter);
        mvvm.getCoListMutableLiveData().observe(this, countryModels -> {
            if (countryModels != null && countryModels.size() > 0) {
                countryModelList.clear();
                countryModelList.addAll(countryModels);
                countriesAdapter.updateList(countryModelList);
            }
        });

        mvvm.setCountry();


        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().startsWith("0")) {
                    binding.edtPhone.setText("");
                }
            }
        });
        model.setPhone_code("+966");
        sortCountries();
        binding.btnLogin.setOnClickListener(v -> {
            createVerificationCodeDialog();
        });

        mvvm.getTime().observe(this, time -> {
            if (dailogVerificationCodeBinding != null) {
                dailogVerificationCodeBinding.tvResend.setText(time);

            }
        });

        mvvm.canResend().observe(this, canResend -> {
            if (dailogVerificationCodeBinding != null) {
                dailogVerificationCodeBinding.tvResend.setEnabled(canResend);
                if (canResend) {
                    dailogVerificationCodeBinding.tvResend.setText(getString(R.string.resend));

                }
            }

        });
        binding.btnLogin.setOnClickListener(v -> {
            mvvm.login(this, model.getPhone_code(), model.getPhone());
        });
        mvvm.getUserData().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if (userModel != null) {
                    setUserModel(userModel);
                    navigateToHomeActivity();
                } else {
                    createVerificationCodeDialog();
                }
            }
        });
    }

    private void navigateToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);

        startActivity(intent);
        finish();
    }


    private void sortCountries() {
        Collections.sort(countryModelList, (country1, country2) -> {
            return country1.getName().trim().compareToIgnoreCase(country2.getName().trim());
        });
    }

    private void createVerificationCodeDialog() {
        androidx.appcompat.app.AlertDialog builder = new androidx.appcompat.app.AlertDialog.Builder(this)
                .create();
        builder.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dailogVerificationCodeBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dailog_verification_code, null, false);
        dailogVerificationCodeBinding.setModel(model);
        builder.setView(dailogVerificationCodeBinding.getRoot());
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(false);
        dailogVerificationCodeBinding.edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dailogVerificationCodeBinding.btnVerify.setEnabled(s.toString().length() == 6);

            }
        });
        dailogVerificationCodeBinding.tvResend.setOnClickListener(v -> mvvm.reSendSmsCode(model));
        builder.show();

        dailogVerificationCodeBinding.btnVerify.setOnClickListener(v -> {
            navigateToSignUpActivity();
        });
        builder.setOnCancelListener(dialog -> mvvm.stopTimer());

    }

    private void navigateToSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("phone_code", model.getPhone_code());
        intent.putExtra("phone", model.getPhone());
        startActivity(intent);
        finish();
    }


//    private void navigateToVerificationCodeActivity() {
//        Intent intent = new Intent(this, VerificationCodeActivity.class);
//        intent.putExtra("phone_code", model.getPhone_code());
//        intent.putExtra("phone", model.getPhone());
//        launcher.launch(intent);
//    }

    public void setItemData(CountryModel countryModel) {
        dialog.dismiss();
        model.setPhone_code(countryModel.getDialCode());
        binding.setModel(model);

    }
}