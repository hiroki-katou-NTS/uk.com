package nts.uk.ctx.at.record.infra.entity.dailyperformanceformat;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCST_DAILY_REC_OPE")
public class KrcstDailyRecordOperation extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcstDailyRecordOperationPK krcstDailyRecordOperationPK;

	@Column(name = "SETTING_UNIT")
	public BigDecimal settingUnit;

	@Column(name = "OPERATION_COMMENT")
	public String comment;

	@Override
	protected Object getKey() {
		return this.krcstDailyRecordOperationPK;
	}
}
