package nts.uk.ctx.at.record.infra.entity.log;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
	public BigDecimal executedMenu;

	@Column(name = "EXECUTED_STATUS")
	public BigDecimal executedStatus;

	@Column(name = "EXECUTED_DATE")
	public BigDecimal executedDate;

	@Column(name = "PROCESSING_MONTH")
	public BigDecimal processingMonth;

	@Column(name = "CLOSURE_ID")
	public BigDecimal closureId;

	@Override
	protected Object getKey() {
		return this.krcmtEmpExecutionLogPK;
	}
}
