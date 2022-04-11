package nts.uk.ctx.at.function.ac.employmentinfoterminal.nrweb.wage;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.ItemValueImported;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageRecordImported;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.annual.NRWebGetAnnualWageRecordAdapter;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage.ItemValueExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage.NRWebGetAnnualWageRecordPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage.NRWebMonthWageRecordExport;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceAmountMonth;

/**
* @author sakuratani
*
*			NRWeb照会年間賃金月別実績を取得AdapterImpl
*
*/
@Stateless
public class NRWebGetAnnualWageRecordAdapterImpl implements NRWebGetAnnualWageRecordAdapter {

	@Inject
	NRWebGetAnnualWageRecordPub nRWebGetAnnualWageRecordPub;

	@Override
	public NRWebMonthWageRecordImported get(String employeeId, DatePeriod period, List<YearMonth> yearMonths) {
		return this.toImported(nRWebGetAnnualWageRecordPub.get(employeeId, period, yearMonths));
	}

	//NRWebMonthWageRecordExport -> NRWebMonthWageRecordImported へ変換
	private NRWebMonthWageRecordImported toImported(NRWebMonthWageRecordExport export) {
		return new NRWebMonthWageRecordImported(export.getPeriod(), toImported(export.getMeasure()),
				toImported(export.getCurrentWork()), toImported(export.getCurrentOvertime()));
	}

	//ItemValueExport -> ItemValueImported へ変換
	private ItemValueImported toImported(ItemValueExport export) {
		return new ItemValueImported(new AttendanceTimeMonthWithMinus(export.getTime()),
				new AttendanceAmountMonth(export.getAmount()),
				export.getColor().map(x -> new ColorCode(x)));
	}

}
