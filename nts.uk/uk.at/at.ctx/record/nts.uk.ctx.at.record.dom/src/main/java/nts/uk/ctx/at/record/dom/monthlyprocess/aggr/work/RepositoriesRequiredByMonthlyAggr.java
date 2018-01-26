package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.GetAggrSettingMonthly;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.legaltransferorder.LegalTransferOrderSetOfAggrMonthlyRepository;
import nts.uk.ctx.at.record.dom.raisesalarytime.repo.SpecificDateAttrOfDailyPerforRepo;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtionRepository;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworktime.GetOfStatutoryWorkTime;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * 月次集計が必要とするリポジトリ
 * @author shuichu_ishida
 */
public interface RepositoriesRequiredByMonthlyAggr {

	/** 日別実績の勤怠時間の取得 */
	AttendanceTimeRepository getAttendanceTimeOfDaily();
	
	/** 日別実績の勤務情報の取得 */
	WorkInformationRepository getWorkInformationOfDaily();

	/** 日別実績の出退勤の取得 */
	TimeLeavingOfDailyPerformanceRepository getTimeLeavingOfDaily();

	/** 日別実績の特定日区分の取得 */
	SpecificDateAttrOfDailyPerforRepo getSpecificDateAtrOfDaily();
	
	/** 勤務情報の取得 */
	WorkTypeRepository getWorkType();

	/** 月別実績の勤怠時間の取得 */
	AttendanceTimeOfMonthlyRepository getAttendanceTimeOfMonthly();
	
	/** 月別実績集計設定の取得 */
	GetAggrSettingMonthly getAggrSettingMonthly();

	/** 月次集計の法定内振替順設定の取得 */
	LegalTransferOrderSetOfAggrMonthlyRepository getLegalTransferOrderSetOfAggrMonthly();
	
	/** 法定労働時間の取得 */
	GetOfStatutoryWorkTime getGetOfStatutoryWorkTime();
	
	/** 休日加算設定 */
	HolidayAddtionRepository getHolidayAddition();
	
	/** 代休時間設定の取得 */
	//CompensatoryOccurrenceSettingGetMemento getCompensatoryOccurrenceSet();
	
	/** 回数集計の取得 */
	TotalTimesRepository getTotalTimes();
}
