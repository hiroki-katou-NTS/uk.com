/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshstComNormalSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHST_COM_NORMAL_SET")
public class KshstComNormalSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshst com normal set PK. */
	@EmbeddedId
	protected KshstComNormalSetPK kshstComNormalSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The jan time. */
	@Column(name = "JAN_TIME")
	private int janTime;

	/** The feb time. */
	@Column(name = "FEB_TIME")
	private int febTime;

	/** The mar time. */
	@Column(name = "MAR_TIME")
	private int marTime;

	/** The apr time. */
	@Column(name = "APR_TIME")
	private int aprTime;

	/** The may time. */
	@Column(name = "MAY_TIME")
	private int mayTime;

	/** The jun time. */
	@Column(name = "JUN_TIME")
	private int junTime;

	/** The jul time. */
	@Column(name = "JUL_TIME")
	private int julTime;

	/** The aug time. */
	@Column(name = "AUG_TIME")
	private int augTime;

	/** The sep time. */
	@Column(name = "SEP_TIME")
	private int sepTime;

	/** The oct time. */
	@Column(name = "OCT_TIME")
	private int octTime;

	/** The nov time. */
	@Column(name = "NOV_TIME")
	private int novTime;

	/** The dec time. */
	@Column(name = "DEC_TIME")
	private int decTime;

	/**
	 * Instantiates a new kshst com normal set.
	 */
	public KshstComNormalSet() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstComNormalSetPK != null ? kshstComNormalSetPK.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstComNormalSet)) {
			return false;
		}
		KshstComNormalSet other = (KshstComNormalSet) object;
		if ((this.kshstComNormalSetPK == null && other.kshstComNormalSetPK != null)
				|| (this.kshstComNormalSetPK != null && !this.kshstComNormalSetPK.equals(other.kshstComNormalSetPK))) {
			return false;
		}
		return true;
	}

	@Override
	protected Object getKey() {
		return this.kshstComNormalSetPK;
	}
	
}
