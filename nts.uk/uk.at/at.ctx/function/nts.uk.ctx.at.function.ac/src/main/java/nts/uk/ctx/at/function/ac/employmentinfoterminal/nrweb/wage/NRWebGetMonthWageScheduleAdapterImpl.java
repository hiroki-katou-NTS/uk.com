package nts.uk.ctx.at.function.ac.employmentinfoterminal.nrweb.wage;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebGetMonthWageScheduleAdapter;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.NRWebMonthWageScheduleImported;

/**
 * @author atsuki_sakuratani
 *
 */
@Stateless
public class NRWebGetMonthWageScheduleAdapterImpl implements NRWebGetMonthWageScheduleAdapter {

	@Override
	public List<NRWebMonthWageScheduleImported> GetDataMonthWageSchedule(String cid, String employeeId,
			DatePeriod period) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
