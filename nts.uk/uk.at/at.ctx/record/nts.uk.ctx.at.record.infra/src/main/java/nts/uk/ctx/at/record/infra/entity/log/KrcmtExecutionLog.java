package nts.uk.ctx.at.record.infra.entity.log;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.log.ExecutionLog;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 * 実行ログ
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_EMP_EXECUTION_LOG")
public class KrcmtExecutionLog extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtExecutionLogPK krcmtExecutionLogPK;
	/**
	 * エラーの有無
	 */
	@Column(name = "EXISTENCE_ERROR")
	public int existenceError;

	@Column(name = "EXECUTE_CONTENT_BY_CASE_ID")
	public int executeContenByCaseID;

	@Column(name = "EXECUTION_CONTENT")
	public int executionContent;

	@Column(name = "EXECUTION_START_DATE")
	public GeneralDate executionStartDate;
	
	@Column(name = "EXECUTION_END_DATE")
	public GeneralDate executionEndDate;

	@Column(name = "PROCESSING_SITUATION")
	public int processStatus;

	@Column(name = "SETTING_INFORMATION_TYPE")
	public int settingInfoType;

	@Column(name = "SETTING_INFORMATION_CONTENT")
	public int settingInfoContent;

	@Column(name = "PERIOD_COVERED_START_DATE")
	public GeneralDate periodCoverdStartDate;

	@Column(name = "PERIOD_COVERED_END_DATE")
	public GeneralDate periodCoverdEndDate;
	
	@ManyToOne
	@JoinColumns({
        @JoinColumn(name="EMP_EXECUTION_LOG_ID", referencedColumnName="EMP_EXECUTION_LOG_ID", insertable = false, updatable = false)
    })
	public KrcmtEmpExecutionLog executionlog;

	@Override
	protected Object getKey() {
		return this.krcmtExecutionLogPK;
	}
	
	public static KrcmtExecutionLog toEntity(ExecutionLog domain) {
		return new KrcmtExecutionLog(
				 new KrcmtExecutionLogPK(
				     domain.getExecutedLogID(),
					 domain.getEmpCalAndSumExecLogID()
					),
				 domain.getExistenceError().value,
				 domain.getExecuteContenByCaseID(),
				 domain.getExecutionContent().value,
				 domain.getExecutionTime().getStartTime(),
				 domain.getExecutionTime().getEndTime(),
				 domain.getProcessStatus().value,
				 domain.getCalExeSetInfor().getExecutionType().value,
				 domain.getCalExeSetInfor().getExecutionContent().value,
				 domain.getObjectPeriod().getStartDate(),
				 domain.getObjectPeriod().getEndDate(),
				 null);
	}

}
