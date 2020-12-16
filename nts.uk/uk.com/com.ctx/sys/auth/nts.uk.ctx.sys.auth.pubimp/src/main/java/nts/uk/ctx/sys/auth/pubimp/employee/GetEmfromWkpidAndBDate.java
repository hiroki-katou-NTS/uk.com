/**
 * 
 */
package nts.uk.ctx.sys.auth.pubimp.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.workplace.AffWorkplaceHistoryItemExport3;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.sys.auth.dom.role.RoleType;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;
import nts.uk.ctx.sys.auth.pub.employee.WorkPlaceAuthorityDto;
import nts.uk.ctx.sys.auth.pub.grant.RoleFromUserIdPub;
import nts.uk.ctx.sys.auth.pub.user.UserInforEx;
import nts.uk.ctx.sys.auth.pub.user.UserPublisher;

/**
 * @author laitv 職場リスト、基準日から就業確定できるロールを持っている社員を取得する 
 * path :UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.権限管理.アルゴリズム.職場リスト、基準日から就業確定できるロールを持っている社員を取得する
 */

@Stateless
public class GetEmfromWkpidAndBDate {
	
	 @Inject
	 private WorkplacePub workplacePub;
	 @Inject
	 private UserPublisher userPublish;
	 @Inject
	 private RoleFromUserIdPub roleFromUserIdPub;

	/**
	 * 【INPUT】 require{ 所属職場権限を取得(会社ID、ロールID、機能NO) } 
	 * 会社ID 
	 * 基準日 
	 * List<職場ID>
	 * 
	 * 【OUTPUT】 Map＜職場ID、社員ID＞
	 */
	public Map<String, String> getData(EmployeePublisher.RequireRQ653 requireRQ653, String companyID,
			GeneralDate referenceDate, List<String> workplaceIDs) {

		// 職場（List）と基準日から所属職場履歴項目を取得する
		List<AffWorkplaceHistoryItemExport3> affWorkplaceHisItems = workplacePub.getWorkHisItemfromWkpIdsAndBaseDate(workplaceIDs,  referenceDate);
		
		if(affWorkplaceHisItems.isEmpty())
			return new HashMap<>();
		
		// OUTPUT「Map＜職場ID、社員ID＞」を返す
		Map<String, String> result = new HashMap<>();
		
		for (AffWorkplaceHistoryItemExport3 item : affWorkplaceHisItems) {
			Optional<UserInforEx> userInfor = userPublish.getByEmpID(item.getEmployeeId());
			if (userInfor.isPresent()) {
				String roleId = roleFromUserIdPub.getRoleFromUserId(userInfor.get().getUserID(), RoleType.EMPLOYMENT.value, referenceDate, companyID);
				if (roleId != null && roleId != "") {
					Optional<WorkPlaceAuthorityDto> workPlaceAuthority = requireRQ653.getWorkAuthority(companyID, roleId, 2);
					if(workPlaceAuthority.isPresent()){
						if(workPlaceAuthority.get().isAvailability() == true){
							result.put(item.getWorkplaceId(), item.getEmployeeId());
						}
					}
				}
			}
		}
		return result;
	}
}
