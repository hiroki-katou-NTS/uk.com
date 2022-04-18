package nts.uk.ctx.at.function.ac.employmentinfoterminal.nrweb.wage;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.ItemValueImported;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageScheduleImported;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.month.NRWebGetMonthWageScheduleAdapter;
import nts.uk.ctx.at.schedule.pub.nrweb.wage.ItemValueExport;
import nts.uk.ctx.at.schedule.pub.nrweb.wage.NRWebGetMonthWageSchedulePub;
import nts.uk.ctx.at.schedule.pub.nrweb.wage.NRWebMonthWageScheduleExport;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceAmountMonth;

/**
* @author sakuratani
*
*			NRWeb照会月間賃金予定を取得AdapterImpl
*
*/
@Stateless
public class NRWebGetMonthWageScheduleAdapterImpl implements NRWebGetMonthWageScheduleAdapter {

	@Inject
	NRWebGetMonthWageSchedulePub nRWebGetMonthWageSchedulePub;

	@Override
	public NRWebMonthWageScheduleImported get(String employeeId, DatePeriod period) {
		return this.toImported(nRWebGetMonthWageSchedulePub.get(employeeId, period));
	}

	//NRWebMonthWageScheduleExport -> NRWebMonthWageScheduleImported へ変換
	private NRWebMonthWageScheduleImported toImported(NRWebMonthWageScheduleExport export) {
		return new NRWebMonthWageScheduleImported(export.getPeriod(), toItemValueImported(export.getMeasure()),
				toItemValueImported(export.getScheduleWork()), toItemValueImported(export.getScheduleOvertime()));
	}

	//ItemValueExport -> ItemValueImported へ変換
	private ItemValueImported toItemValueImported(ItemValueExport export) {
		return new ItemValueImported(new AttendanceTimeMonthWithMinus(export.getTime()),
				new AttendanceAmountMonth(export.getAmount()),
				export.getColor().map(x -> new ColorCode(x)));
	}

}
