/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.fixedset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtFixBrWekTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FIX_BR_WEK_TS")
public class KshmtWtFixBrWekTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt fixed half rest set PK. */
	@EmbeddedId
	protected KshmtWtFixBrWekTsPK kshmtWtFixBrWekTsPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The start time. */
	@Column(name = "START_TIME")
	private int startTime;

	/** The end time. */
	@Column(name = "END_TIME")
	private int endTime;

	/**
	 * Instantiates a new kshmt fixed half rest set.
	 */
	public KshmtWtFixBrWekTs() {
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
		hash += (kshmtWtFixBrWekTsPK != null ? kshmtWtFixBrWekTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFixBrWekTs)) {
			return false;
		}
		KshmtWtFixBrWekTs other = (KshmtWtFixBrWekTs) object;
		if ((this.kshmtWtFixBrWekTsPK == null && other.kshmtWtFixBrWekTsPK != null)
				|| (this.kshmtWtFixBrWekTsPK != null
						&& !this.kshmtWtFixBrWekTsPK.equals(other.kshmtWtFixBrWekTsPK))) {
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
		return this.kshmtWtFixBrWekTsPK;
	}

}
