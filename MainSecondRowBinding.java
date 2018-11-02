package com.webingate.paysmartcustomerapp.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.webingate.paysmartcustomerapp.object.MainViewSecondLevelObject;

public abstract class MainSecondRowBinding extends ViewDataBinding {
  @NonNull
  public final ConstraintLayout mainSecond;

  @NonNull
  public final ImageView subHeaderImageView;

  @NonNull
  public final ImageView subHeaderImageView2;

  @NonNull
  public final TextView subHeaderTextView;

  @Bindable
  protected MainViewSecondLevelObject mSecondLevelObj;

  protected MainSecondRowBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, ConstraintLayout mainSecond, ImageView subHeaderImageView,
      ImageView subHeaderImageView2, TextView subHeaderTextView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.mainSecond = mainSecond;
    this.subHeaderImageView = subHeaderImageView;
    this.subHeaderImageView2 = subHeaderImageView2;
    this.subHeaderTextView = subHeaderTextView;
  }

  public abstract void setSecondLevelObj(@Nullable MainViewSecondLevelObject secondLevelObj);

  @Nullable
  public MainViewSecondLevelObject getSecondLevelObj() {
    return mSecondLevelObj;
  }

  @NonNull
  public static MainSecondRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static MainSecondRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<MainSecondRowBinding>inflate(inflater, com.webingate.paysmartcustomerapp.R.layout.main_second_row, root, attachToRoot, component);
  }

  @NonNull
  public static MainSecondRowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static MainSecondRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<MainSecondRowBinding>inflate(inflater, com.webingate.paysmartcustomerapp.R.layout.main_second_row, null, false, component);
  }

  public static MainSecondRowBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static MainSecondRowBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (MainSecondRowBinding)bind(component, view, com.webingate.paysmartcustomerapp.R.layout.main_second_row);
  }
}
