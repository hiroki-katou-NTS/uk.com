package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthlyaggrmethod.GetAggrSettingMonthly;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSettingGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.GetOfStatutoryWorkTime;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * 月次集計が必要とするリポジトリ実装
 * @author shuichu_ishida
 */
@Stateless
public class RepositoriesRequiredByMonthlyAggrImpl implements RepositoriesRequiredByMonthlyAggr {

	/** 月別実績集計設定の取得 */
	@Inject
	@Getter
	public GetAggrSettingMonthly aggrSettingMonthly;
	
	/** 日別実績の勤怠時間の取得 */
	@Inject
	@Getter
	public AttendanceTimeRepository attendanceTimeOfDaily;
	
	/** 日別実績の勤務情報の取得 */
	@Inject
	@Getter
	public WorkInformationRepository workInformationOfDaily;

	/** 勤務情報の取得 */
	@Inject
	@Getter
	public WorkTypeRepository workType;
	
	/** 法定労働時間の取得 */
	@Inject
	@Getter
	public GetOfStatutoryWorkTime getOfStatutoryWorkTime;
	
	/** 代休時間設定の取得 */
	@Inject
	@Getter
	public CompensatoryOccurrenceSettingGetMemento compensatoryOccurrenceSet;
	
	/** 月別実績の勤怠時間の取得 */
	@Inject
	@Getter
	public AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthly;
}
