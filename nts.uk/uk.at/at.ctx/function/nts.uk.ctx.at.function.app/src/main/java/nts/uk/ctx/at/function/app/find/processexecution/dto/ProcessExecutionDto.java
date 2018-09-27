package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecution;
import nts.uk.ctx.at.function.dom.processexecution.dailyperformance.TargetGroupClassification;

@Data
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
	
	/* 手修正を保護する */
	private boolean manualCorrection;
	
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
	
	public ProcessExecutionDto() {
		super();
	}
	public static ProcessExecutionDto fromDomain(ProcessExecution domain) {
		List<String> workplaceList =
				domain.getExecScope().getWorkplaceIdList()
							.stream()
								.map(x -> x.getWkpId()).collect(Collectors.toList());
		return new ProcessExecutionDto(domain.getCompanyId(),
				domain.getExecItemCd().v(), 
				domain.getExecItemName().v(),
				domain.getExecSetting().getPerSchedule().isPerSchedule(),
				domain.getExecSetting().getPerSchedule().getPeriod().getTargetMonth().value,
				domain.getExecSetting().getPerSchedule().getPeriod().getTargetDate().v(),
				domain.getExecSetting().getPerSchedule().getPeriod().getCreationPeriod().v(),
				domain.getExecSetting().getPerSchedule().getTarget().getCreationTarget().value,
				domain.getExecSetting().getPerSchedule().getTarget().getTargetSetting().isRecreateWorkType(),
				domain.getExecSetting().getPerSchedule().getTarget().getTargetSetting().isManualCorrection(),
				domain.getExecSetting().getPerSchedule().getTarget().getTargetSetting().isCreateEmployee(),
				domain.getExecSetting().getPerSchedule().getTarget().getTargetSetting().isRecreateTransfer(),
				domain.getExecSetting().getDailyPerf().isDailyPerfCls(),
				domain.getExecSetting().getDailyPerf().getDailyPerfItem().value,
				domain.getExecSetting().getDailyPerf().getTargetGroupClassification().isMidJoinEmployee(),
				domain.getExecSetting().isReflectResultCls(),
				domain.getExecSetting().isMonthlyAggCls(),
				domain.getExecScope().getExecScopeCls().value,
				domain.getExecScope().getRefDate(),
				workplaceList,domain.getExecSetting().getDailyPerf().getTargetGroupClassification().isRecreateTypeChangePerson(),domain.getExecSetting().getDailyPerf().getTargetGroupClassification().isRecreateTransfer(),
				domain.getExecSetting().getAppRouteUpdateDaily().getAppRouteUpdateAtr().value==1?true:false,
				!domain.getExecSetting().getAppRouteUpdateDaily().getCreateNewEmp().isPresent()?null:domain.getExecSetting().getAppRouteUpdateDaily().getCreateNewEmp().get().value==1?true:false,
				domain.getExecSetting().getAppRouteUpdateMonthly().value==1?true:false,
				domain.getProcessExecType().value,
				domain.getExecSetting().getAlarmExtraction().isAlarmAtr(),
				!domain.getExecSetting().getAlarmExtraction().getAlarmCode().isPresent()?null:domain.getExecSetting().getAlarmExtraction().getAlarmCode().get().v(),
				!domain.getExecSetting().getAlarmExtraction().getMailPrincipal().isPresent()?null:domain.getExecSetting().getAlarmExtraction().getMailPrincipal().get().booleanValue(),
				!domain.getExecSetting().getAlarmExtraction().getMailAdministrator().isPresent()?null:domain.getExecSetting().getAlarmExtraction().getMailAdministrator().get().booleanValue()
				);
	}
}
