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
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.KrcstRegMCalSet;

/**
 * The Class KrcmtCalcMSetRegSya.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_CALC_M_SET_REG_SYA")
public class KrcmtCalcMSetRegSya extends KrcstRegMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst sha reg M cal set PK. */
	@EmbeddedId
	protected KrcmtCalcMSetRegSyaPK krcmtCalcMSetRegSyaPK;

	/**
	 * Instantiates a new krcst sha reg M cal set.
	 */
	public KrcmtCalcMSetRegSya() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcmtCalcMSetRegSyaPK != null ? krcmtCalcMSetRegSyaPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtCalcMSetRegSya)) {
			return false;
		}
		KrcmtCalcMSetRegSya other = (KrcmtCalcMSetRegSya) object;
		if ((this.krcmtCalcMSetRegSyaPK == null && other.krcmtCalcMSetRegSyaPK != null)
				|| (this.krcmtCalcMSetRegSyaPK != null
						&& !this.krcmtCalcMSetRegSyaPK.equals(other.krcmtCalcMSetRegSyaPK))) {
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
		return this.krcmtCalcMSetRegSyaPK;
	}

}
