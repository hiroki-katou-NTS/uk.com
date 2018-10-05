package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.BreakType;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.OutingTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ReflectWorkInforDomainService;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.requestlist.PrevisionalForImp;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.shorttimework.ShortWorkingTimeSheet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workinformation.enums.NotUseAttribute;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkTimes;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;

/**
 * 日別実績の仮計算(申請・スケからの窓口)
 * 
 * @author keisuke_hoshina
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class PrevisionalCalculationServiceImpl implements ProvisionalCalculationService {

	@Inject
	private CalculateDailyRecordServiceCenter calculateDailyRecordServiceCenter;

	@Inject
	private AttendanceTimeRepository attendanceTimeRepository;

	@Inject
	private BreakTimeOfDailyPerformanceRepository breakTimeOfDailyPerformanceRepository;

	@Inject
	private OutingTimeOfDailyPerformanceRepository outingTimeOfDailyPerformanceRepository;

	@Inject
	private ReflectWorkInforDomainService reflectWorkInforDomainServiceImpl;

	@Override
	public List<IntegrationOfDaily> calculation(List<PrevisionalForImp> impList) {
		return calculationPassCompanyCommonSetting(impList, Optional.empty());
	}
	
	@Override
	public List<IntegrationOfDaily> calculationPassCompanyCommonSetting(List<PrevisionalForImp> impList,Optional<ManagePerCompanySet> companySetting){
		List<IntegrationOfDaily> integraionList = new ArrayList<>();
		for(PrevisionalForImp imp:impList) {
			if (imp.getWorkTypeCode() == null)
				return Collections.emptyList();
			//疑似的な日別実績を作成
			val provisionalRecord = createProvisionalDailyRecord(imp.getEmployeeId(), imp.getTargetDate(), imp.getWorkTypeCode(), imp.getWorkTimeCode(),
					imp.getTimeSheets());
			if (!provisionalRecord.isPresent())
				return Collections.emptyList();
			//控除置き換え
			val provisionalDailyRecord = replaceDeductionTimeSheet(provisionalRecord.get(), imp.getBreakTimeSheets(),
				imp.getOutingTimeSheets(), imp.getShortWorkingTimeSheets(), imp.getEmployeeId(), imp.getTargetDate());
			integraionList.add(provisionalDailyRecord);
		}
		// ドメインモデル「日別実績の勤怠時間」を返す
		return calculateDailyRecordServiceCenter.calculateForSchedule(new CalculateOption(true, true), integraionList, companySetting);
	}

	/**
	 * 疑似的な日別実績を作成
	 */
	private Optional<IntegrationOfDaily> createProvisionalDailyRecord(String employeeId, GeneralDate ymd,
			WorkTypeCode workTypeCode, WorkTimeCode workTimeCode, Map<Integer, TimeZone> timeSheets) {
		// 日別実績の勤務情報
		//Optional<WorkInfoOfDailyPerformance> preworkInformation = workInformationRepository.find(employeeId, ymd);
		String setWorkTimeCode = null;
		if (workTimeCode != null)
			setWorkTimeCode = workTimeCode.v();
		WorkInfoOfDailyPerformance workInformation = new WorkInfoOfDailyPerformance(employeeId,
				new WorkInformation(setWorkTimeCode, workTypeCode.toString()), new WorkInformation(setWorkTimeCode, workTypeCode.toString()), CalculationState.No_Calculated,
				NotUseAttribute.Not_use, NotUseAttribute.Not_use, ymd, Collections.emptyList());
		// 勤怠時間取得
		val attendanceTime = attendanceTimeRepository.find(employeeId, ymd);
		// 日別実績の休憩時間帯
		val breakTimeSheet = breakTimeOfDailyPerformanceRepository.findByKey(employeeId, ymd);
		// 日別実績の外出時間帯
		val goOutTimeSheet = outingTimeOfDailyPerformanceRepository.findByEmployeeIdAndDate(employeeId, ymd);
		// 日別実績の短時間勤務時間帯
		//Optional<ShortTimeOfDailyPerformance> ShortTimeOfDailyPerformance = Optional.empty();
		// 日別実績の臨時出退勤
		// ;
		// 日別実績の出退勤
		List<TimeLeavingWork> timeLeavingWorks = new ArrayList<>();
		for (Map.Entry<Integer, TimeZone> key : timeSheets.entrySet()) {
			WorkStamp attendance = new WorkStamp(key.getValue().getStart(), key.getValue().getStart(),
					new WorkLocationCD("01"), StampSourceInfo.CORRECTION_RECORD_SET);
			WorkStamp leaving = new WorkStamp(key.getValue().getEnd(), key.getValue().getEnd(),
					new WorkLocationCD("01"), StampSourceInfo.CORRECTION_RECORD_SET);
			TimeActualStamp attendanceStamp = new TimeActualStamp(attendance, attendance, key.getKey());
			TimeActualStamp leavingStamp = new TimeActualStamp(leaving, leaving, key.getKey());
			TimeLeavingWork timeLeavingWork = new TimeLeavingWork(new WorkNo(key.getKey()), attendanceStamp,
					leavingStamp);

			timeLeavingWorks.add(timeLeavingWork);
		}
		TimeLeavingOfDailyPerformance timeAttendance = new TimeLeavingOfDailyPerformance(employeeId,
				new WorkTimes(timeSheets.size()), timeLeavingWorks, ymd);

		// 日別実績の計算区分作成
		val calAttrOfDailyPerformance = new CalAttrOfDailyPerformance(employeeId, ymd,
				new AutoCalFlexOvertimeSetting(
						new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS)),
				new AutoCalRaisingSalarySetting(true, true),
				new AutoCalRestTimeSetting(
						new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS),
						new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS)),
				new AutoCalOvertimeSetting(
						new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS),
						new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS),
						new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS),
						new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS),
						new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS),
						new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS)),
				new AutoCalcOfLeaveEarlySetting(true, true),
				new AutoCalcSetOfDivergenceTime(DivergenceTimeAttr.USE));
		// 日別実績の所属情報作成
		// 日別作成側にある日別実績の所属情報を作成している所を呼び出す
		/*-----"01"について  --------*/
		// ↓を使用して帰ってくるクラスにエラーメッセージが格納される場合がある。
		// エラーメッセージは日別計算で使用しないため、empCalAndSumExecLogIDに任意の物を入れている
		val employeeState = reflectWorkInforDomainServiceImpl
				.createAffiliationInforOfDailyPerfor(AppContexts.user().companyId(), employeeId, ymd, "01");
		if (!employeeState.getAffiliationInforOfDailyPerfor().isPresent()) {
			org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
			employeeState.getErrMesInfos().forEach(tc -> { if(tc.getEmployeeID() == "001")
																log.info("所属雇用履歴が存在しません。");
														   if(tc.getEmployeeID() == "002")
															    log.info("所属職場履歴が存在しません。");
														   if(tc.getEmployeeID() == "003")
															    log.info("所属分類履歴が存在しません。");
														   if(tc.getEmployeeID() == "004")
															   log.info("所属職位履歴が存在しません。");
			});
			return Optional.empty();		
		}
		
		// return new IntegrationOfDaily(workInformation, timeAttendance,
		// attendanceTime.get());
		return Optional.of(new IntegrationOfDaily(workInformation, calAttrOfDailyPerformance,
				employeeState.getAffiliationInforOfDailyPerfor().get(), Optional.empty(), Optional.empty(), 
				Collections.emptyList(), goOutTimeSheet, breakTimeSheet, attendanceTime, Optional.empty(), 
				Optional.of(timeAttendance), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), 
				Collections.emptyList(), Optional.empty(), new ArrayList<>()));
	}

	private IntegrationOfDaily replaceDeductionTimeSheet(IntegrationOfDaily provisionalRecord,
			List<BreakTimeSheet> breakTimeSheets, List<OutingTimeSheet> outingTimeSheets,
			List<ShortWorkingTimeSheet> shortWorkingTimeSheets, String employeeId, GeneralDate ymd) {

		provisionalRecord
				.setOutingTime(Optional.of(new OutingTimeOfDailyPerformance(employeeId, ymd, outingTimeSheets)));
		List<BreakTimeOfDailyPerformance> addElement = new ArrayList<>();
		addElement.add(new BreakTimeOfDailyPerformance(employeeId, BreakType.REFER_WORK_TIME, breakTimeSheets, ymd));
		addElement.add(new BreakTimeOfDailyPerformance(employeeId, BreakType.REFER_SCHEDULE, breakTimeSheets, ymd));
		provisionalRecord.setBreakTime(addElement);
		provisionalRecord
				.setShortTime(Optional.of(new ShortTimeOfDailyPerformance(employeeId, shortWorkingTimeSheets, ymd)));

		return provisionalRecord;
	}

}
