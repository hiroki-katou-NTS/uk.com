/**
 * 5:26:59 PM Dec 5, 2017
 */
package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition.attendanceitem;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
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
@Table(name = "KRCST_ERAL_SINGLE_FIXED")
public class KrcstErAlSingleFixed extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcstErAlSingleFixedPK krcstEralSingleFixedPK;
	@Basic(optional = false)
	@NotNull
	@Column(name = "FIXED_VALUE")
	public double fixedValue;
	
	@OneToOne
	@JoinColumns({
			@JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false),
			@JoinColumn(name = "ATD_ITEM_CON_NO", referencedColumnName = "ATD_ITEM_CON_NO", insertable = false, updatable = false) })
	public KrcmtErAlAtdItemCon krcmtErAlAtdItemCon;
	
	@Override
	protected Object getKey() {
		return this.krcstEralSingleFixedPK;
	}
	public KrcstErAlSingleFixed(KrcstErAlSingleFixedPK krcstEralSingleFixedPK, double fixedValue) {
		super();
		this.krcstEralSingleFixedPK = krcstEralSingleFixedPK;
		this.fixedValue = fixedValue;
	}
}
