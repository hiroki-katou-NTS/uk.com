/**
 * 
 */
package nts.uk.ctx.at.shared.pubimp.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.pub.employee.ContractTimeEmployeeExport;
import nts.uk.ctx.at.shared.pub.employee.ContractTimeEmployeePub;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * @author hieult
 *
 */
@Stateless
public class ContractTimeEmployeePubImpl implements ContractTimeEmployeePub {

	@Inject
	private WorkingConditionRepository workingConditionRepository;

	@Inject
	private WorkingConditionItemRepository	workingConditionItemRepository;
	
	@Override
	public List<ContractTimeEmployeeExport> getData(List<String> listEmpID, GeneralDate baseDate) {
		//ドメインモデル「労働条件」をすべて取得する
		//(Lấy all domain [WorkingCondition])
		List<List<DateHistoryItem>> listWorkingCondition = workingConditionRepository.getBySids(listEmpID, baseDate)
				.stream().map(c -> c.dateHistoryItem).collect(Collectors.toList());
		List<String> listHistID = listWorkingCondition.stream().flatMap(c -> c.stream())
				.map(c -> c.identifier()).collect(Collectors.toList());
		
		//ドメインモデル「労働条件項目」を全て取得する
		//(Lấy all domain [WorkingConditionItem])
		if(listHistID.isEmpty()) return new ArrayList<>();
		List<ContractTimeEmployeeExport> result = workingConditionItemRepository
				.getByListHistoryID(listHistID).stream()
				.map(c -> new ContractTimeEmployeeExport(c.getEmployeeId(), c.getContractTime().v()))
				.collect(Collectors.toList());
		return result;
	}
}
