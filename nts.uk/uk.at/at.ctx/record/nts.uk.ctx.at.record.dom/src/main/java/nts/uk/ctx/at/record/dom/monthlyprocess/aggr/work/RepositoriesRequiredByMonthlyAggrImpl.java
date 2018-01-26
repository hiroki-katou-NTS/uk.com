package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.Getter;
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
 * 月次集計が必要とするリポジトリ実装
 * @author shuichu_ishida
 */
@Stateless
@Getter
public class RepositoriesRequiredByMonthlyAggrImpl implements RepositoriesRequiredByMonthlyAggr {

	/** 日別実績の勤怠時間の取得 */
	@Inject
	public AttendanceTimeRepository attendanceTimeOfDaily;
	
	/** 日別実績の勤務情報の取得 */
	@Inject
	public WorkInformationRepository workInformationOfDaily;

	/** 日別実績の出退勤の取得 */
	@Inject
	public TimeLeavingOfDailyPerformanceRepository timeLeavingOfDaily;
	
	/** 日別実績の特定日区分の取得 */
	@Inject
	public SpecificDateAttrOfDailyPerforRepo specificDateAtrOfDaily;
	
	/** 勤務情報の取得 */
	@Inject
	public WorkTypeRepository workType;
	
	/** 月別実績の勤怠時間の取得 */
	@Inject
	public AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthly;
	
	/** 月別実績集計設定の取得 */
	@Inject
	public GetAggrSettingMonthly aggrSettingMonthly;

	/** 月次集計の法定内振替順設定の取得 */
	@Inject
	public LegalTransferOrderSetOfAggrMonthlyRepository legalTransferOrderSetOfAggrMonthly;
	
	/** 法定労働時間の取得 */
	@Inject
	public GetOfStatutoryWorkTime getOfStatutoryWorkTime;
	
	/** 休日加算設定 */
	@Inject
	public HolidayAddtionRepository holidayAddition;
	
	/** 代休時間設定の取得 */
	//@Inject
	//public CompensatoryOccurrenceSettingGetMemento compensatoryOccurrenceSet;
	
	/** 回数集計の取得 */
	@Inject
	public TotalTimesRepository totalTimes;
}
