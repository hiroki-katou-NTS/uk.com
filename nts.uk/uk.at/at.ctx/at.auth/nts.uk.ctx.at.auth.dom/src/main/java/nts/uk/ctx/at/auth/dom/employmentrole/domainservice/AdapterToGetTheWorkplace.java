package nts.uk.ctx.at.auth.dom.employmentrole.domainservice;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AffWorkplaceHistoryItemImport;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AuthWorkPlaceAdapter;

/**
 * @author thanhpv
 * @name 所属職場を取得するAdapter
 */
@Stateless
public class AdapterToGetTheWorkplace {
	
	@Inject
	private AuthWorkPlaceAdapter authWorkPlaceAdapter;

	/**
	 * @param employeeID 	社員ID
	 * @param date 年月日
	 * @return 	Map<社員ID,職場ID> 所属情報
	 */
	public Map<String, String> get(String employeeID, GeneralDate date) {
		Map<String, String> result = new HashMap<String, String>();
		AffWorkplaceHistoryItemImport i =  authWorkPlaceAdapter.getAffWkpHistItemByEmpDate(employeeID, date);
		if(i != null) {
			result.put(employeeID, i.getWorkplaceId());
		}
		return result;
	}
}
