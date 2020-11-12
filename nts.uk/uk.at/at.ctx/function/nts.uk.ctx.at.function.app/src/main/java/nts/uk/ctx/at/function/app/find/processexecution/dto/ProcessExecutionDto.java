package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.List;
import java.util.stream.Collectors;

//import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.processexecution.AlarmExtraction;
import nts.uk.ctx.at.function.dom.processexecution.AppRouteUpdateDaily;
import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScope;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScopeItem;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionSetting;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.DailyPerformanceCreation;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreation;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreationPeriod;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreationTarget;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetClassification;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.TargetSetting;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
//import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.TargetGroupClassification;
import nts.uk.shr.com.time.calendar.MonthDay;

@Data
@Builder
@AllArgsConstructor
public class ProcessExecutionDto {
	
	/* 会社ID */
	private String companyId;
	
	/* コード */
	private String execItemCd;
	
	/* 名称 */
	private String execItemName;
	
	/* 個人スケジュール作成 */
	private boolean perScheduleCls;
	
	/* 対象月 */
	private int targetMonth;
	
	/* 対象日 */
	private Integer targetDate;
	
	/* 作成期間 */
	private Integer creationPeriod;
	
	/* 作成対象 */
	private int creationTarget;
	
	/* 勤務種別変更者を再作成 */
	private boolean recreateWorkType;
	
	/* 新入社員を作成する */
	private boolean createEmployee;
	
	/* 異動者を再作成する */
	private boolean recreateTransfer;
	
	/* 日別実績の作成・計算 */
	private boolean dailyPerfCls;
	
	/* 作成・計算項目 */
	private int dailyPerfItem;
	
	/* 途中入社は入社日からにする */
	private boolean midJoinEmployee;
	
	/* 承認結果反映 */
	private boolean reflectResultCls;
	
	/* 月別集計 */
	private boolean monthlyAggCls;

	private int execScopeCls;

	private GeneralDate refDate;
	
    private List<String> workplaceList;
    
    /* 更新処理の日別処理対象者区分.勤務種別変更者を再作成 */
	public boolean recreateTypeChangePerson;
	
	/* 更新処理の日別処理対象者区分.異動者を再作成する */
	public boolean recreateTransfers;
	
	/** 承認ルート更新区分 */
	private boolean appRouteUpdateAtr;
	
	/** 新入社員を作成する */
	private Boolean createNewEmp;
	
	/* 承認ルート更新（月次） */
	private boolean appRouteUpdateMonthly;
	
	/* 実行種別*/
	private int processExecType;
	
	private boolean alarmAtr;
	
	private String alarmCode;
	
	private Boolean mailPrincipal;
	
	private Boolean mailAdministrator;
	
	/* 指定年 */
	private Integer designatedYear;
	
	/* 指定開始月日 */
	private Integer startMonthDay;
	
	/* 指定終了月日*/
	private Integer endMonthDay;
	
	/* クラウド作成フラグ  */
	private Boolean cloudCreationFlag;
	
	public ProcessExecutionDto() {
		super();
	}
	
	public static ProcessExecutionDto fromDomain(UpdateProcessAutoExecution domain) {
		List<String> workplaceList = domain.getExecScope().getWorkplaceIdList().stream()
				.map(ProcessExecutionScopeItem::getWkpId)
				.collect(Collectors.toList());
		ProcessExecutionDtoBuilder builder = ProcessExecutionDto.builder()
				.companyId(domain.getCompanyId())
				.execItemCd(domain.getExecItemCode().v())
				.execItemName(domain.getExecItemName().v())
				.workplaceList(workplaceList)
				.processExecType(domain.getExecutionType().value)
				.cloudCreationFlag(domain.getCloudCreationFlag());
		ProcessExecutionSetting execSetting = domain.getExecSetting();
		if (execSetting != null) {
			builder = builder
					.reflectResultCls(execSetting.isReflectResultCls())
					.monthlyAggCls(execSetting.isMonthlyAggCls())
					.appRouteUpdateMonthly(execSetting.getAppRouteUpdateMonthly().equals(NotUseAtr.USE));
			PersonalScheduleCreation perSchedule = execSetting.getPerScheduleCreation();
			if (perSchedule != null) {
				builder = builder
						.perScheduleCls(perSchedule.isPerSchedule());
				PersonalScheduleCreationPeriod period = perSchedule.getPerSchedulePeriod();
				if (period != null) {
					builder = builder
							.targetMonth(period.getTargetMonth().value)
							.targetDate(period.getTargetDate().v())
							.creationPeriod(period.getCreationPeriod().v())
							.designatedYear(period.getDesignatedYear()
									.map(o -> o.value)
									.orElse(null))
							.startMonthDay(period.getStartMonthDay()
									.map(o -> ProcessExecutionDto.getValueMonthDay(o))
									.orElse(null))
							.endMonthDay(period.getEndMonthDay()
									.map(o -> ProcessExecutionDto.getValueMonthDay(o))
									.orElse(null));
				}
				PersonalScheduleCreationTarget target = perSchedule.getTarget();
				if (target != null) {
					TargetClassification targetClassification = target.getCreationTarget();
					if (targetClassification != null) {
						builder = builder
								.creationTarget(targetClassification.value);
					}
					TargetSetting targetSetting = target.getTargetSetting();
					if (targetSetting != null) {
						builder = builder
								.recreateWorkType(targetSetting.isRecreateWorkType())
								.createEmployee(targetSetting.isCreateEmployee())
								.recreateTransfer(targetSetting.isRecreateTransfer());
					}
				}
			}
			DailyPerformanceCreation dailyPerf = execSetting.getDailyPerf();
			if (dailyPerf != null) {
				builder = builder
						.dailyPerfCls(dailyPerf.isDailyPerfCls())
						.dailyPerfItem(dailyPerf.getDailyPerfItem().value)
						.midJoinEmployee(dailyPerf.getTargetGroupClassification().isMidJoinEmployee())
						.recreateTypeChangePerson(dailyPerf.getTargetGroupClassification().isRecreateTypeChangePerson())
						.recreateTransfers(dailyPerf.getTargetGroupClassification().isRecreateTransfer());
			}
			AppRouteUpdateDaily appRouteUpdateDaily = execSetting.getAppRouteUpdateDaily();
			if (appRouteUpdateDaily != null) {
				builder = builder
						.appRouteUpdateAtr(appRouteUpdateDaily.getAppRouteUpdateAtr().equals(NotUseAtr.USE))
						.createNewEmp(appRouteUpdateDaily.getCreateNewEmpApp()
								.map(o -> o.equals(NotUseAtr.USE))
								.orElse(null));
			}
			AlarmExtraction alarmExtraction = execSetting.getAlarmExtraction();
			if (alarmExtraction != null) {
				builder = builder
						.alarmAtr(alarmExtraction.isAlarmAtr())
						.alarmCode(alarmExtraction.getAlarmCode()
								.map(o -> o.v())
								.orElse(null))
						.mailPrincipal(alarmExtraction.getMailPrincipal()
								.map(o -> o.booleanValue())
								.orElse(null))
						.mailAdministrator(alarmExtraction.getMailAdministrator()
								.map(o -> o.booleanValue())
								.orElse(null));
			}
		}
		ProcessExecutionScope execScope = domain.getExecScope();
		if (execScope != null) {
			builder = builder
					.execScopeCls(execScope.getExecScopeCls().value)
					.refDate(execScope.getRefDate());
		}
		
		return builder.build();
	}
	
	private static Integer getValueMonthDay(MonthDay monthDay) {
		return monthDay.getDay() + monthDay.getMonth()*100;
	}
}
