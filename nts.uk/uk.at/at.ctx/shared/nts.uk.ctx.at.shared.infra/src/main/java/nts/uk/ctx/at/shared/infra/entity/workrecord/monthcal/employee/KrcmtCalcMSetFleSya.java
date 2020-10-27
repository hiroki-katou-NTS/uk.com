/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employee;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.KrcstFlexMCalSet;

/**
 * The Class KrcmtCalcMSetFleSya.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_CALC_M_SET_FLE_SYA")
public class KrcmtCalcMSetFleSya extends KrcstFlexMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst sha flex M cal set PK. */
	@EmbeddedId
	protected KrcmtCalcMSetFleSyaPK krcmtCalcMSetFleSyaPK;

	/**
	 * Instantiates a new krcst sha flex M cal set.
	 */
	public KrcmtCalcMSetFleSya() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcmtCalcMSetFleSyaPK != null ? krcmtCalcMSetFleSyaPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtCalcMSetFleSya)) {
			return false;
		}
		KrcmtCalcMSetFleSya other = (KrcmtCalcMSetFleSya) object;
		if ((this.krcmtCalcMSetFleSyaPK == null && other.krcmtCalcMSetFleSyaPK != null)
				|| (this.krcmtCalcMSetFleSyaPK != null
						&& !this.krcmtCalcMSetFleSyaPK.equals(other.krcmtCalcMSetFleSyaPK))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.krcmtCalcMSetFleSyaPK;
	}

}
