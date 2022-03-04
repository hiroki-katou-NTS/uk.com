package nts.uk.ctx.at.function.ac.employmentinfoterminal.nrweb.wage;

import javax.ejb.Stateless;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.DailyRecordPeriodAdapter;

/**
 * @author atsuki_sakuratani
 *
 *
 */
@Stateless
public class DailyRecordPeriodAdapterImpl implements DailyRecordPeriodAdapter {

	@Override
	public DatePeriod getPeriodDuringDailyDataExists(String employeeId, DatePeriod period) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
