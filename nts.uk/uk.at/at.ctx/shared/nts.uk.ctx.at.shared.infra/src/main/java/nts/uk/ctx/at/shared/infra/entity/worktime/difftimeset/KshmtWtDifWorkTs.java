/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtDifWorkTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_DIF_WORK_TS")
public class KshmtWtDifWorkTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt dt work time set PK. */
	@EmbeddedId
	protected KshmtWtDifWorkTsPK kshmtWtDifWorkTsPK;

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
	 * Instantiates a new kshmt dt work time set.
	 */
	public KshmtWtDifWorkTs() {
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
		hash += (kshmtWtDifWorkTsPK != null ? kshmtWtDifWorkTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtDifWorkTs)) {
			return false;
		}
		KshmtWtDifWorkTs other = (KshmtWtDifWorkTs) object;
		if ((this.kshmtWtDifWorkTsPK == null && other.kshmtWtDifWorkTsPK != null)
				|| (this.kshmtWtDifWorkTsPK != null
						&& !this.kshmtWtDifWorkTsPK.equals(other.kshmtWtDifWorkTsPK))) {
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
		return this.kshmtWtDifWorkTsPK;
	}

}
