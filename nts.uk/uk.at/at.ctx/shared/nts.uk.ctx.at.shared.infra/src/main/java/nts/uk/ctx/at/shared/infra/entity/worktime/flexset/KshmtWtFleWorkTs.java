/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtFleWorkTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLE_WORK_TS")
public class KshmtWtFleWorkTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flex work time set PK. */
	@EmbeddedId
	protected KshmtWtFleWorkTsPK kshmtWtFleWorkTsPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The unit. */
	@Column(name = "UNIT")
	private int unit;

	/** The rounding. */
	@Column(name = "ROUNDING")
	private int rounding;

	/** The time str. */
	@Column(name = "TIME_STR")
	private int timeStr;

	/** The time end. */
	@Column(name = "TIME_END")
	private int timeEnd;

	/**
	 * Instantiates a new kshmt flex work time set.
	 */
	public KshmtWtFleWorkTs() {
		super();
	}
	
	/**
	 * Instantiates a new kshmt flex work time set.
	 *
	 * @param kshmtWtFleWorkTsPK the kshmt flex work time set PK
	 */
	public KshmtWtFleWorkTs(KshmtWtFleWorkTsPK kshmtWtFleWorkTsPK) {
		super();
		this.kshmtWtFleWorkTsPK = kshmtWtFleWorkTsPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtFleWorkTsPK != null ? kshmtWtFleWorkTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFleWorkTs)) {
			return false;
		}
		KshmtWtFleWorkTs other = (KshmtWtFleWorkTs) object;
		if ((this.kshmtWtFleWorkTsPK == null && other.kshmtWtFleWorkTsPK != null)
				|| (this.kshmtWtFleWorkTsPK != null
						&& !this.kshmtWtFleWorkTsPK.equals(other.kshmtWtFleWorkTsPK))) {
			return false;
		}
		return true;
	}

	@Override
	protected Object getKey() {
		return this.kshmtWtFleWorkTsPK;
	}


}
