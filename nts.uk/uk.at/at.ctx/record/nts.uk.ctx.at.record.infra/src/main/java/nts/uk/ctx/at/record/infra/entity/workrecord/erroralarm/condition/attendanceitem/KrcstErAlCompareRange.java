/**
 * 5:26:10 PM Dec 5, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCST_ERAL_COMPARE_RANGE")
public class KrcstErAlCompareRange extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcstErAlCompareRangePK krcstEralCompareRangePK;
	@Basic(optional = false)
	@NotNull
	@Column(name = "COMPARE_ATR")
	public BigDecimal compareAtr;
	@Basic(optional = false)
	@NotNull
	@Column(name = "START_VALUE")
	public BigDecimal startValue;
	@Basic(optional = false)
	@NotNull
	@Column(name = "END_VALUE")
	public BigDecimal endValue;
	
	@Override
	protected Object getKey() {
		return this.krcstEralCompareRangePK;
	}
}
