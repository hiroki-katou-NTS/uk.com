/**
 * 
 */
package nts.uk.ctx.sys.auth.pubimp.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;
import nts.uk.ctx.sys.auth.pub.employee.WorkPlaceAuthorityDto;
import nts.uk.ctx.sys.auth.pub.employee.WorkplaceManagerDto;

/**
 * @author laitv
 * 職場リスト、基準日から就業確定できる職場管理者を取得する
 */
@Stateless
public class ObtainWkpListAndWkpManager {
	
	
	/**
	 * 【INPUT】 require{ 職場管理者を取得(List<職場ID>、基準日) 所属職場権限を取得(会社ID、ロールID、機能NO) }
	 * 会社ID 
	 * 基準日 
	 * List<職場ID>
	 * 
	 * 【OUTPUT】 Map＜職場ID、社員ID＞
	 */
	public Map<String, String> getData(EmployeePublisher.RequireRQ653 requireRQ653, String companyID,
			GeneralDate referenceDate, List<String> workplaceIDs) {
		
		List<WorkplaceManagerDto> workplaceManagers = requireRQ653.getWorkplaceManager( workplaceIDs,  referenceDate );
		if(workplaceManagers.isEmpty())
			return new HashMap<String, String>();
		
		Map<String, String> result = new HashMap<>();
		for (WorkplaceManagerDto workplaceManager : workplaceManagers) {
			Optional<WorkPlaceAuthorityDto> workPlaceAuthority = requireRQ653.getWorkAuthority(companyID, workplaceManager.getWorkplaceManagerId(), 2);
			if(workPlaceAuthority.isPresent()){
				if(workPlaceAuthority.get().isAvailability() == true){
					result.put(workplaceManager.getWorkplaceId(), workplaceManager.getEmployeeId());
				}
			}
		}
		return result;
	}
}
