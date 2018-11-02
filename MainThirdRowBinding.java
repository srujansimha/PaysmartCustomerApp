package com.webingate.paysmartcustomerapp.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.webingate.paysmartcustomerapp.object.MainViewThirdLevelObject;

public abstract class MainThirdRowBinding extends ViewDataBinding {
  @NonNull
  public final TextView nameTextView;

  @NonNull
  public final ImageView newImageView;

  @Bindable
  protected MainViewThirdLevelObject mThirdLevelObj;

  protected MainThirdRowBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, TextView nameTextView, ImageView newImageView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.nameTextView = nameTextView;
    this.newImageView = newImageView;
  }

  public abstract void setThirdLevelObj(@Nullable MainViewThirdLevelObject thirdLevelObj);

  @Nullable
  public MainViewThirdLevelObject getThirdLevelObj() {
    return mThirdLevelObj;
  }

  @NonNull
  public static MainThirdRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static MainThirdRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<MainThirdRowBinding>inflate(inflater, com.webingate.paysmartcustomerapp.R.layout.main_third_row, root, attachToRoot, component);
  }

  @NonNull
  public static MainThirdRowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static MainThirdRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<MainThirdRowBinding>inflate(inflater, com.webingate.paysmartcustomerapp.R.layout.main_third_row, null, false, component);
  }

  public static MainThirdRowBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static MainThirdRowBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (MainThirdRowBinding)bind(component, view, com.webingate.paysmartcustomerapp.R.layout.main_third_row);
  }
}
