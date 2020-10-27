package nts.uk.file.com.infra.role;

import nts.arc.i18n.I18NText;

import java.util.List;
import java.util.stream.Collectors;

public class CommonRole {
    public static final String GET_FUNCTION_NO_CAS005 = "SELECT FUNCTION_NO,DISPLAY_NAME " +
            "FROM SACCT_WKP_FUNCTION ORDER BY DISPLAY_ORDER ASC";
    //CAS 009
    public static final String CAS009_23 = "コードカラム";
    public static final String CAS009_24 = "名称カラム";
    public static final String CAS009_25 = "担当区分カラム";
    public static final String CAS009_26 = "社員１参照範囲カラム";
    public static final String CAS009_27 = "未来日参照権限カラム";
    public static final String GET_FUNCTION_NO_CAS009 = "SELECT FUNCTION_NO,FUNCTION_NAME " +
            "FROM PPECT_ROLE_FUNC ORDER BY DISPLAY_ORDER ASC";

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

    public static String getFutureDateRefPermit(int value){
    	// enum ScheduleEmployeeRef
    	switch (value) {
		case 0:
			return I18NText.getText("Enum_ScheduleEmployeeRef_ALL");
		case 1:
			return I18NText.getText("Enum_ScheduleEmployeeRef_ALL_INCLUDE_SUBOR");
		case 2:
			return I18NText.getText("Enum_ScheduleEmployeeRef_ALL_EXCLUDE_SUBOR");
		case 3:
			return I18NText.getText("Enum_ScheduleEmployeeRef_ALL_TEAMS");
		case 4:
			return I18NText.getText("Enum_ScheduleEmployeeRef_SAME_EMPLOYEE_REF_RANGE");
        
    	}
    	return null;
    }


}
