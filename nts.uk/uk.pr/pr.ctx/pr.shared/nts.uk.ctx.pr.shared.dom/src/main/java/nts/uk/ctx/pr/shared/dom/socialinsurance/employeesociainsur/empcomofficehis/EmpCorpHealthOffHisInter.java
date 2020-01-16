package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.history.DateHistoryItem;
@Getter
@AllArgsConstructor
public class EmpCorpHealthOffHisInter {
	
	private EmpCorpHealthOffHis domain;
	
	private DateHistoryItem itemToBeUpdated;
	
	private AffOfficeInformation newHistInfo;
	
}
