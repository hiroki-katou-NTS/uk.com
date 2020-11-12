package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;

/**
 * 更新処理自動実行管理
 * @author TungVD
 *
 */
@Data
public class ProcessExecutionLogManageDto {

	/* コード */
	private String execItemCd;
	
	/* 会社ID */
	private String companyId;
	
	/* 全体のエラー詳細  -> 強制終了の原因 */
	private Integer overallError;
	
	/* 全体の終了状態 */
	private Integer overallStatus;
	
	/* 前回実行日時 */
	private GeneralDateTime lastExecDateTime;
	
	/* 現在の実行状態 */
	private Integer currentStatus;
	
	/* 前回実行日時（即時実行含めない） */
	private GeneralDateTime lastExecDateTimeEx;
	
	/* 前回終了日時*/
	private GeneralDateTime lastEndExecDateTime;
	
	/* 全体のシステムエラー状態*/
	private Boolean errorSystem;
	
	/* 全体の業務エラー状態*/
	private Boolean errorBusiness;
	
	private ProcessExecutionLogManageDto(
			String execItemCd,
			String companyId,
			Integer overallError,
			Integer overallStatus,
			GeneralDateTime lastExecDateTime,
			Integer currentStatus,
			GeneralDateTime lastExecDateTimeEx,
			GeneralDateTime lastEndExecDateTime,
			Boolean errorSystem,
			Boolean errorBusiness) {
		super();
		this.execItemCd = execItemCd;
		this.companyId = companyId;
		this.overallError = overallError;
		this.overallStatus = overallStatus;
		this.lastExecDateTime = lastExecDateTime;
		this.currentStatus = currentStatus;
		this.lastExecDateTimeEx = lastExecDateTimeEx;
		this.lastEndExecDateTime = lastEndExecDateTime;
		this.errorSystem = errorSystem;
		this.errorBusiness = errorBusiness;
	}
	
	public static ProcessExecutionLogManageDto fromDomain(ProcessExecutionLogManage domain) {
		return new ProcessExecutionLogManageDto(
				domain.getExecItemCd().v(),
				domain.getCompanyId(),
				domain.getOverallError().map(item -> item.value).orElse(null),
				domain.getOverallStatus().map(o -> o.value).orElse(null),
				domain.getLastExecDateTime().orElse(null),
				domain.getCurrentStatus().map(item -> item.value).orElse(null),
				domain.getLastExecDateTimeEx().orElse(null),
				domain.getLastEndExecDateTime().orElse(null),
				domain.getErrorSystem().orElse(null), 
				domain.getErrorBusiness().orElse(null));
	}
	
}
