package nts.uk.ctx.at.record.infra.entity.monthlyclosureupdatelog;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLog;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author HungTT - 月締め更新対象者ログ
 *
 */

@NoArgsConstructor
@Entity
@Table(name = "KRCDT_MCLOSE_TARGET")
public class KrcdtMcloseTarget extends ContractUkJpaEntity {

	@EmbeddedId
	public KrcdtMcloseTargetPk pk;

	// 実行結果
	@Column(name = "EXECUTE_RESULT")
	public int executionResult;

	// 実行状況
	@Column(name = "EXECUTE_STATUS")
	public int executionStatus;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KrcdtMcloseTarget(String employeeId, String monthlyClosureUpdateLogId, int executionResult,
			int executionStatus) {
		super();
		this.pk = new KrcdtMcloseTargetPk(employeeId, monthlyClosureUpdateLogId);
		this.executionResult = executionResult;
		this.executionStatus = executionStatus;
	}

	public static KrcdtMcloseTarget fromDomain(MonthlyClosureUpdatePersonLog domain) {
		return new KrcdtMcloseTarget(domain.getEmployeeId(), domain.getMonthlyClosureUpdateLogId(),
				domain.getExecutionResult().value, domain.getExecutionStatus().value);
	}

	public MonthlyClosureUpdatePersonLog toDomain() {
		return new MonthlyClosureUpdatePersonLog(this.pk.employeeId, this.pk.monthlyClosureUpdateLogId,
				this.executionResult, this.executionStatus);
	}
}
