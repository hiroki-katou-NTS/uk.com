package nts.uk.file.at.infra.vacation.set;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.RoundProcessingClassification;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.util.List;

public class CommonTempHolidays {
    public static String getTextEnumExpirationTime(int value) {
        switch (value) {
            case 0: {
                return "当月";
            }
            case 1: {
                return "常に繰越";
            }
            case 2: {
                return "年度末クリア";
            }
            case 3: {
                return "1ヶ月";
            }
            case 4: {
                return "2ヶ月";
            }
            case 5: {
                return "3ヶ月";
            }
            case 6: {
                return "4ヶ月";
            }
            case 7: {
                return "5ヶ月";
            }
            case 8: {
                return "6ヶ月";
            }
            case 9: {
                return "7ヶ月";
            }
            case 10: {
                return "8ヶ月";
            }
            case 11: {
                return "9ヶ月";
            }
            case 12: {
                return "10ヶ月";
            }
            case 13: {
                return "11ヶ月";
            }
            case 14: {
                return "1年";
            }

        }
        return null;
    }

    public static String getTextEnumApplyPermission(int value) {
        switch (value) {
            case 0: {
                return "許可しない";
            }
            case 1: {
                return "許可する";
            }
        }
        return null;
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
        return null;
    }
    public static String getTextEnumTimeDigestiveUnit(int value) {
        switch (value) {
            case 0: {
                return "1分";
            }
            case 1: {
                return "15分";
            }
            case 2: {
                return "30分";
            }
            case 3: {
                return "1時間";
            }
            case 4: {
                return "2時間";
            }
        }
        return null;

    }
    public static String getTextEnumSubHolTransferSetAtr(int value) {
        switch (value) {
            case 0: {
                return I18NText.getText("Enum_SubHolTransferSetAtr_specifiedTimeSubHol");
            }
            case 1: {
                return  I18NText.getText("Enum_SubHolTransferSetAtr_certainTimeExcSubHol");
            }

        }
        return null;

    }
    public static String checkOcurrType(int value){
        switch (value) {
            case 0: {
                return "-";
            }
            case 1: {
                return "○";
            }

        }
        return null;
    }
    public static String getTextEnumManageDistinct(int value){
        switch (value) {
            case 0: {
                return "管理しない";
            }
            case 1: {
                return "管理する";
            }

        }
        return null;
    }
    public static String getTextEnumSixtyHourExtra(int value){
        switch (value) {
            case 0: {
                return "常に繰越";
            }
            case 1: {
                return "1ヶ月";
            }
            case 2: {
                return "2ヶ月";
            }
            case 3: {
                return "3ヶ月";
            }
            case 4: {
                return "4ヶ月";
            }
            case 5: {
                return "5ヶ月";
            }
            case 6: {
                return "6ヶ月";
            }
            case 7: {
                return "7ヶ月";
            }
            case 8: {
                return "8ヶ月";
            }
            case 9: {
                return "9ヶ月";
            }
            case 10: {
                return "10ヶ月";
            }
            case 11: {
                return "11ヶ月";
            }
            case 12: {
                return "12ヶ月";
            }


        }
        return null;
    }
    public static String getEnumMaxDayReference(int value){
        switch (value) {
            case 0: {
                return "会社一律";
            }
            case 1: {
                return "年休付与テーブルを参照";
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
                return "当年付与分から消化する";
            }
            case 1: {
                return "繰越分から消化する";
            }

        }
        return "";
    }
    public static String getSettingDistinct(int value){
        switch (value) {
            case 0: {
                return "設定しない";
            }
            case 1: {
                return "設定する";
            }

        }
        return "";
    }
    public static String getEnumTimeAnnualRoundProcesCla(int value){
        switch (value) {
            case 0: {
                return "1日に切り上げる";
            }
            case 1: {
                return "0日に切り捨てる";
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
