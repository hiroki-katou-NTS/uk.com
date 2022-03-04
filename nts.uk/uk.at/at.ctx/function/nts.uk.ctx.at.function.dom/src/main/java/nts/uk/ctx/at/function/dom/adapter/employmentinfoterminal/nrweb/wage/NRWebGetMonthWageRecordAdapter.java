package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author sakuratani
 *
 *         NRWeb照会月間賃金実績を取得Adapter
 */
public interface NRWebGetMonthWageRecordAdapter {

	public List<NRWebMonthWageRecordImported> getDataMonthWageRecord(String cid, String employeeId, DatePeriod period);

}
