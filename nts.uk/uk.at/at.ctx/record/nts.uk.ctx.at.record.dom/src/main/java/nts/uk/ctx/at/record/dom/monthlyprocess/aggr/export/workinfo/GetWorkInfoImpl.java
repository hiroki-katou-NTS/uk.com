package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.workinfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
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

	/** 日別実績の勤務情報 */
	private Map<GeneralDate, WorkInfoOfDailyPerformance> workInfoOfDailyMap = new HashMap<>();
	
	/** データ設定 */
	@Override
	public GetWorkInfo setData(String employeeId, DatePeriod period) {
		for (val workInfoOfDaily : this.workInformationOfDaily.findByPeriodOrderByYmd(employeeId, period)){
			val ymd = workInfoOfDaily.getYmd();
			this.workInfoOfDailyMap.putIfAbsent(ymd, workInfoOfDaily);
		}
		return this;
	}
	
	/** 実績の勤務情報を取得する */
	@Override
	public Optional<WorkInformation> getRecord(GeneralDate ymd) {
		if (!this.workInfoOfDailyMap.containsKey(ymd)) return Optional.empty();
		
		WorkInformation record = this.workInfoOfDailyMap.get(ymd).getRecordInfo();
		if (record == null) return Optional.empty();
		
		String workTypeCd = "NON";
		String siftCd = "NON";
		if (record.getWorkTypeCode() != null) workTypeCd = record.getWorkTypeCode().v();
		if (record.getSiftCode() != null) siftCd = record.getSiftCode().v();
		return Optional.of(new WorkInformation(siftCd, workTypeCd));
	}
	
	/** 予定の勤務情報を取得する */
	@Override
	public Optional<WorkInformation> getSchedule(GeneralDate ymd) {
		if (!this.workInfoOfDailyMap.containsKey(ymd)) return Optional.empty();
		
		WorkInformation schedule = this.workInfoOfDailyMap.get(ymd).getScheduleInfo();
		if (schedule == null) return Optional.empty();
		
		String workTypeCd = "NON";
		String siftCd = "NON";
		if (schedule.getWorkTypeCode() != null) workTypeCd = schedule.getWorkTypeCode().v();
		if (schedule.getSiftCode() != null) siftCd = schedule.getSiftCode().v();
		return Optional.of(new WorkInformation(siftCd, workTypeCd));
	}
}
