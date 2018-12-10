package nts.uk.file.com.infra.role;

import nts.arc.i18n.I18NText;

import java.util.List;
import java.util.stream.Collectors;

public class CommonRole {
    public static final String CAS005_122 = "コードカラム";
    public static final String CAS005_123 = "名称カラム";
    public static final String CAS005_124 = "担当区分カラム";
    public static final String CAS005_125 = "社員１参照範囲カラム";
    public static final String CAS005_126 = "未来日参照権限カラム";
    public static final String CAS005_127 = "メニュー設定カラム";
    public static final String CAS005_128 = "スケジュール画面社員１参照カラム";
    public static final String FUNCTION_NO_ = "FUNCTION_NO_";
    public static final String GET_FUNCTION_NO_CAS005 = "SELECT FUNCTION_NO,DISPLAY_NAME " +
            "FROM KASMT_WORPLACE_FUNCTION";
    //CAS 009
    public static final String CAS009_23 = "コードカラム";
    public static final String CAS009_24 = "名称カラム";
    public static final String CAS009_25 = "担当区分カラム";
    public static final String CAS009_26 = "社員１参照範囲カラム";
    public static final String CAS009_27 = "未来日参照権限カラム";
    public static final String GET_FUNCTION_NO_CAS009 = "SELECT FUNCTION_NO,FUNCTION_NAME " +
            "FROM PPEMT_PER_INFO_FUNCTION";

    public static String getQueryFunctionNo(List<Integer> listFunctionNo) {
        return listFunctionNo.stream().map(x -> x.toString())
                .collect(Collectors.toList()).stream().collect(Collectors.joining("], [", "[", "]"));
    }

    public static String getTextEnumEmplReferRange(int value) {
        String resulf = "";
        switch (value) {
            case 0: {
                resulf = I18NText.getText("Enum_EmployeeReferenceRange_allEmployee");
                break;
            }
            case 1: {
                resulf = I18NText.getText("Enum_EmployeeReferenceRange_departmentAndChild");
                break;
            }
            case 2: {
                resulf = I18NText.getText("Enum_EmployeeReferenceRange_departmentOnly");
                break;
            }
            case 3: {
                resulf = I18NText.getText("Enum_EmployeeReferenceRange_onlyMyself");
                break;
            }
        }
        return resulf;
    }
    public static String getTextRoleAtr(String value){
        if(value.equals("1"))
            return I18NText.getText("Enum_RoleAtr_General");
        return I18NText.getText("Enum_RoleAtr_inCharge");

    }


}
