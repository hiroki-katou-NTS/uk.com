package nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.daily;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.daily.fixlogic.CheckNotReflectionStamp;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.record.daily.fixlogic.CheckNotRegistWorkLocation;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.workflow.dom.approverstatemanagement.DailyConfirmAtr;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRootStateStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * 固定のチェック条件(社員別・日次)
 */
@RequiredArgsConstructor
public enum FixedLogicDailyByEmployee {

    勤務種類未登録(1, c -> alarmToIntegrationOfDaily(
            c, (iod) -> !c.require.existsWorkType(iod.getWorkInformation().getRecordInfo().getWorkTypeCode().v()))),

    就業時間帯未登録(2, c -> alarmToIntegrationOfDaily(
            c, (iod) -> !c.require.existsWorkTime(iod.getWorkInformation().getRecordInfo().getWorkTimeCode().v()))),
    
    手入力(3, c -> {        
        return IteratorUtil.iterableFlatten(c.period.datesBetween(), date -> {
           List<EditStateOfDailyAttd> handCorrects = c.require.getIntegrationOfDailyRecord(c.employeeId, date).map(iod -> iod.getEditState()).orElse(new ArrayList<>())
                   .stream().filter(editState -> editState.isHandCorrect()).collect(Collectors.toList());
           return IteratorUtil.iterable(handCorrects, handCorrected -> c.alarm(date, handCorrected.getAttendanceItemId()));
        });
    }),

    未計算(3, c -> alarmToIntegrationOfDaily(
            c, iod -> iod.getWorkInformation().getCalculationState().equals(CalculationState.No_Calculated))),

    管理者未承認(4, c -> {
        val approvalState = c.require.getApprovalRootStateByPeriod(c.employeeId, c.period);
        return () -> approvalState.stream()
                .filter(state -> !state.getDailyConfirmAtr().equals(DailyConfirmAtr.ALREADY_APPROVED))
                .map(state -> c.alarm(state.getDate()))
                .iterator();
    }),
    
    本人未確認(5, c-> alarm(c, date -> c.require.getIdentification(c.employeeId, date).isPresent())),

    加給コード未登録(6, c -> alarmToIntegrationOfDaily(
            c, (iod) -> iod.getAffiliationInfor()
            					  .getBonusPaySettingCode()
            					  .map(bonusPayCode -> !c.require.existsBonusPay(bonusPayCode))
            					  .orElse(true))),
    
    契約時間超過(7, c -> alarmToIntegrationOfDaily(
                c, (iod) -> {
                	val totalTime = iod.getAttendanceTimeOfDailyPerformance()
                		.map(dailyPerformance -> dailyPerformance.getActualWorkingTimeOfDaily().getTotalWorkingTime().getTotalTime())
                		.orElse(new AttendanceTime(0));
                	return c.require.getWorkingConditionItem(iod.getEmployeeId(), iod.getYmd())
											  .map(cItem -> cItem.getContractTime())
											  .map(contractTime -> totalTime.greaterThan(contractTime.v()))
											  .orElse(false);
                })),
    
    契約時間未満(8, c -> alarmToIntegrationOfDaily(
            c, (iod) -> {
            	val totalTime = iod.getAttendanceTimeOfDailyPerformance()
            		.map(dailyPerformance -> dailyPerformance.getActualWorkingTimeOfDaily().getTotalWorkingTime().getTotalTime())
            		.orElse(new AttendanceTime(0));
            	return c.require.getWorkingConditionItem(iod.getEmployeeId(), iod.getYmd())
										  .map(cItem -> cItem.getContractTime())
										  .map(contractTime -> contractTime.greaterThan(totalTime.v()))
										  .orElse(false);
            })),
    特定日出勤(9 , c -> alarmToIntegrationOfDaily(
            c, (iod) -> iod.getSpecDateAttr()
            	.map(specDate -> specDate.getSpecificDateAttrSheets()
            												  .stream()
            												  .anyMatch(sheet -> sheet.getSpecificDateAttr().isUse())
            	)
            	.orElse(false))),
    
    未反映打刻(10, c -> CheckNotReflectionStamp.check(c.getRequire(), c.getEmployeeId(), c.getPeriod())),
    
    未登録場所の打刻(11, c -> CheckNotRegistWorkLocation.check(c.getRequire(), c.getEmployeeId(), c.getPeriod())),
    
    入退門二重打刻(12, c -> alarmToIntegrationOfDaily(
            c, (iod) -> iod.getAttendanceLeavingGate()
            					  .map(stamps -> stamps.getAttendanceLeavingGates().size() > 1)
            					  .orElse(false)
    		)),
    ;

    public final int value;

    private final Function<Context, Iterable<AlarmRecordByEmployee>> logic;

    /**
     * チェックする
     * @param require
     * @param employeeId
     * @param checkingPeriod
     * @param message
     * @return
     */
    public Iterable<AlarmRecordByEmployee> check(
            FixedLogicDailyByEmployee.RequireCheck require,
            String employeeId,
            DatePeriod checkingPeriod,
            AlarmListAlarmMessage message) {
    	
        val context = new Context(require, employeeId, checkingPeriod, message);

        return logic.apply(context);
    }

    private String getName() {
        return "チェック項目名";
    }

    private String getAlarmCondition() {
        return "アラーム条件";
    }

    public Context createContext(RequireCheck require, String employeeId, DatePeriod checkingPeriod, AlarmListAlarmMessage message) {
        return new Context(require, employeeId, checkingPeriod, message);
    }
    
    /**
     * IntegrationOfDaily(日別実績）からアラームを発生させるメソッド
     * @param <T>
     * @param context
     * @param checker
     * @return 
     */
    private static <T> Iterable<AlarmRecordByEmployee> alarmToIntegrationOfDaily(
            Context context,
            Function<IntegrationOfDaily, Boolean> checker) {
        return alarm(context, (date) -> {
            return context.require.getIntegrationOfDailyRecord(context.employeeId, date)
                    .map(iod -> checker.apply(iod)).orElse(false);
        });
    }
    
    /**
     * 日付からアラームを発生させるメソッド
     * @param <T>
     * @param context
     * @param checker
     * @return 
     */
    private static <T> Iterable<AlarmRecordByEmployee> alarm(
            Context context,
            Predicate<GeneralDate> checker) {
        return () -> context.period.stream()
                .filter(checker)
                .map(date -> context.alarm(date))
                .iterator();
    }
    
            
    @Value
    private class Context {
        RequireCheck require;
        String employeeId;
        DatePeriod period;
        AlarmListAlarmMessage message;

        public AlarmRecordByEmployee alarm(GeneralDate date) {
            return new AlarmRecordByEmployee(
                    employeeId,
                    new DateInfo(date),
                    AlarmListCategoryByEmployee.RECORD_DAILY,
                    getName(),
                    getAlarmCondition(),
                    null,
                    message);
        }
        
        public AlarmRecordByEmployee alarm(GeneralDate date, Integer attendanceItemId) {
            return new AlarmRecordByEmployee(
                    employeeId,
                    new DateInfo(date),
                    AlarmListCategoryByEmployee.RECORD_DAILY,
                    require.getDailyAttendanceItemName(attendanceItemId),
                    getAlarmCondition(),
                    "",
                    message);
        }
    }

    public interface RequireCheck extends CheckNotReflectionStamp.Require,
    																 CheckNotRegistWorkLocation.Require{

        Optional<IntegrationOfDaily> getIntegrationOfDailyRecord(String employeeId, GeneralDate date);

        List<ApprovalRootStateStatus> getApprovalRootStateByPeriod(String employeeId, DatePeriod period);
        
        Optional<Identification> getIdentification(String employeeId, GeneralDate date);

        boolean existsWorkType(String workTypeCode);

        boolean existsWorkTime(String workTimeCode);
        
        String getDailyAttendanceItemName(Integer attendanceItemId);
        
        boolean existsBonusPay(BonusPaySettingCode bonusPaySettingCode);
        
        Optional<WorkingConditionItem> getWorkingConditionItem(String employeeId, GeneralDate date);
    }
}
