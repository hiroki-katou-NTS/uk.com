/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flowset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtFloBrFiAllTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLO_BR_FI_ALL_TS")
public class KshmtWtFloBrFiAllTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flow fixed rt set PK. */
	@EmbeddedId
	protected KshmtWtFloBrFiAllTsPK kshmtWtFloBrFiAllTsPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The str day. */
	@Column(name = "STR_DAY")
	private int strDay;

	/** The end day. */
	@Column(name = "END_DAY")
	private int endDay;

	/**
	 * Instantiates a new kshmt flow fixed rt set.
	 */
	public KshmtWtFloBrFiAllTs() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtFloBrFiAllTsPK != null ? kshmtWtFloBrFiAllTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFloBrFiAllTs)) {
			return false;
		}
		KshmtWtFloBrFiAllTs other = (KshmtWtFloBrFiAllTs) object;
		if ((this.kshmtWtFloBrFiAllTsPK == null && other.kshmtWtFloBrFiAllTsPK != null)
				|| (this.kshmtWtFloBrFiAllTsPK != null
						&& !this.kshmtWtFloBrFiAllTsPK.equals(other.kshmtWtFloBrFiAllTsPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtWtFloBrFiAllTsPK;
	}

}
