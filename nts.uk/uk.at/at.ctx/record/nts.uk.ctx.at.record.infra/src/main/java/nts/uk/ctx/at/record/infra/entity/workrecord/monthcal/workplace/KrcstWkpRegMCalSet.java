/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.workplace;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.infra.entity.workrecord.monthcal.KrcstRegMCalSet;

/**
 * The Class KrcstWkpRegMCalSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCST_WKP_REG_M_CAL_SET")
public class KrcstWkpRegMCalSet extends KrcstRegMCalSet implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The krcst wkp reg M cal set PK. */
	@EmbeddedId
	protected KrcstWkpRegMCalSetPK krcstWkpRegMCalSetPK;

	/**
	 * Instantiates a new krcst wkp reg M cal set.
	 */
	public KrcstWkpRegMCalSet() {
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
		hash += (krcstWkpRegMCalSetPK != null ? krcstWkpRegMCalSetPK.hashCode() : 0);
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
		if (!(object instanceof KrcstWkpRegMCalSet)) {
			return false;
		}
		KrcstWkpRegMCalSet other = (KrcstWkpRegMCalSet) object;
		if ((this.krcstWkpRegMCalSetPK == null && other.krcstWkpRegMCalSetPK != null)
				|| (this.krcstWkpRegMCalSetPK != null
						&& !this.krcstWkpRegMCalSetPK.equals(other.krcstWkpRegMCalSetPK))) {
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
		return this.krcstWkpRegMCalSetPK;
	}

}
