/**
 * 
 */
package nts.uk.ctx.at.shared.pubimp.employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.pub.employee.ContractTimeEmployee;
import nts.uk.ctx.at.shared.pub.employee.ContractTimeEmployeeExport;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * @author hieult
 *
 */
@Stateless
public class ContractTimeEmployeePubImpl implements ContractTimeEmployee {

	@Inject
	private WorkingConditionRepository workingConditionRepository;

	@Inject
	private WorkingConditionItemRepository	workingConditionItemRepository;
	
	@Override
	public List<ContractTimeEmployeeExport> getData(List<String> listEmpID, GeneralDate baseDate) {
		List<String> listHistID = new ArrayList<>();
		List<ContractTimeEmployeeExport> data = new ArrayList<>();
		//ドメインモデル「労働条件」をすべて取得する
		//(Lấy all domain [WorkingCondition])
		List<WorkingCondition> listWorkingCondition = workingConditionRepository.getBySids(listEmpID,baseDate);
		List<List<DateHistoryItem>> lstDateHistoryItem = listWorkingCondition.stream().map(c->c.dateHistoryItem).collect(Collectors.toList());
		for (List<DateHistoryItem> dateHistoryItem : lstDateHistoryItem) {
			for(DateHistoryItem c :dateHistoryItem){
				String histID = c.identifier();
				listHistID.add(histID);
			}
			
		}
		
		//ドメインモデル「労働条件項目」を全て取得する
		//(Lấy all domain [WorkingConditionItem])
		List<WorkingConditionItem> listWorkingConditionItem = workingConditionItemRepository.getByListHistoryID(listHistID);
		for (WorkingConditionItem workingConditionItem : listWorkingConditionItem) {
			String employeeID = workingConditionItem.getEmployeeId();
			int contractTime = workingConditionItem.getContractTime().v();
			ContractTimeEmployeeExport a = new ContractTimeEmployeeExport(employeeID, contractTime);
			data.add(a);
		}
		
		return data;
	}

	
	
	

}
