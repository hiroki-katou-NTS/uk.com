package nts.uk.ctx.at.request.dom.application.common.adapter.record;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.actualsituation.confirmstatusmonthly.StatusConfirmMonthImport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface RecordWorkInfoAdapter {
	
	public RecordWorkInfoImport_Old getRecordWorkInfo(String employeeId, GeneralDate ymd);
	
	public RecordWorkInfoImport getRecordWorkInfoRefactor(String employeeId, GeneralDate ymd);
	
	/**
	 * request list 634 -> 586
	 * @param companyId
	 * @param listEmployeeId
	 * @param yearmonthInput
	 * @param clsId
	 * @param clearState
	 * @return
	 */
	public Optional<StatusConfirmMonthImport> getConfirmStatusMonthly(String companyId, List<String> listEmployeeId,
			YearMonth yearmonthInput, Integer clsId);
	
	/**
	 * request list 165
	 * @param employeeId
	 * @param datePeriod
	 * @return
	 */
	public List<GeneralDate> getResovleDateIdentify(String employeeId, DatePeriod datePeriod);

}
