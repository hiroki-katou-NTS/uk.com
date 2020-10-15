package nts.uk.ctx.at.shared.ac.employment.employwork.leaveinfo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentHisScheduleAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmploymentPeriodImported;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.SalarySegment;
import nts.uk.ctx.bs.employee.pub.employment.history.export.EmploymentHistoryPublish;
import nts.uk.ctx.bs.employee.pub.employment.history.export.EmploymentPeriodExported;

/**
 * 社員の雇用履歴Adapter Impl
 * @author HieuLt
 */
@Stateless
public class EmploymentHisScheduleAdapterImpl implements EmploymentHisScheduleAdapter {

	@Inject
	private EmploymentHistoryPublish publish;

	@Override
	public List<EmploymentPeriodImported> getEmploymentPeriod(List<String> listEmpId, DatePeriod datePeriod) {

		List<EmploymentPeriodExported> data = publish.get(listEmpId, datePeriod);
		List<EmploymentPeriodImported> result = data.stream()
				.map(c -> new EmploymentPeriodImported(c.getEmpID(), c.getDatePeriod(), c.getEmploymentCd(),
						!c.getOtpSalarySegment().isPresent() ? Optional.empty() : Optional.ofNullable(EnumAdaptor.valueOf(c.getOtpSalarySegment().get(), SalarySegment.class))))
				.collect(Collectors.toList());
		// return 社員の雇用履歴Publish.取得する( 基準日, 社員IDリスト )
		return result;
	}
}
