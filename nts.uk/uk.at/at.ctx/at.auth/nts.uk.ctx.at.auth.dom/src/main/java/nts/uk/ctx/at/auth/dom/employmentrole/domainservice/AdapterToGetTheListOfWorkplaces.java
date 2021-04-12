package nts.uk.ctx.at.auth.dom.employmentrole.domainservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.workplace.AffWorkplaceHistoryItemImport;

/**
 * @author thanhpv
 * @name 所属職場リストを取得するAdapter
 */
@Stateless
public class AdapterToGetTheListOfWorkplaces {
	
	@Inject
	private EmployeesBelongingToTheWorkplace employeesBelongingToTheWorkplace;

	/**
	 * @param employeeID  	List<職場ID>	
	 * @param date 年月日
	 * @return 	社員一覧	Map<社員ID,職場ID>
	 */
	public Map<String, String> get(List<String> workPlaceIds, GeneralDate baseDate) {
		Map<String, String> result = new HashMap<String, String>();
		//	$所属職場 = 職場に所属する社員Publish.取得する(基準日,職場リスト)		
		List<AffWorkplaceHistoryItemImport> list =  employeesBelongingToTheWorkplace.get(workPlaceIds, baseDate);
		for (AffWorkplaceHistoryItemImport i : list) {
			result.put(i.getEmployeeId(), i.getWorkplaceId());
		}
//		return $所属職場：																				
//				map <$.社員ID,$.職場ID>																	
		return result;
	}
}
