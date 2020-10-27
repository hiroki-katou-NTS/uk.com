/**
 * 5:26:10 PM Dec 5, 2017
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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_ERALST_CNDEXP")
public class KrcstErAlCompareRange extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcstErAlCompareRangePK krcmtEralstCndexpPK;
	@Basic(optional = false)
	@NotNull
	@Column(name = "COMPARE_ATR")
	public int compareAtr;
	@Basic(optional = false)
	@NotNull
	@Column(name = "START_VALUE")
	public double startValue;
	@Basic(optional = false)
	@NotNull
	@Column(name = "END_VALUE")
	public double endValue;
	
	@OneToOne
	@JoinColumns({
		@JoinColumn(name = "CONDITION_GROUP_ID", referencedColumnName = "CONDITION_GROUP_ID", insertable = false, updatable = false),
		@JoinColumn(name = "ATD_ITEM_CON_NO", referencedColumnName = "ATD_ITEM_CON_NO", insertable = false, updatable = false) })
	public KrcmtEralstCndgrp krcmtEralstCndgrp;
	
	@Override
	protected Object getKey() {
		return this.krcmtEralstCndexpPK;
	}

	public KrcstErAlCompareRange(KrcstErAlCompareRangePK krcmtEralstCndexpPK, int compareAtr,
			double startValue, double endValue) {
		super();
		this.krcmtEralstCndexpPK = krcmtEralstCndexpPK;
		this.compareAtr = compareAtr;
		this.startValue = startValue;
		this.endValue = endValue;
	}
}
