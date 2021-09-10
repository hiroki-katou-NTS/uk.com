package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.jobtitle;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 社員職位Adapter
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.組織管理.社員情報.所属職位履歴.Imported.社員職位Adapter
 * @author lan_lt
 *
 */
public interface EmpJobTitleAdapter {
	/**
	 * 社員職位を取得する
	 * @param baseDate 基準日
	 * @param employeeIds 社員IDリスト
	 * @return
	 */
	List<EmployeeJobTitleImport> getEmpJobTitle(GeneralDate baseDate, List<String> employeeIds);
}
