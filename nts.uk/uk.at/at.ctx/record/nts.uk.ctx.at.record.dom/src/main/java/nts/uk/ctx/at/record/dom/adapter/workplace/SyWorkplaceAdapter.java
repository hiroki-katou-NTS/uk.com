package nts.uk.ctx.at.record.dom.adapter.workplace;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 
 * @author sonnh1
 *
 */
public interface SyWorkplaceAdapter {

	Map<String, Pair<String, String>> getWorkplaceMapCodeBaseDateName(String companyId,
			List<String> wpkCodes, List<GeneralDate> baseDates);
	
	Optional<SWkpHistRcImported> findBySid(String employeeId, GeneralDate baseDate);
	
	List<SWkpHistRcImported> findBySid(List<String>employeeIds, GeneralDate baseDate);

	
	List<WorkplaceInforImport> getWorkplaceInforByWkpIds(String companyId, List<String> listWorkplaceId,
			GeneralDate baseDate);

	/**
	 * [No.597]職場の所属社員を取得する
	 */
	List<EmployeeInfoImported> getLstEmpByWorkplaceIdsAndPeriod(List<String> workplaceIds, DatePeriod period);

	/**
	 * 期間から職場情報を取得
	 * @param companyId
	 * @param datePeriod
	 * @return
	 */
	List<WorkplaceInformationImport> getByCidAndPeriod(String companyId, DatePeriod datePeriod);
	
	/**
	 * @name 所属職場履歴Adapter
	 * @param employeeID
	 * @param date
	 * @return
	 */
	String getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date);
	
	/**
	 * @name 参照可能社員の所属職場を取得するAdapter
	 * @param userID ユーザID
	 * @param employeeID 社員ID
	 * @param date 基準日
	 */
	Map<String, String> getWorkPlace(String userID, String employeeID, GeneralDate date);
	
	/**
	 * @name 全ての職場の所属社員を取得するAdapter
	 * @param companyId 会社ID	会社ID
	 * @param baseDate 	基準日	年月日
	 * @return 	所属職場リスト	List＜所属職場履歴項目＞
	 */
	Map<String, String> getByCID(String companyId, GeneralDate baseDate);
}
