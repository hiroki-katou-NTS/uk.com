package nts.uk.ctx.at.auth.dom.employmentrole.domainservice;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.role.RoleAdaptor;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AuthWorkPlaceAdapter;
import nts.uk.ctx.at.auth.dom.employmentrole.EmployeeReferenceRange;
import nts.uk.ctx.at.auth.dom.employmentrole.RoleType;

/**
 * @author thanhpv
 * @name 参照可能社員の所属職場を取得するPublish
 */
@Stateless
public class GetWorkplaceToWhichTheEmployeeReferredTo {
	
	@Inject
	private AuthWorkPlaceAdapter authWorkPlaceAdapter;
	
	@Inject
	private RoleAdaptor roleAdaptor;
	
	@Inject
	private AdapterToGetTheWorkplace adapterToGetTheWorkplace;
	
	@Inject
	private AdapterToGetTheListOfWorkplaces adapterToGetTheListOfWorkplaces;

	/**
	 * @param userID ユーザID
	 * @param employeeID 社員ID
	 * @param date 基準日
	 */
	public Map<String, String> get(String userID, String employeeID, GeneralDate date) {
		
		//$参照範囲 = 社員参照範囲を取得する(ユーザID,ロール種類 .就業,基準日)
		Integer range = roleAdaptor.getEmployeeReferenceRange(userID, RoleType.EMPLOYMENT.value, date);
		
		//	if $参照範囲 == 自分のみ	
		if(range != null && range == EmployeeReferenceRange.ONLY_MYSELF.value) {
			//return 所属職場を取得するAdapter.取得する(社員ID,基準日)
			return adapterToGetTheWorkplace.get(employeeID, date);
			
		}
		//$職場リスト = 指定社員が参照可能な職場リストを取得する(基準日,$参照範囲,社員ID)
		List<String> workplaceList = authWorkPlaceAdapter.getListWorkPlaceIDNoWkpAdmin(employeeID, range, date);
		
		return adapterToGetTheListOfWorkplaces.get(workplaceList, date);
	}
}
