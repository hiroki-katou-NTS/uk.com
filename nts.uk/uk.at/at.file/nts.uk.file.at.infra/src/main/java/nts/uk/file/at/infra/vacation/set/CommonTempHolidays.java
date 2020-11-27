package nts.uk.file.at.infra.vacation.set;


import nts.arc.i18n.I18NText;

public class CommonTempHolidays {
    public static String getTextEnumExpirationTime(int value) {
        switch (value) {
            case 0: {
                return I18NText.getText("Enum_ExpirationTime_THIS_MONTH");
            }
            case 1: {
                return I18NText.getText("Enum_ExpirationTime_UNLIMITED");
            }
            case 2: {
                return I18NText.getText("Enum_ExpirationTime_END_OF_YEAR");
            }
            case 3: {
                return I18NText.getText("Enum_ExpirationTime_ONE_MONTH");
            }
            case 4: {
                return I18NText.getText("Enum_ExpirationTime_TWO_MONTH");
            }
            case 5: {
                return I18NText.getText("Enum_ExpirationTime_THREE_MONTH");
            }
            case 6: {
                return I18NText.getText("Enum_ExpirationTime_FOUR_MONTH");
            }
            case 7: {
                return I18NText.getText("Enum_ExpirationTime_FIVE_MONTH");
            }
            case 8: {
                return I18NText.getText("Enum_ExpirationTime_SIX_MONTH");
            }
            case 9: {
                return I18NText.getText("Enum_ExpirationTime_SEVEN_MONTH");
            }
            case 10: {
                return I18NText.getText("Enum_ExpirationTime_EIGHT_MONTH");
            }
            case 11: {
                return I18NText.getText("Enum_ExpirationTime_NINE_MONTH");
            }
            case 12: {
                return I18NText.getText("Enum_ExpirationTime_TEN_MONTH");
            }
            case 13: {
                return I18NText.getText("Enum_ExpirationTime_ELEVEN_MONTH");
            }
            case 14: {
                return I18NText.getText("Enum_ExpirationTime_ONE_YEAR");
            }

        }
        return "";
    }

    public static String getTextEnumApplyPermission(int value) {
        switch (value) {
            case 0: {
                return I18NText.getText("Enum_ApplyPermission_NOT_ALLOW");
            }
            case 1: {
                return I18NText.getText("Enum_ApplyPermission_ALLOW");
            }
        }
        return "";
    }

    public static String getTextEnumDeadlCheckMonth(int value) {
        switch (value) {
            case 0: {
                return I18NText.getText("Enum_DeadlCheckMonth_ONE_MONTH");
            }
            case 1: {
                return I18NText.getText("Enum_DeadlCheckMonth_TWO_MONTH");
            }
            case 2: {
                return I18NText.getText("Enum_DeadlCheckMonth_THREE_MONTH");
            }
            case 3: {
                return I18NText.getText("Enum_DeadlCheckMonth_FOUR_MONTH");
            }
            case 4: {
                return I18NText.getText("Enum_DeadlCheckMonth_FIVE_MONTH");
            }
            case 5: {
                return I18NText.getText("Enum_DeadlCheckMonth_SIX_MONTH");
            }
        }
        return "";
    }
    public static String getTextEnumTimeDigestiveUnit(int value) {
        switch (value) {
            case 0: {
                return I18NText.getText("Enum_TimeDigestiveUnit_OneMinute");
            }
            case 1: {
                return I18NText.getText("Enum_TimeDigestiveUnit_FifteenMinute");
            }
            case 2: {
                return I18NText.getText("Enum_TimeDigestiveUnit_ThirtyMinute");
            }
            case 3: {
                return I18NText.getText("Enum_TimeDigestiveUnit_OneHour");
            }
            case 4: {
                return I18NText.getText("Enum_TimeDigestiveUnit_TwoHour");
            }
        }
        return "";

    }
    public static String getTextEnumSubHolTransferSetAtr(int value) {
        switch (value) {
            case 0: {
                return I18NText.getText("Enum_SubHolTransferSetAtr_specifiedTimeSubHol");
            }
            case 1: {
                return I18NText.getText("Enum_SubHolTransferSetAtr_certainTimeExcSubHol");
            }
        }
        return "";

    }
    public static String checkOcurrType(int value){
        switch (value) {
            case 0: {
                return I18NText.getText("-");
            }
            case 1: {
                return I18NText.getText("○");
            }

        }
        return "";
    }
    public static String getTextEnumManageDistinct(int value){
        switch (value) {
            case 0: {
                return I18NText.getText("Enum_ManageDistinct_NO");
            }
            case 1: {
                return I18NText.getText("Enum_ManageDistinct_YES");
            }

        }
        return "";
    }
    
    public static String getTextEnumTermManagement(int value){
        switch (value) {
            case 0: {
                return I18NText.getText("Enum_TermManagement_MANAGE_BY_TIGHTENING");
            }
            case 1: {
                return I18NText.getText("Enum_TermManagement_MANAGE_BASED_ON_THE_DATE");
            }

        }
        return "";
    }
    
    public static String getTextEnumSixtyHourExtra(int value){
        switch (value) {
            case 0: {
                return I18NText.getText("Enum_SixtyHourExtra_ALLWAYS");
            }
            case 1: {
                return I18NText.getText("Enum_SixtyHourExtra_ONE_MONTH");
            }
            case 2: {
                return I18NText.getText("Enum_SixtyHourExtra_TWO_MONTH");
            }
            case 3: {
                return I18NText.getText("Enum_SixtyHourExtra_THREE_MONTH");
            }
            case 4: {
                return I18NText.getText("Enum_SixtyHourExtra_FOUR_MONTH");
            }
            case 5: {
                return I18NText.getText("Enum_SixtyHourExtra_FIVE_MONTH");
            }
            case 6: {
                return I18NText.getText("Enum_SixtyHourExtra_SIX_MONTH");
            }
            case 7: {
                return I18NText.getText("Enum_SixtyHourExtra_SEVEN_MONTH");
            }
            case 8: {
                return I18NText.getText("Enum_SixtyHourExtra_EIGHT_MONTH");
            }
            case 9: {
                return I18NText.getText("Enum_SixtyHourExtra_NINE_MONTH");
            }
            case 10: {
                return I18NText.getText("Enum_SixtyHourExtra_TEN_MONTH");
            }
            case 11: {
                return I18NText.getText("Enum_SixtyHourExtra_ELEVEN_MONTH");
            }
            case 12: {
                return I18NText.getText("Enum_SixtyHourExtra_TWELVE_MONTH");
            }
        }
        return "";
    }
    public static String getEnumMaxDayReference(int value){
        switch (value) {
            case 0: {
                return I18NText.getText("Enum_MaxDayReference_CompanyUniform");
            }
            case 1: {
                return I18NText.getText("Enum_MaxDayReference_ReferAnnualGrantTable");
            }
        }
        return "";
    }
    public static String getEnumRoundProcessingClassification(int value){
        switch (value) {
            case 0: {
                return I18NText.getText("Enum_RoundUpToTheDay");
            }
            case 1: {
                return I18NText.getText("Enum_TruncateOnDay0");
            }
            case 2: {
                return I18NText.getText("Enum_FractionManagementNo");
            }
        }
        return "";
    }
    public static String getEnumAnnualPriority(int value){
        switch (value) {
            case 0: {
                return I18NText.getText("Enum_AnnualPriority_FIFO");
            }
            case 1: {
                return I18NText.getText("Enum_AnnualPriority_LIFO");
            }

        }
        return "";
    }
    public static String getSettingDistinct(int value){
        switch (value) {
            case 0: {
                return I18NText.getText("Enum_SettingDistinct_NO");
            }
            case 1: {
                return I18NText.getText("Enum_SettingDistinct_YES");
            }

        }
        return "";
    }
    public static String getEnumTimeAnnualRoundProcesCla(int value){
        switch (value) {
            case 0: {
                return I18NText.getText("Enum_TimeAnnualRoundProcesCla_TruncateOnDay0");
            }
            case 1: {
                return I18NText.getText("Enum_TimeAnnualRoundProcesCla_RoundUpToTheDay");
            }

        }
        return "";
    }
    public static String convertToTime(int param){
        int m = param / 60;
        int s = param % 60;

        return String.format("%02d:%02d", m, s);

    }


}
