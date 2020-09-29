package nts.uk.ctx.at.shared.ac.employment.employwork.leaveinfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkHistoryAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.EmpLeaveWorkPeriodImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo.TempAbsenceFrameNo;
import nts.uk.ctx.bs.employee.pub.temporaryabsence.EmpLeaveWorkPeriodExport;
import nts.uk.ctx.bs.employee.pub.temporaryabsence.EmployeeLeaveHistoryPublish;

/**
 * 社員の休業履歴Adapter Impl
 * 
 * @author HieuLt
 *
 */
@Stateless
public class EmpLeaveWorkHistoryAdapterImpl implements EmpLeaveWorkHistoryAdapter {

	@Inject
	public EmployeeLeaveHistoryPublish publish;

	@Override
	public List<EmpLeaveWorkPeriodImport> getHolidayPeriod(List<String> lstEmpId, DatePeriod datePeriod) {
		List<EmpLeaveWorkPeriodExport> data = publish.getHolidayPeriod(lstEmpId, datePeriod);
		// return 社員の休職休業履歴Publish.期間を指定して休業期間を取得する( 基準日, 社員IDリスト )
		List<EmpLeaveWorkPeriodImport> result = data.stream()
				.map(c -> new EmpLeaveWorkPeriodImport(c.getEmpID(),
						new TempAbsenceFrameNo(new BigDecimal(c.getTempAbsenceFrameNo())), c.getDatePeriod()))
				.collect(Collectors.toList());

		return result;
	}

}
