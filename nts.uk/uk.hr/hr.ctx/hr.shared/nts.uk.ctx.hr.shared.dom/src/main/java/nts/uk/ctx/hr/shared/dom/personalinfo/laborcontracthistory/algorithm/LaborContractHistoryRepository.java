/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.algorithm;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.EmployeeInfoDto;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.LaborContractHistory;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.LaborContractHistoryDto;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.WageTypeDto;

/**
 * @author laitv
 *
 */
public interface LaborContractHistoryRepository {

	List<LaborContractHistory> getListDomain(List<String> sids, GeneralDate baseDate);
	
	List<WageTypeDto> getListWageType(List<String> sids, GeneralDate baseDate);
	
	List<EmployeeInfoDto> getListEmployeeInfoDto(String cid, GeneralDate startDate, GeneralDate endDate);
	
	List<LaborContractHistoryDto> getListDomainByListSidAndStartDate(String cid, List<String> sids, GeneralDate startDate);

}
