package nts.uk.ctx.at.record.infra.entity.log;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt
 * 実行ログ
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_EXEC_LOG")
public class KrcdtExecLog extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtExecutionLogPK krcdtExecutionLogPK;
	/**
	 * エラーの有無
	 */
	@Column(name = "EXISTENCE_ERROR")
	public int existenceError;

	@Column(name = "EXECUTION_START_DATE")
	public GeneralDateTime executionStartDate;
	
	@Column(name = "EXECUTION_END_DATE")
	public GeneralDateTime executionEndDate;

	@Column(name = "PROCESSING_SITUATION")
	public int processStatus;

	@Column(name = "PERIOD_COVERED_START_DATE")
	public GeneralDate periodCoverdStartDate;

	@Column(name = "PERIOD_COVERED_END_DATE")
	public GeneralDate periodCoverdEndDate;
	
    @Column(name = "CAN_CALCULATE_WHEN_LOCK")
    public Integer isCalWhenLock;
    
    /**
     * 実行種別
     */
    @NotNull
    @Column(name = "EXECUTION_TYPE")
    public Integer executionType;
	
	public KrcdtExecLog(KrcdtExecutionLogPK krcdtExecutionLogPK, int existenceError,
			GeneralDateTime executionStartDate, GeneralDateTime executionEndDate, int processStatus,
            GeneralDate periodCoverdStartDate, GeneralDate periodCoverdEndDate, Integer isCalWhenLock,
			Integer executionType) {
		super();
		this.krcdtExecutionLogPK = krcdtExecutionLogPK;
		this.existenceError = existenceError;
		this.executionStartDate = executionStartDate;
		this.executionEndDate = executionEndDate;
		this.processStatus = processStatus;
		this.periodCoverdStartDate = periodCoverdStartDate;
		this.periodCoverdEndDate = periodCoverdEndDate;
        this.isCalWhenLock = isCalWhenLock;
        this.executionType = executionType;
	}
	
	public KrcdtExecLog(KrcdtExecutionLogPK krcdtExecutionLogPK, int existenceError,
			GeneralDateTime executionStartDate, GeneralDateTime executionEndDate, int processStatus,
			GeneralDate periodCoverdStartDate, GeneralDate periodCoverdEndDate) {
		super();
		this.krcdtExecutionLogPK = krcdtExecutionLogPK;
		this.existenceError = existenceError;
		this.executionStartDate = executionStartDate;
		this.executionEndDate = executionEndDate;
		this.processStatus = processStatus;
		this.periodCoverdStartDate = periodCoverdStartDate;
		this.periodCoverdEndDate = periodCoverdEndDate;
	}

	public ExecutionLog toDomain() {
		val domain = ExecutionLog.createFromJavaType(
				this.krcdtExecutionLogPK.empCalAndSumExecLogID,
				this.krcdtExecutionLogPK.executionContent, 
				this.existenceError, 
				this.executionStartDate,
				this.executionEndDate, 
				this.processStatus, 
				this.periodCoverdStartDate, 
				this.periodCoverdEndDate,
                Optional.ofNullable(this.isCalWhenLock == null ? null : this.isCalWhenLock== 1),
                this.executionType);

		return domain;
	}
	
	@Override
	protected Object getKey() {
		return this.krcdtExecutionLogPK;
	}
	
	public static KrcdtExecLog toEntity(ExecutionLog domain) {
		val entity = new KrcdtExecLog(
				 new KrcdtExecutionLogPK(
					 domain.getEmpCalAndSumExecLogID(),
					 domain.getExecutionContent().value
					),
				 domain.getExistenceError().value,
				 domain.getExecutionTime().getStartTime(),
				 domain.getExecutionTime().getEndTime(),
				 domain.getProcessStatus().value,
				(domain.getObjectPeriod()!=null&&domain.getObjectPeriod().isPresent())?domain.getObjectPeriod().get().getStartDate():null,
				(domain.getObjectPeriod()!=null&&domain.getObjectPeriod().isPresent())?domain.getObjectPeriod().get().getEndDate():null,
                 domain.getIsCalWhenLock().map(c -> c ? 1 : 0).orElse(0),
                 domain.getExecutionType().value);
		return entity;
	}
}
