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
 * The Class KshstComDeforLarSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KSHST_COM_DEFOR_LAR_SET")
public class KshstComDeforLarSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshst com defor lar set PK. */
	@EmbeddedId
	protected KshstComDeforLarSetPK kshstComDeforLarSetPK;

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
	 * Instantiates a new kshst com defor lar set.
	 */
	public KshstComDeforLarSet() {
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
		hash += (kshstComDeforLarSetPK != null ? kshstComDeforLarSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstComDeforLarSet)) {
			return false;
		}
		KshstComDeforLarSet other = (KshstComDeforLarSet) object;
		if ((this.kshstComDeforLarSetPK == null && other.kshstComDeforLarSetPK != null)
				|| (this.kshstComDeforLarSetPK != null
						&& !this.kshstComDeforLarSetPK.equals(other.kshstComDeforLarSetPK))) {
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
		return this.kshstComDeforLarSetPK;
	}

}
