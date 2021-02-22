package nts.uk.ctx.sys.auth.dom.adapter.employee.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmployeeInformationImport;
import javax.ejb.Stateless;

@Stateless
/**
 *UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.権限管理.ユーザ.社員情報を取得する
 */
public class EmployeeService {

	/**
	 * [1] 取得する
	 * @param require
	 * @param employeeId
	 * @param baseDate
	 * @return 対象組織識別情報 (EmployeeInformationImport)
	 */
    public EmployeeInformationImport getEmployeeInformation(Require require, String employeeId, GeneralDate baseDate) {
        return require.findEmployeeInformation(employeeId, baseDate);
    }
    
    public static interface Require {
    	/**
    	 * [R-1] 社員の情報を取得する
    	 * [社員の情報Adapter.取得する( 年月日, 社員ID)
    	 * @param employeeId
    	 * @param baseDate
    	 * @return EmployeeInformationImport
    	 */
    	public EmployeeInformationImport findEmployeeInformation(String employeeId, GeneralDate baseDate);
    }
}

