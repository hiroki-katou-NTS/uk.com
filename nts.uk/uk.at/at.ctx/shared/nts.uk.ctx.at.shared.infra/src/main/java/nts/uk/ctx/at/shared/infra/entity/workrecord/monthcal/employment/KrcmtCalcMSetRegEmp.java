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
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.KrcstRegMCalSet;

/**
 * The Class KrcmtCalcMSetRegEmp.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_CALC_M_SET_REG_EMP")
public class KrcmtCalcMSetRegEmp extends KrcstRegMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst emp reg M cal set PK. */
	@EmbeddedId
	protected KrcmtCalcMSetRegEmpPK krcmtCalcMSetRegEmpPK;

	/**
	 * Instantiates a new krcst emp reg M cal set.
	 */
	public KrcmtCalcMSetRegEmp() {
		super();
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (krcmtCalcMSetRegEmpPK != null ? krcmtCalcMSetRegEmpPK.hashCode() : 0);
		return hash;
	}

	/**
	 * Equals.
	 *
	 * @param object
	 *            the object
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KrcmtCalcMSetRegEmp)) {
			return false;
		}
		KrcmtCalcMSetRegEmp other = (KrcmtCalcMSetRegEmp) object;
		if ((this.krcmtCalcMSetRegEmpPK == null && other.krcmtCalcMSetRegEmpPK != null)
				|| (this.krcmtCalcMSetRegEmpPK != null
						&& !this.krcmtCalcMSetRegEmpPK.equals(other.krcmtCalcMSetRegEmpPK))) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	@Override
	protected Object getKey() {
		return this.krcmtCalcMSetRegEmpPK;
	}

}
