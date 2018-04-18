package nts.uk.ctx.at.record.ac.employment;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.employment.EmploymentHistAdapter;
import nts.uk.ctx.at.record.dom.adapter.employment.EmploymentHistImport;
import nts.uk.ctx.bs.employee.pub.employment.EmploymentHisOfEmployee;
import nts.uk.ctx.bs.employee.pub.employment.IEmploymentHistoryPub;

/**
 * アダプタ実装：所属雇用履歴を取得する
 * @author shuichu_ishida
 */
@Stateless
public class EmploymentHistAdapterImpl implements EmploymentHistAdapter {

	@Inject
	private IEmploymentHistoryPub employmentHistoryPub;
	
	@Override
	public List<EmploymentHistImport> findByEmployeeIdOrderByStartDate(String employeeId) {

		List<EmploymentHisOfEmployee> empHists = this.employmentHistoryPub.getEmploymentHisBySid(employeeId);

		return empHists.stream().map(c -> new EmploymentHistImport(
				c.getSId(), c.getEmploymentCD(), c.getStartDate(), c.getEndDate())).collect(Collectors.toList());
	}
}
