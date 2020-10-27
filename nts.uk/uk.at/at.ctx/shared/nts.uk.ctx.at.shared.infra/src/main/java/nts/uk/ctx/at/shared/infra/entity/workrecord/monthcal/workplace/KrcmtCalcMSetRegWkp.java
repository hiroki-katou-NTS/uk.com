/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.workplace;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.KrcstRegMCalSet;

/**
 * The Class KrcmtCalcMSetRegWkp.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_CALC_M_SET_REG_WKP")
public class KrcmtCalcMSetRegWkp extends KrcstRegMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst wkp reg M cal set PK. */
	@EmbeddedId
	protected KrcmtCalcMSetRegWkpPK krcmtCalcMSetRegWkpPK;

	/**
	 * Instantiates a new krcst wkp reg M cal set.
	 */
	public KrcmtCalcMSetRegWkp() {
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
		hash += (krcmtCalcMSetRegWkpPK != null ? krcmtCalcMSetRegWkpPK.hashCode() : 0);
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
		if (!(object instanceof KrcmtCalcMSetRegWkp)) {
			return false;
		}
		KrcmtCalcMSetRegWkp other = (KrcmtCalcMSetRegWkp) object;
		if ((this.krcmtCalcMSetRegWkpPK == null && other.krcmtCalcMSetRegWkpPK != null)
				|| (this.krcmtCalcMSetRegWkpPK != null
						&& !this.krcmtCalcMSetRegWkpPK.equals(other.krcmtCalcMSetRegWkpPK))) {
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
		return this.krcmtCalcMSetRegWkpPK;
	}

}
