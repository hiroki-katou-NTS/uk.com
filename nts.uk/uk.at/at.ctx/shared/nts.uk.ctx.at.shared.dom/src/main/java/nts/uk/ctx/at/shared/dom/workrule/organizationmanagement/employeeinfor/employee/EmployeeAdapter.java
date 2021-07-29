package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeInfoImported;

/**
 * 社員Adapter
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.組織管理.社員情報.社員.Imported.社員Adapter
 * @author dan_pv
 */
public interface EmployeeAdapter {
	
	/**
	 * 社員の情報を取得する
	 * @param employeeIds 社員IDリスト
	 * @param baseDate 基準日
	 * @param param 取得したい社員情報
	 * @return
	 */
	public List<EmployeeInfoImported> getEmployeeInfo(
			List<String> employeeIds, 
			GeneralDate baseDate, 
			EmployeeInfoWantToBeGet param);

}
