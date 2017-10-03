package nts.uk.ctx.workflow.dom.adapter.bs;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;

public interface EmployeeAdapter {
	/**
	 * get workplace id by employeeId and basedate
	 * @param companyId 会社ID
	 * @param employeeId　社員ID
	 * @param baseDate　基準日
	 * @return　職場ID
	 */
	String getWorkplaceId(String companyId,String employeeId, GeneralDate baseDate);

	/**
	 * get employment code by companyID, employeeID and base date
	 * @param companyId 会社ID
	 * @param employeeId　社員ID　
	 * @param baseDate　基準日
	 * @return　雇用コード
	 */
	String getEmploymentCode(String companyId, String employeeId, GeneralDate baseDate);

	/**
	 * 「所属職場履歴」をすべて取得する
	 * get employee information by companyId, workplaceId and base date
	 * @param companyId　会社ID
	 * @param workplaceIds　職場IDリスト
	 * @param baseDate　基準日
	 * @return 社員情報
	 */
	List<EmployeeImport> findByWpkIds(String companyId, List<String> workplaceIds, GeneralDate baseDate);
	/**
	 * ドメインモデル「職場別就業承認ルート」を取得する(lấy domain「職場別就業承認ルート」)
	 * 取得した所属職場ID＋その上位職場ID
	 * @param companyId
	 * @param employeeId
	 * @param date
	 * @return
	 */
	List<String> findWpkIdsBySid(String companyId, String employeeId, GeneralDate date);
	/**
	 * 基準日、会社IDに一致するすべての社員を取得する(在職中の社員）
	 * @param companyId
	 * @param baseDate
	 * @return
	 */
	List<EmployeeImport> getEmployeesAtWorkByBaseDate(String companyId , GeneralDate baseDate);
	
	/**
	 * Get employee Name
	 * @param sID
	 * @return
	 */
	String getEmployeeName(String sID);
}
