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
 * The Class KrcstWkpFlexMCalSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCST_WKP_FLEX_M_CAL_SET")
public class KrcstWkpFlexMCalSet extends KrcstFlexMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst wkp flex M cal set PK. */
	@EmbeddedId
	protected KrcstWkpFlexMCalSetPK krcstWkpFlexMCalSetPK;

	/**
	 * Instantiates a new krcst wkp flex M cal set.
	 */
	public KrcstWkpFlexMCalSet() {
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
		hash += (krcstWkpFlexMCalSetPK != null ? krcstWkpFlexMCalSetPK.hashCode() : 0);
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
		if (!(object instanceof KrcstWkpFlexMCalSet)) {
			return false;
		}
		KrcstWkpFlexMCalSet other = (KrcstWkpFlexMCalSet) object;
		if ((this.krcstWkpFlexMCalSetPK == null && other.krcstWkpFlexMCalSetPK != null)
				|| (this.krcstWkpFlexMCalSetPK != null
						&& !this.krcstWkpFlexMCalSetPK.equals(other.krcstWkpFlexMCalSetPK))) {
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
		return this.krcstWkpFlexMCalSetPK;
	}

}
