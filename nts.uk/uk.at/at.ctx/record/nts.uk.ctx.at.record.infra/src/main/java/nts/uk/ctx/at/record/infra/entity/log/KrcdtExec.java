package nts.uk.ctx.at.record.infra.entity.log;

import java.io.Serializable;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
//import javax.persistence.JoinTable;
//import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
//import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
//import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt
 * 就業計算と集計実行ログ
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_EXEC")
public class KrcdtExec extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtEmpExecutionLogPK krcdtEmpExecutionLogPK;
	
	@Column(name = "CID")
	public String companyID;
	
	@Column(name = "PROCESSING_MONTH")
	public Integer processingMonth;
	
	@Column(name = "EXECUTED_DATE")
	public GeneralDateTime executedDate;

	@Column(name = "EXECUTED_STATUS")
	public Integer executedStatus;

	@Column(name = "SID")
	public String employeeID;

	@Column(name = "CLOSURE_ID")
	public int closureID;
	
	@Column(name = "CAL_AGG_CLASS")
	public int executionClassification;

//	@OneToMany(mappedBy="empexecutionlog", cascade = CascadeType.ALL)
//	@JoinTable(name = "KRCDT_EXEC_LOG")
//	public List<KrcdtExecLog> executionLogs;
	
	@Override
	protected Object getKey() {
		return this.krcdtEmpExecutionLogPK;
	}
	
	public static KrcdtExec toEntity(EmpCalAndSumExeLog domain) {
		return new  KrcdtExec(
				new KrcdtEmpExecutionLogPK(domain.getEmpCalAndSumExecLogID()),
				domain.getCompanyID(),
				domain.getProcessingMonth().v(),
				domain.getExecutionDate(),
			(domain.getExecutionStatus()!=null&& domain.getExecutionStatus().isPresent())?domain.getExecutionStatus().get().value:null,
				domain.getEmployeeID(),
				domain.getClosureID(),
				domain.getExecutionClassification().value
				//domain.getExecutionLogs().stream().map(c->KrcdtExecLog.toEntity(c)).collect(Collectors.toList())
				);
	}
	
	public EmpCalAndSumExeLog toDomain() {
		//List<ExecutionLog> executionLogs = this.executionLogs.stream().map(c -> c.toDomain()).collect(Collectors.toList());
		return EmpCalAndSumExeLog.createFromJavaType(
				this.krcdtEmpExecutionLogPK.empCalAndSumExecLogID,
				this.companyID,
				new YearMonth( this.processingMonth),
				this.executedDate, 
				this.executedStatus,
				this.employeeID,
				this.closureID, 
				this.executionClassification);
	}
}
