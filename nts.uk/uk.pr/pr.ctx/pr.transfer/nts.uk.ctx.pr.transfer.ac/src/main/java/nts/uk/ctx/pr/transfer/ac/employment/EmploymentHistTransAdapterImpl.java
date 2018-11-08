package nts.uk.ctx.pr.transfer.ac.employment;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.transfer.dom.adapter.employment.EmploymentHistTransAdapter;
import nts.uk.ctx.pr.transfer.dom.adapter.employment.EmploymentHistImport;


/**
 * アダプタ実装：所属雇用履歴を取得する
 * @author HungTT
 */
@Stateless
public class EmploymentHistTransAdapterImpl implements EmploymentHistTransAdapter {

//	@Inject
//	private IEmploymentHistoryPub employmentHistoryPub;
	
	@Override
	public List<EmploymentHistImport> findByEmployeeIdOrderByStartDate(String employeeId) {

//		List<EmploymentHisOfEmployee> empHists = this.employmentHistoryPub.getEmploymentHisBySid(employeeId);

//		return empHists.stream().map(c -> new EmploymentHistImport(
//				c.getSId(), c.getEmploymentCD(), c.getStartDate(), c.getEndDate())).collect(Collectors.toList());
		return Collections.emptyList();
	}
}
