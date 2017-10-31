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
import nts.arc.time.GeneralDateTime;
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
@Table(name = "KRCMT_EXECUTION_LOG")
public class KrcdtExecutionLog extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtExecutionLogPK krcdtExecutionLogPK;
	/**
	 * エラーの有無
	 */
	@Column(name = "EXISTENCE_ERROR")
	public int existenceError;

	@Column(name = "EXECUTION_CONTENT")
	public int executionContent;

	@Column(name = "EXECUTION_START_DATE")
	public GeneralDateTime executionStartDate;
	
	@Column(name = "EXECUTION_END_DATE")
	public GeneralDateTime executionEndDate;

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
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="EMP_EXECUTION_LOG_ID", referencedColumnName="EMP_EXECUTION_LOG_ID", insertable = false, updatable = false),
		@JoinColumn(name="OPERATION_CASE_ID", referencedColumnName="OPERATION_CASE_ID", insertable = false, updatable = false),
        @JoinColumn(name="SID", referencedColumnName="SID", insertable = false, updatable = false)
    })
	public KrcdtEmpExecutionLog executionlog;

	@Override
	protected Object getKey() {
		return this.krcdtExecutionLogPK;
	}
	
	public static KrcdtExecutionLog toEntity(ExecutionLog domain) {
		return new KrcdtExecutionLog(
				 new KrcdtExecutionLogPK(
					 domain.getCompanyID(),
					 String.valueOf(domain.getEmpCalAndSumExecLogID()),
					 String.valueOf(domain.getCaseSpecExeContentID()),
					 domain.getEmployeeID(),
				     domain.getExecutedLogID(),
					 domain.getExecuteContenByCaseID()
					),
				 domain.getExistenceError().value,
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
	
	public ExecutionLog toDomain() {
		return ExecutionLog.createFromJavaType(
				this.krcdtExecutionLogPK.companyID, 
				this.krcdtExecutionLogPK.empCalAndSumExecLogID, 
				this.krcdtExecutionLogPK.caseSpecExeContentID, 
				this.krcdtExecutionLogPK.employeeID, 
				this.krcdtExecutionLogPK.executedLogID, 
				this.krcdtExecutionLogPK.executeContenByCaseID, 
				this.existenceError, 
				this.executionContent, 
				this.executionStartDate,
				this.executionEndDate, 
				this.processStatus, 
				this.settingInfoType, 
				this.settingInfoContent, 
				this.periodCoverdStartDate, 
				this.periodCoverdEndDate);
	}

}
