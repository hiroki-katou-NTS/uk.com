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
import nts.uk.ctx.at.shared.infra.entity.workrecord.monthcal.KrcstFlexMCalSet;

/**
 * The Class KrcmtCalcMSetFleWkp.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCMT_CALC_M_SET_FLE_WKP")
public class KrcmtCalcMSetFleWkp extends KrcstFlexMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst wkp flex M cal set PK. */
	@EmbeddedId
	protected KrcmtCalcMSetFleWkpPK krcmtCalcMSetFleWkpPK;

	/**
	 * Instantiates a new krcst wkp flex M cal set.
	 */
	public KrcmtCalcMSetFleWkp() {
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
		hash += (krcmtCalcMSetFleWkpPK != null ? krcmtCalcMSetFleWkpPK.hashCode() : 0);
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
		if (!(object instanceof KrcmtCalcMSetFleWkp)) {
			return false;
		}
		KrcmtCalcMSetFleWkp other = (KrcmtCalcMSetFleWkp) object;
		if ((this.krcmtCalcMSetFleWkpPK == null && other.krcmtCalcMSetFleWkpPK != null)
				|| (this.krcmtCalcMSetFleWkpPK != null
						&& !this.krcmtCalcMSetFleWkpPK.equals(other.krcmtCalcMSetFleWkpPK))) {
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
		return this.krcmtCalcMSetFleWkpPK;
	}

}
