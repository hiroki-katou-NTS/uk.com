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
 * The Class KrcstEmpRegMCalSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCST_EMP_REG_M_CAL_SET")
public class KrcstEmpRegMCalSet extends KrcstRegMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst emp reg M cal set PK. */
	@EmbeddedId
	protected KrcstEmpRegMCalSetPK krcstEmpRegMCalSetPK;

	/**
	 * Instantiates a new krcst emp reg M cal set.
	 */
	public KrcstEmpRegMCalSet() {
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
		hash += (krcstEmpRegMCalSetPK != null ? krcstEmpRegMCalSetPK.hashCode() : 0);
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
		if (!(object instanceof KrcstEmpRegMCalSet)) {
			return false;
		}
		KrcstEmpRegMCalSet other = (KrcstEmpRegMCalSet) object;
		if ((this.krcstEmpRegMCalSetPK == null && other.krcstEmpRegMCalSetPK != null)
				|| (this.krcstEmpRegMCalSetPK != null
						&& !this.krcstEmpRegMCalSetPK.equals(other.krcstEmpRegMCalSetPK))) {
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
		return this.krcstEmpRegMCalSetPK;
	}

}
