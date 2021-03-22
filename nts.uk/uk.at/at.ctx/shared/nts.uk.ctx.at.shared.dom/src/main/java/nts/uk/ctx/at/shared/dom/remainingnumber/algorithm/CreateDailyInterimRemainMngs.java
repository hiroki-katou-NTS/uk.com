package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.service.RemainNumberCreateInformation;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.CompanyHolidayMngSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;

public class CreateDailyInterimRemainMngs {

	/**
	 * Workを考慮した月次処理用の暫定残数管理データを作成する
	 */
	public static Map<GeneralDate, DailyInterimRemainMngData> createDailyInterimRemainMngs(Require require, CacheCarrier cacheCarrier, 
			String cid, String sid, DatePeriod period, List<IntegrationOfDaily> dailyRecord, Optional<ComSubstVacation> absSettingOpt,
			CompensatoryLeaveComSetting dayOffSetting) {

		val attendanceTimeMap = dailyRecord.stream().filter(c -> c.getAttendanceTimeOfDailyPerformance().isPresent())
				.collect(Collectors.toMap(c -> c.getYmd(), c -> c.getAttendanceTimeOfDailyPerformance().get()));
		val workInfoMap = dailyRecord.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getWorkInformation()));

		/** 残数作成元情報(実績)を作成する */
		List<RecordRemainCreateInfor> recordRemains = RemainNumberCreateInformation.createRemainInfor(
				sid, attendanceTimeMap, workInfoMap);

		InterimRemainCreateDataInputPara inputPara = new InterimRemainCreateDataInputPara(cid,
				sid, period, recordRemains, Collections.emptyList(), Collections.emptyList(), false);
		CompanyHolidayMngSetting comHolidaySetting = new CompanyHolidayMngSetting(cid, absSettingOpt, dayOffSetting);
		
		/** 指定期間の暫定残数管理データを作成する */
		return InterimRemainOffPeriodCreateData.createInterimRemainDataMng(require, cacheCarrier,
				inputPara, comHolidaySetting);
	}
	
	public static interface Require extends InterimRemainOffPeriodCreateData.RequireM4 {}
}
