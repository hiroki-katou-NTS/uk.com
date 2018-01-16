package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.GetAggrSettingMonthly;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworkime.GetOfStatutoryWorkTime;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * 月次集計が必要とするリポジトリ
 * @author shuichu_ishida
 */
public interface RepositoriesRequiredByMonthlyAggr {

	/** 月別実績集計設定の取得 */
	GetAggrSettingMonthly getAggrSettingMonthly();
	
	/** 日別実績の勤怠時間の取得 */
	AttendanceTimeRepository getAttendanceTimeOfDaily();
	
	/** 日別実績の勤務情報の取得 */
	WorkInformationRepository getWorkInformationOfDaily();

	/** 勤務情報の取得 */
	WorkTypeRepository getWorkType();
	
	/** 法定労働時間の取得 */
	GetOfStatutoryWorkTime getGetOfStatutoryWorkTime();
	
	/** 代休時間設定の取得 */
	//CompensatoryOccurrenceSettingGetMemento getCompensatoryOccurrenceSet();

	/** 月別実績の勤怠時間の取得 */
	AttendanceTimeOfMonthlyRepository getAttendanceTimeOfMonthly();
}
