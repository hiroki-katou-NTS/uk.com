package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.workinfo;

import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.workinfo.GetWorkInfo;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：勤務情報を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetWorkInfoImpl implements GetWorkInfo {

	/** 日別実績の勤務情報 */
	@Inject
	public WorkInformationRepository workInformationOfDaily;

	/** 実績の勤務情報を取得する */
	@Override
	public Optional<WorkInformation> getRecord(String employeeId, GeneralDate ymd) {
		WorkInfoList workInfoList = new WorkInfoList(
				employeeId, new DatePeriod(ymd, ymd), this.workInformationOfDaily);
		return workInfoList.getRecord(ymd);
	}
	
	/** 予定の勤務情報を取得する */
	@Override
	public Optional<WorkInformation> getSchedule(String employeeId, GeneralDate ymd) {
		WorkInfoList workInfoList = new WorkInfoList(
				employeeId, new DatePeriod(ymd, ymd), this.workInformationOfDaily);
		return workInfoList.getSchedule(ymd);
	}
	
	/** 実績の勤務情報を取得する */
	@Override
	public Map<GeneralDate, WorkInformation> getRecordMap(String employeeId, DatePeriod period) {
		WorkInfoList workInfoList = new WorkInfoList(
				employeeId, period, this.workInformationOfDaily);
		return workInfoList.getRecordMap();
	}
	
	/** 予定の勤務情報を取得する */
	@Override
	public Map<GeneralDate, WorkInformation> getScheduleMap(String employeeId, DatePeriod period) {
		WorkInfoList workInfoList = new WorkInfoList(
				employeeId, period, this.workInformationOfDaily);
		return workInfoList.getScheduleMap();
	}
}
