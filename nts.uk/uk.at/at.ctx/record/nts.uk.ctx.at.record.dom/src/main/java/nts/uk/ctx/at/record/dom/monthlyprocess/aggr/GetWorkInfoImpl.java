package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.workinfo.WorkInfoList;
import nts.uk.ctx.at.shared.dom.scherec.workinfo.GetWorkInfo;

/**
 * 実装：勤務情報を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetWorkInfoImpl implements GetWorkInfo {

	/** 日別実績の勤務情報 */
	@Inject
	WorkInformationRepository workInformationOfDaily;
	
	/** 実績の勤務情報を取得する */
	public Optional<WorkInformation> getRecord(CacheCarrier cacheCarrier, 
			String employeeId, GeneralDate ymd) {
		return initWorkInfoList(cacheCarrier, employeeId, new DatePeriod(ymd, ymd)).getRecord(ymd);
	}
	
	/** 実績の勤務情報を取得する */
	public Map<GeneralDate, WorkInformation> getRecordMap(CacheCarrier cacheCarrier, 
			String employeeId, DatePeriod period) {

		return initWorkInfoList(cacheCarrier, employeeId, period).getRecordMap();
	}
	
	private WorkInfoList initWorkInfoList(CacheCarrier cacheCarrier, 
			String employeeId, DatePeriod period) {
		
		return new WorkInfoList(new RequireM1(cacheCarrier), 
				employeeId, period);
	}
	
	@AllArgsConstructor
	class RequireM1 implements WorkInfoList.RequireM1 {
		
		CacheCarrier cacheCarrier;

		@Override
		public Map<GeneralDate, WorkInfoOfDailyAttendance> dailyWorkInfo(String employeeId, DatePeriod datePeriod) {
			return workInformationOfDaily.findByPeriodOrderByYmd(employeeId, datePeriod)
					.stream().collect(Collectors.toMap(c-> c.getYmd(), c -> c.getWorkInformation()));
		}
	}
}
