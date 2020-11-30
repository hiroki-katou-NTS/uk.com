package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;

/**
 * 更新処理自動実行管理
 */
@Getter
@Builder
@AllArgsConstructor
public class ProcessExecutionLogManage extends AggregateRoot {
	/* コード */
	private ExecutionCode execItemCd;
	
	/* 会社ID */
	private String companyId;
	
	/* 全体のエラー詳細  -> 強制終了の原因 */
	private Optional<OverallErrorDetail> overallError;
	
	/* 全体の終了状態 */
	private Optional<EndStatus> overallStatus;
	
	/* 前回実行日時 */
	private Optional<GeneralDateTime> lastExecDateTime;
	
	/* 現在の実行状態 */
	private Optional<CurrentExecutionStatus> currentStatus;
	
	/* 前回実行日時（即時実行含めない） */
	private Optional<GeneralDateTime> lastExecDateTimeEx;
	
	/* 前回終了日時*/
	private Optional<GeneralDateTime> lastEndExecDateTime;
	
	/* 全体のシステムエラー状態*/
	private Optional<Boolean> errorSystem;
	
	/* 全体の業務エラー状態*/
	private Optional<Boolean> errorBusiness;

	public ProcessExecutionLogManage(ExecutionCode execItemCd, String companyId, EndStatus overallStatus, CurrentExecutionStatus currentStatus) {
		super();
		this.execItemCd = execItemCd;
		this.companyId = companyId;
		this.overallStatus = Optional.ofNullable(overallStatus);
		this.currentStatus = Optional.ofNullable(currentStatus);
		this.overallError = Optional.empty();
		this.lastExecDateTime = Optional.empty();
		this.lastExecDateTimeEx = Optional.empty();
		this.lastEndExecDateTime = Optional.empty();
		this.errorSystem = Optional.empty(); 
		this.errorBusiness = Optional.empty();
	}

	public void setExecItemCd(ExecutionCode execItemCd) {
		this.execItemCd = execItemCd;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setOverallError(OverallErrorDetail overallError) {
		this.overallError = Optional.ofNullable(overallError);
	}

	public void setOverallStatus(EndStatus overallStatus) {
		this.overallStatus = Optional.ofNullable(overallStatus);
	}

	public void setLastExecDateTime(GeneralDateTime lastExecDateTime) {
		this.lastExecDateTime = Optional.ofNullable(lastExecDateTime);
	}
	
	public void setLastEndExecDateTime(GeneralDateTime lastEndExecDateTime) {
		this.lastEndExecDateTime = Optional.ofNullable(lastEndExecDateTime);
	}

	public void setCurrentStatus(CurrentExecutionStatus currentStatus) {
		this.currentStatus = Optional.ofNullable(currentStatus);
	}

	public void setLastExecDateTimeEx(GeneralDateTime lastExecDateTimeEx) {
		this.lastExecDateTimeEx = Optional.ofNullable(lastExecDateTimeEx);
	}

	public void setErrorSystem(Boolean errorSystem) {
		this.errorSystem = Optional.ofNullable(errorSystem);
	}

	public void setErrorBusiness(Boolean errorBusiness) {
		this.errorBusiness = Optional.ofNullable(errorBusiness);
	}
	
}
