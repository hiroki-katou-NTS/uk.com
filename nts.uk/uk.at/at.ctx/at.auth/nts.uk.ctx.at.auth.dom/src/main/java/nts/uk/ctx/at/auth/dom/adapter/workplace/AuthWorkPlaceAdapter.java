package nts.uk.ctx.at.auth.dom.adapter.workplace;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;

public interface AuthWorkPlaceAdapter {
	
	List<String> getListWorkPlaceID(String employeeID , GeneralDate referenceDate);
	
	WorkplaceInfoImport getWorkplaceListId(GeneralDate referenceDate, String employeeID, boolean referEmployee);
	
	/**
	 * 職場と基準日から所属職場履歴項目を取得する
	 * @param workPlaceId  職場ID
	 * @param baseDate 基準日
	 */
	List<AffWorkplaceHistoryItemImport> getWorkHisItemfromWkpIdAndBaseDate(String workPlaceId, GeneralDate baseDate);
	
	// 指定社員が参照可能な職場リストを取得する（職場管理者なし）
	List<String> getListWorkPlaceIDNoWkpAdmin(String employeeID , int empRange , GeneralDate referenceDate);
	
	// 職場管理者Repository.取得する(社員ID, 年月日)
	List<WorkplaceManagerImport> findListWkpManagerByEmpIdAndBaseDate(String employeeId, GeneralDate baseDate);
	
	// [No.650]社員が所属している職場を取得する
	AffWorkplaceHistoryItemImport getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date);
	
	/**
	 * @name と基準日から所属職場履歴項目を取得する
	 * @param workPlaceId 	職場リスト 	List<職場ID>
	 * @param baseDate 基準日
	 */
	List<AffWorkplaceHistoryItemImport> getWorkHisItemfromWkpIdsAndBaseDate(List<String> workPlaceIds, GeneralDate baseDate);
	
	//[No.559]運用している職場の情報をすべて取得する	
	List<WorkplaceInforImport2> getAllActiveWorkplaceInfor(String companyId, GeneralDate baseDate);
	
	/**
	 * @name 参照可能社員の所属職場を取得するAdapter
	 * @param userID ユーザID
	 * @param employeeID 社員ID
	 * @param date 基準日
	 * @return 	社員一覧	Map<社員ID,職場ID>
	 */
	public Map<String, String> getReferenceableEmployees(String userID, String employeeID, GeneralDate date);
	
	/**
	 * @name 全ての職場の所属社員を取得するAdapter
	 * @param 会社ID		
	 * @param date 年月日
	 * @return 	社員一覧	Map<社員ID,職場ID>
	 */
	public Map<String, String> getemployeesAllWorkplaces(String employeeID, GeneralDate baseDate);
}

