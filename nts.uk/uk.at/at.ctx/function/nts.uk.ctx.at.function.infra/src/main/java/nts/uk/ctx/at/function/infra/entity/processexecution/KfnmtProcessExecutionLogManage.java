package nts.uk.ctx.at.function.infra.entity.processexecution;

//import java.io.Serializable;
//import java.util.List;
import java.util.Optional;
//import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.CurrentExecutionStatus;
//import nts.uk.ctx.at.function.dom.processexecution.executionlog.EachProcessPeriod;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.EndStatus;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.OverallErrorDetail;
//import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogManage;
import nts.uk.shr.com.context.AppContexts;
//import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KFNMT_PRO_EXE_LOG_MANAGE")
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtProcessExecutionLogManage extends UkJpaEntity {
	/* 主キー */
	@EmbeddedId
	public KfnmtProcessExecutionLogManagePK kfnmtProcExecLogPK;

	/** The exclus ver. */
	@Version
	@Column(name = "EXCLUS_VER")
	private Long exclusVer;

	/** The Contract Code. */
	@Column(name = "CONTRACT_CD")
	public String contractCode;

	/* 現在の実行状態 */
	@Basic(optional = true)
	@Column(name = "CURRENT_STATUS")
	public Integer currentStatus;

	/* 全体の終了状態 */
	@Basic(optional = true)
	@Column(name = "OVERALL_STATUS")
	public Integer overallStatus;

	/* 全体のエラー詳細 */
	@Basic(optional = true)
	@Column(name = "ERROR_DETAIL")
	public Integer errorDetail;

	/* 前回実行日時 */
	@Column(name = "LAST_EXEC_DATETIME")
	public GeneralDateTime lastExecDateTime;

	/* 前回実行日時（即時実行含めない） */
	@Column(name = "LAST_EXEC_DATETIME_EX")
	public GeneralDateTime prevExecDateTimeEx;

	@Column(name = "LAST_END_EXEC_DATETIME")
	public GeneralDateTime lastEndExecDateTime;

	@Basic(optional = true)
	@Column(name = "ERROR_SYSTEM")
	public Integer errorSystem;

	@Basic(optional = true)
	@Column(name = "ERROR_BUSINESS")
	public Integer errorBusiness;

	@Override
	protected Object getKey() {
		return this.kfnmtProcExecLogPK;
	}

	public static KfnmtProcessExecutionLogManage toEntity(ProcessExecutionLogManage domain) {
		try {	
			return new KfnmtProcessExecutionLogManage(
					new KfnmtProcessExecutionLogManagePK(domain.getCompanyId(), domain.getExecItemCd().v()),
					domain.getVersion(), AppContexts.user().contractCode(),
					domain.getCurrentStatus().map(item -> item.value).orElse(null),
					domain.getOverallStatus().map(item -> item.value).orElse(null),
					domain.getOverallError().map(item -> item.value).orElse(null),
					domain.getLastExecDateTime().orElse(null), 
					domain.getLastExecDateTimeEx().orElse(null),
					domain.getLastEndExecDateTime().orElse(null),
					domain.getErrorSystem().map(item -> item ? 1 : 0).orElse(null),
					domain.getErrorBusiness().map(item -> item ? 1 : 0).orElse(null));
		} catch (Exception e) {
			return null;
		}

	}

	public ProcessExecutionLogManage toDomain() {
		return new ProcessExecutionLogManage(new ExecutionCode(this.kfnmtProcExecLogPK.execItemCd),
				this.kfnmtProcExecLogPK.companyId,
				Optional.ofNullable(this.errorDetail).map(item -> EnumAdaptor.valueOf(item, OverallErrorDetail.class)),
				Optional.ofNullable(this.overallStatus).map(item -> EnumAdaptor.valueOf(item, EndStatus.class)),
				Optional.ofNullable(this.lastExecDateTime), 
				Optional.ofNullable(this.currentStatus).map(item -> EnumAdaptor.valueOf(item, CurrentExecutionStatus.class)),
				Optional.ofNullable(this.prevExecDateTimeEx),
				Optional.ofNullable(this.lastEndExecDateTime),
				Optional.ofNullable(this.errorSystem).map(item -> item == 1),
				Optional.ofNullable(this.errorBusiness).map(item -> item == 1));
	}

}
