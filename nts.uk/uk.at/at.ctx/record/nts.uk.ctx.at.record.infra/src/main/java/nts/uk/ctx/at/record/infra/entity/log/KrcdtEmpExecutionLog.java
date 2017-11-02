package nts.uk.ctx.at.record.infra.entity.log;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt
 * 就業計算と集計実行ログ
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_EMP_EXECUTION_LOG")
public class KrcdtEmpExecutionLog extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtEmpExecutionLogPK krcdtEmpExecutionLogPK;

	@Column(name = "EXECUTED_MENU")
	public int executedMenu;

	@Column(name = "EXECUTED_STATUS")
	public int executedStatus;

	@Column(name = "EXECUTED_DATE")
	public GeneralDate executedDate;

	@Column(name = "PROCESSING_MONTH")
	public Integer processingMonth;

	@Column(name = "CLOSURE_ID")
	public int closureID;

	@OneToMany(mappedBy="executionlog", cascade = CascadeType.ALL)
	@JoinTable(name = "KRCMT_EXECUTION_LOG")
	public List<KrcdtExecutionLog> executionLogs;
	
	@Override
	protected Object getKey() {
		return this.krcdtEmpExecutionLogPK;
	}
	
	public static KrcdtEmpExecutionLog toEntity(EmpCalAndSumExeLog domain) {
		return new  KrcdtEmpExecutionLog(
				new KrcdtEmpExecutionLogPK(
						domain.getCompanyID(),
						String.valueOf(domain.getEmpCalAndSumExecLogID()),
						String.valueOf(domain.getCaseSpecExeContentID()),
						domain.getEmployeeID()),
				domain.getExecutedMenu().value,
				domain.getExecutionStatus().value,
				domain.getExecutionDate(),
				domain.getProcessingMonth().v(),
				domain.getClosureID(),
				domain.getExecutionLogs().stream().map(c->KrcdtExecutionLog.toEntity(c)).collect(Collectors.toList())
				);
	}
	
	public EmpCalAndSumExeLog toDomain() {
		return EmpCalAndSumExeLog.createFromJavaType(
				this.krcdtEmpExecutionLogPK.companyID, 
				this.krcdtEmpExecutionLogPK.empCalAndSumExecLogID, 
				this.krcdtEmpExecutionLogPK.caseSpecExeContentID, 
				this.krcdtEmpExecutionLogPK.employeeID, 
				this.executedMenu, 
				this.executedStatus,
				this.executedDate, 
				this.processingMonth, 
				this.closureID, 
				this.executionLogs.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
	}
}
