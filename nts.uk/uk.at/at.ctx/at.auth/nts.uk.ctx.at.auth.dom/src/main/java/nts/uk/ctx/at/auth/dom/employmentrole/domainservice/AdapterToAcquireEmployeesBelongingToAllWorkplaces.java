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
 * @name 全ての職場の所属社員を取得するAdapter	
 */
@Stateless
public class AdapterToAcquireEmployeesBelongingToAllWorkplaces {
	
	@Inject
	private AcquireEmployeesBelongingToAllWorkplaces acquireEmployeesBelongingToAllWorkplaces;

	/**
	 * @param 会社ID		
	 * @param date 年月日
	 * @return 	社員一覧	Map<社員ID,職場ID>
	 */
	public Map<String, String> getAllEmployees(String companyId, GeneralDate baseDate) {
		Map<String, String> result = new HashMap<String, String>();
		//$所属職場 = 全ての職場の所属社員を取得するPublish.取得する(会社ID,基準日)
		List<AffWorkplaceHistoryItemImport> list =  acquireEmployeesBelongingToAllWorkplaces.get(companyId, baseDate);
		for (AffWorkplaceHistoryItemImport i : list) {
			result.put(i.getEmployeeId(), i.getWorkplaceId());
		}
		/*
		 * return $所属職場： map <$.社員ID,$.職場ID>
		 */
		return result;
	}
}
