package nts.uk.ctx.pr.transfer.ac.employment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employment.EmploymentHisExport;
import nts.uk.ctx.bs.employee.pub.employment.IEmploymentHistoryPub;
import nts.uk.ctx.pr.transfer.dom.adapter.employment.EmploymentHistImport;
import nts.uk.ctx.pr.transfer.dom.adapter.employment.TransEmploymentHistAdapter;

/**
 * アダプタ実装：所属雇用履歴を取得する
 * 
 * @author HungTT
 */
@Stateless
public class TransEmploymentHistAdapterImpl implements TransEmploymentHistAdapter {

	@Inject
	private IEmploymentHistoryPub employmentHistoryPub;

	@Override
	public List<EmploymentHistImport> findByCidAndDate(String companyId, GeneralDate baseDate) {
		List<EmploymentHistImport> result = new ArrayList<>();
		List<EmploymentHisExport> exportResult = employmentHistoryPub.getEmploymentHistoryItem(companyId, baseDate);
		for (EmploymentHisExport ex : exportResult) {
			List<EmploymentHistImport> tmp = ex.getLstEmpCodeandPeriod().stream()
					.map(x -> new EmploymentHistImport(ex.getEmployeeId(), x.getEmploymentCode(), x.getDatePeriod()))
					.collect(Collectors.toList());
			result.addAll(tmp);
		}
		return result;
	}
	
}
