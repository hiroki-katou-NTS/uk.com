package nts.uk.ctx.at.record.infra.entity.divergence.time.history;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;


/**
 * The persistent class for the KRCMT_DVGC_REF_TIME database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="KRCMT_DVGC_REF_TIME")
public class KrcmtDvgcRefTime extends ContractUkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@EmbeddedId
	private KrcmtDvgcRefTimePK id;

	/** The alarm time. */
	@Column(name="ALARM_TIME")
	private BigDecimal alarmTime;

	/** The dvgc time use set. */
	@Column(name="DVGC_TIME_USE_SET")
	private BigDecimal dvgcTimeUseSet;

	/** The error time. */
	@Column(name="ERROR_TIME")
	private BigDecimal errorTime;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.id;
	}
}