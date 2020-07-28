package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import nts.arc.time.GeneralDate;

/**
 * 組織を指定して参照可能な社員を取得する		
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.組織管理.職場
 * @author Hieult 
 *
 */
public class GetEmpCanReferBySpecOrganizationService {
		
	/**
	 * [1] 取得する		
	 * @param require
	 * @param referenceDate --- 年月日
	 * @param epmloyeeId --- 社員ID
	 * @param targetOrgIdenInfor ---対象組織識別情報
	 * @return TargetOrgIdenInfor
	 */
	
	public static List<String> getListEmpID(Require require,GeneralDate referenceDate , String epmloyeeId , TargetOrgIdenInfor targetOrgIdenInfor  ){
		List<String> result = new ArrayList<>();
		switch (targetOrgIdenInfor.getUnit()){
		case WORKPLACE_GROUP :
			//this.
		}
		
		return result;
	}
	/**
	 * [prv-1] 職場グループ単位で取得する	
	 * @param require
	 * @param referenceDate
	 * @param epmloyeeId
	 * @param workplaceGroupId
	 * @return List<社員ID>
	 */
	private List<String> getByWorkPlaceGroup(Require require,GeneralDate referenceDate , String epmloyeeId ,String workplaceGroupId ){
		List<String> result = new ArrayList<>();
		//[R-1] 職場グループで参照可能な所属社員を取得する		
		//QA Adapter tên hai hàm đang không giống nhau
		//	$社員IDリスト = require.職場グループで参照可能な所属社員を取得する( 基準日, 社員ID, 職場グループ )	
		List<String> lstEmpId = require.getReferableEmp(referenceDate, epmloyeeId, Arrays.asList(workplaceGroupId));
		if(lstEmpId.isEmpty()){
			//	$社員IDリスト = require.社員を並び替える( 社員IDリスト, システム区分.就業, null, 基準日, null )				
			// List<String> sortEmployee(List<String> lstmployeeId, int sysAtr ,Integer sortOrderNo, GeneralDate referenceDate , int nameType);
			List<String> result1 = require.sortEmployee(lstEmpId, 0, null, referenceDate, null);
		}
		return result;
	}
	/**
	 * [prv-2] 職場単位で取得する	
	 * @param referenceDate
	 * @param epmloyeeId 
	 * @param workplaceGroupId
	 * @return List<社員ID>
	 */
	private List<String> getByWorkPlace(Require require,GeneralDate referenceDate , String epmloyeeId ,String workplaceGroupId ){
		List<String> result = new ArrayList<>();
		return result;
	}
	
	public static interface Require {
		/**
		 * [R-1] 職場グループで参照可能な所属社員を取得する
		 * 職場グループAdapter.参照可能な所属社員を取得する( 年月日, 社員ID, 職場グループID )
		 * @param date
		 * @param empId
		 * @param lstWorkplaceGroupID
		 * @return
		 */
		List<String> getReferableEmp(GeneralDate date,String empId, List<String> lstWorkplaceGroupID);
		
		
		/**
		 * [R-2] 社員を並び替える			
		 * @param lstmployeeId --- 社員一覧：List＜社員ID＞
		 * @param sysAtr --- システム区分
		 * @param sortOrderNo --- 並び順NO 
		 * @param referenceDate --- 基準日（社員の所属情報を取得するため
		 * @param nameType --- 氏名の種類：Enum＜氏名の種類＞
		 * @return List<String> 
		 */
		List<String> sortEmployee(List<String> lstmployeeId, Integer sysAtr ,Integer sortOrderNo, GeneralDate referenceDate , Integer nameType);
		
		/**
		 * [R-3] ロールIDを取得する		 
		 * @return
		 */
		String getRoleID();
		
		/**
		 * [R-4] 社員を検索する	
		 * @param workingConditionsEmpSearch --- ・就業検索条件：クエリパラメータ「社員検索の就業条件」
		 * @param roleId --- ・いまのロール：ロールID
		 * @return ・並べ替えた社員リスト：List＜社員ID＞
		 */
		List<String> searEmployee(WorkingConditionsEmpSearch workingConditionsEmpSearch , String roleId );
	}
	
	
}
