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
	
	@Column(name = "EXISTENCE_ERROR")
	public BigDecimal existenceError;

	@Column(name = "EXECUTE_CONTENT_BY_CASE_ID")
	public BigDecimal executeContenByCaseID;

	@Column(name = "EXECUTION_CONTENT")
	public BigDecimal executedDate;

	@Column(name = "EXECUTION_START_DATE")
	public BigDecimal executionStartDate;
	
	@Column(name = "EXECUTION_END_DATE")
	public BigDecimal executionEndDate;

	@Column(name = "PROCESSING_SITUATION")
	public BigDecimal processingSituation;

	@Column(name = "SETTING_INFORMATION_TYPE")
	public BigDecimal settingInfoType;

	@Column(name = "SETTING_INFORMATION_CONTENT")
	public BigDecimal settingInfoContent;

	@Column(name = "PERIOD_COVERED_START_DATE")
	public BigDecimal periodCoverdStartDate;

	@Column(name = "PERIOD_COVERED_END_DATE")
	public BigDecimal periodCoverdEndDate;

	@Override
	protected Object getKey() {
		return this.krcmtExecutionLogPK;
	}

}
