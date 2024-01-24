package com.neon.cookbook;

import javafx.scene.layout.AnchorPane;

public class Context {
  static private Context mContext;
  public String mUsername;
  public String mNickname;
  public int mUserId;
  public String mRole;
  public AnchorPane showSpace;

  static Context getContext() {
    if (mContext == null) {
      mContext = new Context();
    }
    return mContext;
  }

  static void removeContext() {
    mContext = null;
  }

}