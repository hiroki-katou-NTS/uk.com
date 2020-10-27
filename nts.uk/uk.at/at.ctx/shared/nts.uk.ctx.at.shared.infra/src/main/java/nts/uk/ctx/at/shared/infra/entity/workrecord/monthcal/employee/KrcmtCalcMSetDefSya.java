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
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.KrcstDeforMCalSet;

/**
 * The Class KrcmtCalcMSetDefSya.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_CALC_M_SET_DEF_SYA")
public class KrcmtCalcMSetDefSya extends KrcstDeforMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst sha defor M cal set PK. */
	@EmbeddedId
	protected KrcmtCalcMSetDefSyaPK krcmtCalcMSetDefSyaPK;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcmtCalcMSetDefSyaPK != null ? krcmtCalcMSetDefSyaPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof KrcmtCalcMSetDefSya)) {
			return false;
		}
		KrcmtCalcMSetDefSya other = (KrcmtCalcMSetDefSya) object;
		if ((this.krcmtCalcMSetDefSyaPK == null && other.krcmtCalcMSetDefSyaPK != null)
				|| (this.krcmtCalcMSetDefSyaPK != null
						&& !this.krcmtCalcMSetDefSyaPK.equals(other.krcmtCalcMSetDefSyaPK))) {
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
		return this.krcmtCalcMSetDefSyaPK;
	}

}
