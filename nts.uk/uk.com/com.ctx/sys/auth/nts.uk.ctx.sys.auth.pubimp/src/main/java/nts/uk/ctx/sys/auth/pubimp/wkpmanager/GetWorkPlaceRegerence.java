package nts.uk.ctx.sys.auth.pubimp.wkpmanager;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.sys.auth.pub.grant.RoleTypeExport;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.ctx.sys.auth.pub.wkpmanager.ReferenceableWorkplace;
import nts.uk.ctx.sys.auth.pubimp.workplace.GetWorkPlaceFromReferenceRange;

/**
 * 
 * @author sonnlb
 *
 *         参照できる職場・社員を取得する
 */
@Stateless
public class GetWorkPlaceRegerence {

	@Inject
	private RoleExportRepo roleExportRepo;
	
	@Inject
	private GetWorkPlaceFromReferenceRange getWorkPlaceFromReferenceRange;
	
	@Inject
	private WorkplaceAdapter workplaceAdapter;

	/**
	 * [1] 取得する
	 */
	/**
	 * 
	 * @param userID ユーザID
	 * @param employeeID 社員ID
	 * @param date 基準日
	 * @return 参照可能職場
	 */
	public ReferenceableWorkplace get(String userID, String employeeID, GeneralDate date) {
		// $社員参照範囲 == 社員参照範囲を取得する(ユーザID,ロール種類 .就業,基準日)
		Integer range = this.roleExportRepo.getEmployeeReferenceRange(userID, RoleTypeExport.EMPLOYMENT.value, date);

		// return 参照範囲から参照できる職場・社員を取得する#取得する(require,社員ID,基準日,$社員参照範囲)

		return this.getWorkPlaceFromReferenceRange.get(new RequireImpl(workplaceAdapter), employeeID, date, range);
	}
	
	@AllArgsConstructor 
	private class RequireImpl implements GetWorkPlaceFromReferenceRange.Require {
		private WorkplaceAdapter workplaceAdapter;

		@Override
		public Map<String, String> getAWorkplace(String employeeID, GeneralDate date) {
			return this.workplaceAdapter.getAWorkplace(employeeID, date);
		}

		@Override
		public Map<String, String> getByListIds(List<String> workPlaceIds, GeneralDate baseDate) {
			return this.workplaceAdapter.getByListIds(workPlaceIds, baseDate);
		}
		
		
	}
}

