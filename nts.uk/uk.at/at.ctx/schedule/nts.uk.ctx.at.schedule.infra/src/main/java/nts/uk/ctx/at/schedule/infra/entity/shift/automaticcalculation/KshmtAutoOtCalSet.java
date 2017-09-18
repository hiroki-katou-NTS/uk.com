/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.automaticcalculation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtAutoOtCalSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_AUTO_OT_CAL_SET")
public class KshmtAutoOtCalSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt auto ot cal set PK. */
	@EmbeddedId
	protected KshmtAutoOtCalSetPK kshmtAutoOtCalSetPK;

	/** The early ot time. */
	@Column(name = "EARLY_OT_TIME")
	private short earlyOtTime;

	/** The early mid ot time. */
	@Column(name = "EARLY_MID_OT_TIME")
	private short earlyMidOtTime;

	/** The normal ot time. */
	@Column(name = "NORMAL_OT_TIME")
	private short normalOtTime;

	/** The normal mid ot time. */
	@Column(name = "NORMAL_MID_OT_TIME")
	private short normalMidOtTime;

	/** The legal ot time. */
	@Column(name = "LEGAL_OT_TIME")
	private short legalOtTime;

	/** The legal mid ot time. */
	@Column(name = "LEGAL_MID_OT_TIME")
	private short legalMidOtTime;

	/**
	 * Instantiates a new kshmt auto ot cal set.
	 */
	public KshmtAutoOtCalSet() {
		super();
	}

	/**
	 * Instantiates a new kshmt auto ot cal set.
	 *
	 * @param kshmtAutoOtCalSetPK
	 *            the kshmt auto ot cal set PK
	 */
	public KshmtAutoOtCalSet(KshmtAutoOtCalSetPK kshmtAutoOtCalSetPK) {
		this.kshmtAutoOtCalSetPK = kshmtAutoOtCalSetPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtAutoOtCalSet)) {
			return false;
		}
		KshmtAutoOtCalSet other = (KshmtAutoOtCalSet) object;
		if ((this.kshmtAutoOtCalSetPK == null && other.kshmtAutoOtCalSetPK != null)
				|| (this.kshmtAutoOtCalSetPK != null
						&& !this.kshmtAutoOtCalSetPK.equals(other.kshmtAutoOtCalSetPK))) {
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
		return this.kshmtAutoOtCalSetPK;
	}

}
