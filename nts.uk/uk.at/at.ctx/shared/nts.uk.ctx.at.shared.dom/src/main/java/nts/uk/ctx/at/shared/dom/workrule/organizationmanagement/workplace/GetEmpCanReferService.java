package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import java.util.Collections;
import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 参照可能な社員を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.組織管理.職場.参照可能な社員を取得する
 * @author Hieult
 * 
 * OldName: GetEmpCanReferBySpecOrganizationService
 */
public class GetEmpCanReferService {
	
	/**
	 * すべて取得する
	 * @param require
	 * @param date 年月日
	 * @param employeeId 社員ID
	 * @return List<社員ID>
	 */
	public static List<String> getAll(Require require, GeneralDate date, String employeeId) {
		
		// TODO implement
		return Collections.emptyList();
	}
		
	/**
	 * 組織を指定して取得する		
	 * @param require
	 * @param date
	 * @param employeeId
	 * @param targetOrg
	 * @return List<社員ID>
	 */
	public static List<String> getByOrg(Require require, GeneralDate date, String employeeId, TargetOrgIdenInfor targetOrg){
		
		// TODO implement
		return Collections.emptyList();
	}
	
	public static interface Require {
		
		/**
		 * 職場グループで参照可能な所属社員を取得する
		 * @param date 年月日
		 * @param empId 社員ID
		 * @param workplaceGroupID 職場グループID
		 * @return List<社員ID>
		 */
		List<String> getEmpCanReferByWorkplaceGroup(GeneralDate date, String empId, String workplaceGroupID);
		
		/**
		 * 職場グループで参照可能な所属社員をすべて取得する
		 * @param date 年月日
		 * @param empId 社員ID
		 * @return List<社員ID>
		 */
		List<String> getAllEmpCanReferByWorkplaceGroup(GeneralDate date, String empId);
		
		/**
		 * 社員を並び替える
		 * @param employeeIdList 社員IDリスト
		 * @param systemType システム区分
		 * @param sortOrderNo 並び順NO
		 * @param date 年月日
		 * @param nameType 氏名の種類
		 * @return List<社員ID>
		 */
		List<String> sortEmployee(List<String> employeeIdList, Integer systemType, Integer sortOrderNo, GeneralDate date, Integer nameType);
		// TODO change systemType(システム区分), nameType(氏名の種類) to ENUM
		
		/**
		 * ロールIDを取得する
		 * @return
		 */
		String getRoleID();
		
		/**
		 * 社員を検索する
		 * @param regulationInfoEmpQuery
		 * @param roleId
		 * @return
		 */
		List<String> searchEmployee(RegulationInfoEmpQuery regulationInfoEmpQuery, String roleId);
	}
	
	
}
