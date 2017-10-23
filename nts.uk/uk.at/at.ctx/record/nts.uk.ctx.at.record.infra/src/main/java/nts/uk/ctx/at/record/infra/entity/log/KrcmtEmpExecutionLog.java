package nts.uk.ctx.at.record.infra.entity.log;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.log.aggregateroot.EmpCalAndSumExeLog;
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
public class KrcmtEmpExecutionLog extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtEmpExecutionLogPK krcmtEmpExecutionLogPK;

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
	public List<KrcmtExecutionLog> executionLogs;
	
	@Override
	protected Object getKey() {
		return this.krcmtEmpExecutionLogPK;
	}
	
	public static KrcmtEmpExecutionLog toEntity(EmpCalAndSumExeLog domain) {
		return new  KrcmtEmpExecutionLog(
				new KrcmtEmpExecutionLogPK(
						domain.getCompanyID(),
						domain.getEmpCalAndSumExecLogID(),
						domain.getCaseSpecExeContentID(),
						domain.getEmployeeID()),
				domain.getExecutedMenu().value,
				domain.getExecutionStatus().value,
				domain.getExecutionDate(),
				domain.getProcessingMonth().v(),
				domain.getClosureID(),
				domain.getExecutionLogs().stream().map(c->KrcmtExecutionLog.toEntity(c)).collect(Collectors.toList())
				);
	}
}
