/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.employment;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.KrcstFlexMCalSet;

/**
 * The Class KrcmtCalcMSetFleEmp.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_CALC_M_SET_FLE_EMP")
public class KrcmtCalcMSetFleEmp extends KrcstFlexMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst emp flex M cal set PK. */
	@EmbeddedId
	protected KrcmtCalcMSetFleEmpPK krcmtCalcMSetFleEmpPK;

	/**
	 * Instantiates a new krcst emp flex M cal set.
	 */
	public KrcmtCalcMSetFleEmp() {
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
		hash += (krcmtCalcMSetFleEmpPK != null ? krcmtCalcMSetFleEmpPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtCalcMSetFleEmp)) {
			return false;
		}
		KrcmtCalcMSetFleEmp other = (KrcmtCalcMSetFleEmp) object;
		if ((this.krcmtCalcMSetFleEmpPK == null && other.krcmtCalcMSetFleEmpPK != null)
				|| (this.krcmtCalcMSetFleEmpPK != null
						&& !this.krcmtCalcMSetFleEmpPK.equals(other.krcmtCalcMSetFleEmpPK))) {
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
		return this.krcmtCalcMSetFleEmpPK;
	}

}
