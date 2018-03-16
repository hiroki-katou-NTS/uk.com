package nts.uk.ctx.bs.employee.pubimp.employment;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryOfEmployee;
import nts.uk.ctx.bs.employee.pub.employment.EmploymentHisOfEmployee;
import nts.uk.ctx.bs.employee.pub.employment.IEmploymentHistoryPub;

@Stateless
public class EmploymentHistoryPubImp implements IEmploymentHistoryPub{
	
	@Inject
	private EmploymentHistoryItemRepository employmentHistoryItemRepository;
	
	@Override
	public List<EmploymentHisOfEmployee> getEmploymentHisBySid(String sID) {
		// アルゴリズム「社員の雇用履歴を全て取得する」を実行する
		List<EmploymentHistoryOfEmployee> listEmpOfEmployee = employmentHistoryItemRepository.getEmploymentBySID(sID);
		if (!listEmpOfEmployee.isEmpty()){
			return listEmpOfEmployee.stream().map(temp -> {
				return new EmploymentHisOfEmployee(temp.getSId(),temp.getStartDate(),temp.getEndDate(),temp.getEmploymentCD());
			}).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
	
	

}
