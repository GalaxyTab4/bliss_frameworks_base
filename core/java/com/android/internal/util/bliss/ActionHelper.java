/*
* Copyright (C) 2014 SlimRoms Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.android.internal.util.bliss;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.ThemeConfig;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.os.UserHandle;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ActionHelper {

    public static boolean useSystemUI = false;

    private static final String SYSTEM_METADATA_NAME = "android";
    private static final String SYSTEMUI_METADATA_NAME = "com.android.systemui";
    private static final String SETTINGS_METADATA_NAME = "com.android.settings";

    // get and set the lockcreen shortcut configs from provider and return propper arraylist objects
    // @ActionConfig
    public static ArrayList<ActionConfig> getLockscreenShortcutConfig(Context context) {
        String config = Settings.System.getStringForUser(
                    context.getContentResolver(),
                    Settings.System.LOCKSCREEN_SHORTCUTS,
                    UserHandle.USER_CURRENT);
        if (config == null) {
            config = "";
        }

        return (ConfigSplitHelper.getActionConfigValues(context, config, null, null, true));
    }

    public static void setLockscreenShortcutConfig(Context context,
            ArrayList<ActionConfig> actionConfig, boolean reset) {
        String config;
        if (reset) {
            config = "";
        } else {
            config = ConfigSplitHelper.setActionConfig(actionConfig, true);
        }
        Settings.System.putString(context.getContentResolver(),
                    Settings.System.LOCKSCREEN_SHORTCUTS, config);
    }

    public static ArrayList<ActionConfig> getQuickTileConfigWithDescription(
            Context context, String values, String entries) {
        String config = Settings.System.getStringForUser(
                    context.getContentResolver(),
                    Settings.System.QUICK_TILE_CONFIG,
                    UserHandle.USER_CURRENT);
        if (config == null) {
            config = ActionConstants.QUICK_TILE_CONFIG_DEFAULT;
        }
        return ConfigSplitHelper.getActionConfigValues(context, config, values, entries, true);
    }

    public static void setQuickTileConfig(Context context,
            ArrayList<ActionConfig> actionConfig, boolean reset) {
        String config;
        if (reset) {
            config = ActionConstants.QUICK_TILE_CONFIG_DEFAULT;
        } else {
            config = ConfigSplitHelper.setActionConfig(actionConfig, true);
        }
        Settings.System.putString(context.getContentResolver(),
                    Settings.System.QUICK_TILE_CONFIG,
                    config);
    }

    // Get and set the pie configs from provider and return proper arraylist objects
    // @ActionConfig
    public static ArrayList<ActionConfig> getPieConfig(Context context) {
        return (ConfigSplitHelper.getActionConfigValues(context,
            getPieProvider(context), null, null, false));
    }

    public static ArrayList<ActionConfig> getPieConfigWithDescription(
            Context context, String values, String entries) {
        return (ConfigSplitHelper.getActionConfigValues(context,
            getPieProvider(context), values, entries, false));
    }

    // get and set the navbar configs from provider and return propper arraylist objects
    // @ActionConfig
    public static ArrayList<ActionConfig> getNavBarConfig(Context context) {
        return (ConfigSplitHelper.getActionConfigValues(context,
            getNavBarProvider(context), null, null, false));
    }

    // get @ActionConfig with description if needed and other then an app description
    public static ArrayList<ActionConfig> getNavBarConfigWithDescription(
            Context context, String values, String entries) {
        return (ConfigSplitHelper.getActionConfigValues(context,
            getNavBarProvider(context), values, entries, false));
    }

    private static String getNavBarProvider(Context context) {
        String config = Settings.System.getStringForUser(
                    context.getContentResolver(),
                    Settings.System.NAVIGATION_BAR_CONFIG,
                    UserHandle.USER_CURRENT);
        if (config == null) {
            config = ActionConstants.NAVIGATION_CONFIG_DEFAULT;
        }
        return config;
    }

    private static String getPieProvider(Context context) {
        String config = Settings.System.getStringForUser(
                    context.getContentResolver(),
                    Settings.System.PIE_BUTTONS_CONFIG,
                    UserHandle.USER_CURRENT);
        if (config == null) {
            config = ActionConstants.NAVIGATION_CONFIG_DEFAULT;
        }
        return config;
    }

    public static void setNavBarConfig(Context context,
            ArrayList<ActionConfig> actionConfig, boolean reset) {
        String config;
        if (reset) {
            config = ActionConstants.NAVIGATION_CONFIG_DEFAULT;
        } else {
            config = ConfigSplitHelper.setActionConfig(actionConfig, false);
        }
        Settings.System.putString(context.getContentResolver(),
                    Settings.System.NAVIGATION_BAR_CONFIG,
                    config);
    }

    public static void setPieConfig(Context context,
            ArrayList<ActionConfig> actionConfig, boolean reset) {
        String config;
        if (reset) {
            config = ActionConstants.NAVIGATION_CONFIG_DEFAULT;
        } else {
            config = ConfigSplitHelper.setActionConfig(actionConfig, false);
        }
        Settings.System.putString(context.getContentResolver(),
                    Settings.System.PIE_BUTTONS_CONFIG,
                    config);
    }

    public static ArrayList<ActionConfig> getPieSecondLayerConfig(Context context) {
        return (ConfigSplitHelper.getActionConfigValues(context,
            getPieSecondLayerProvider(context), null, null, false));
    }

    public static ArrayList<ActionConfig> getPieSecondLayerConfigWithDescription(
            Context context, String values, String entries) {
        return (ConfigSplitHelper.getActionConfigValues(context,
            getPieSecondLayerProvider(context), values, entries, false));
    }

    private static String getPieSecondLayerProvider(Context context) {
        String config = Settings.System.getStringForUser(
                    context.getContentResolver(),
                    Settings.System.PIE_BUTTONS_CONFIG_SECOND_LAYER,
                    UserHandle.USER_CURRENT);
        if (config == null) {
            config = ActionConstants.PIE_SECOND_LAYER_CONFIG_DEFAULT;
        }
        return config;
    }

    public static void setPieSecondLayerConfig(Context context,
            ArrayList<ActionConfig> actionConfig, boolean reset) {
        String config;
        if (reset) {
            config = ActionConstants.PIE_SECOND_LAYER_CONFIG_DEFAULT;
        } else {
            config = ConfigSplitHelper.setActionConfig(actionConfig, false);
        }
        Settings.System.putString(context.getContentResolver(),
                    Settings.System.PIE_BUTTONS_CONFIG_SECOND_LAYER,
                    config);
    }

    public static String getNavbarThemePkgName(Context context) {
        if (context == null) return null;
        String res = null;
        ThemeConfig themeConfig = context.getResources().getConfiguration().themeConfig;
        if (themeConfig == null) return res;
        try {
            final String navbarThemePkgName = themeConfig.getOverlayForNavBar();
            final String sysuiThemePkgName = themeConfig.getOverlayForStatusBar();
            if (navbarThemePkgName != null) {
                res = navbarThemePkgName;
            } else {
                res = sysuiThemePkgName;
            }
        } catch (Exception e) {
        }
        return res;
    }

    public static Resources getNavbarThemedResources(Context context) {
        if (context == null) return null;
        ThemeConfig themeConfig = context.getResources().getConfiguration().themeConfig;
        Resources res = null;
        if (themeConfig != null) {
            try {
                final String navbarThemePkgName = themeConfig.getOverlayForNavBar();
                final String sysuiThemePkgName = themeConfig.getOverlayForStatusBar();
                // Check if the same theme is applied for systemui, if so we can skip this
                if (navbarThemePkgName != null && !navbarThemePkgName.equals(sysuiThemePkgName)) {
                    res = context.getPackageManager().getThemedResourcesForApplication(
                            context.getPackageName(), navbarThemePkgName);
                }
            } catch (PackageManager.NameNotFoundException e) {
                // don't care since we'll handle res being null below
            }
        }

        return res != null ? res : context.getResources();
    }

    // General methods to retrieve the correct icon for the respective action.
    public static Drawable getActionIconImage(Context context,
            String clickAction, String customIcon) {
        return getActionIconImage(context, clickAction, customIcon, false);
    }

    public static Drawable getActionIconImage(Context context,
            String clickAction, String customIcon, boolean landscape) {
        int resId = -1;
        Drawable d = null;
        PackageManager pm = context.getPackageManager();
        if (pm == null) {
            return null;
        }

        Resources systemUiResources;
        try {
            if (useSystemUI) {
                systemUiResources = pm.getResourcesForApplication(SYSTEMUI_METADATA_NAME);
            } else {
                systemUiResources = ActionHelper.getNavbarThemedResources(context);
            }
        } catch (Exception e) {
            Log.e("ActionHelper:", "can't access systemui resources",e);
            return null;
        }

        if (!clickAction.startsWith("**")) {
            try {
                String extraIconPath = clickAction.replaceAll(".*?hasExtraIcon=", "");
                if (extraIconPath != null && !extraIconPath.isEmpty()) {
                    File f = new File(Uri.parse(extraIconPath).getPath());
                    if (f.exists()) {
                        d = new BitmapDrawable(context.getResources(),
                                f.getAbsolutePath());
                    }
                }
                if (d == null) {
                    d = pm.getActivityIcon(Intent.parseUri(clickAction, 0));
                }
            } catch (NameNotFoundException e) {
                resId = systemUiResources.getIdentifier(
                    SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_null", null, null);
                if (resId > 0) {
                    d = systemUiResources.getDrawable(resId);
                    return d;
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        if (customIcon != null && customIcon.startsWith(ActionConstants.SYSTEM_ICON_IDENTIFIER)) {
            resId = systemUiResources.getIdentifier(customIcon.substring(
                        ActionConstants.SYSTEM_ICON_IDENTIFIER.length()), "drawable", "android");
            if (resId > 0) {
                return systemUiResources.getDrawable(resId);
            }
        } else if (customIcon != null && !customIcon.equals(ActionConstants.ICON_EMPTY)) {
            File f = new File(Uri.parse(customIcon).getPath());
            if (f.exists()) {
                return new BitmapDrawable(context.getResources(),
                    ImageHelper.getRoundedCornerBitmap(
                        new BitmapDrawable(context.getResources(),
                        f.getAbsolutePath()).getBitmap()));
            } else {
                Log.e("ActionHelper:", "can't access custom icon image");
                return null;
            }
        } else if (clickAction.startsWith("**")) {
            resId = getActionSystemIcon(systemUiResources, clickAction, landscape);

            if (resId > 0) {
                return systemUiResources.getDrawable(resId);
            }
        }
        return d;
    }

    public static int getActionIconUri(Context context,
            String clickAction, String customIcon) {
        return getActionIconUri(context, clickAction, customIcon, false);
    }

    public static int getActionIconUri(Context context,
            String clickAction, String customIcon, boolean landscape) {
        int resId = -1;
        PackageManager pm = context.getPackageManager();
        if (pm == null) {
            return resId;
        }

        Resources systemUiResources;
        try {
            systemUiResources = pm.getResourcesForApplication(SYSTEMUI_METADATA_NAME);
        } catch (Exception e) {
            Log.e("ButtonsHelper:", "can't access systemui resources",e);
            return resId;
        }

        if (customIcon != null && customIcon.startsWith(ActionConstants.SYSTEM_ICON_IDENTIFIER)) {
            resId = systemUiResources.getIdentifier(customIcon.substring(
                        ActionConstants.SYSTEM_ICON_IDENTIFIER.length()),
                        "drawable", "android");
        } else if (clickAction.startsWith("**")) {
            resId = getActionSystemIcon(systemUiResources, clickAction, landscape);
        }

        return resId;
    }

    private static int getActionSystemIcon(Resources systemUiResources, String clickAction) {
        return getActionSystemIcon(systemUiResources, clickAction, false);
    }

    private static int getActionSystemIcon(Resources systemUiResources, String clickAction, boolean landscape) {
        int resId = -1;

        String suffix = landscape ? "_land" : "";
        // ToDo: Add the resources to SystemUI.
        if (clickAction.equals(ActionConstants.ACTION_HOME)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_home"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_BACK)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_back"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_RECENTS)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_recent"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_SEARCH)
                || clickAction.equals(ActionConstants.ACTION_ASSIST)
                || clickAction.equals(ActionConstants.ACTION_KEYGUARD_SEARCH)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_search"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_KEYGUARD_SEARCH)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_search_light", null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_MENU)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_menu"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_MENU_BIG)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_menu"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_IME)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_ime_switcher"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_POWER)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_power"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_POWER_MENU)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_power_menu"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_NOTIFICATIONS)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_notifications", null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_SETTINGS_PANEL)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_qs", null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_VIB)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_vib"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_SILENT)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_silent"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_VIB_SILENT)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_ring_vib_silent"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_SCREENSHOT)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_screenshot"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_THEME_SWITCH)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_theme_switch", null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_LAST_APP)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_lastapp", null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_PIE)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_pie"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_NAVBAR)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_navbar"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_SCREENRECORD)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_screenrecord", null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_TORCH)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_torch"+suffix, null, null);
        } else if (clickAction.equals(ActionConstants.ACTION_RESTARTUI)) {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_qs_systemui_restart", null, null);
        } else {
            resId = systemUiResources.getIdentifier(
                        SYSTEMUI_METADATA_NAME + ":drawable/ic_sysbar_null"+suffix, null, null);
        }
        return resId;
    }
}
