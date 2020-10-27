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
 * The Class KshmtWtFleBrFiHolTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLE_BR_FI_HOL_TS")
public class KshmtWtFleBrFiHolTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flex od fix rest PK. */
	@EmbeddedId
	protected KshmtWtFleBrFiHolTsPK kshmtWtFleBrFiHolTsPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The str time. */
	@Column(name = "STR_TIME")
	private int strTime;

	/** The end time. */
	@Column(name = "END_TIME")
	private int endTime;

	/**
	 * Instantiates a new kshmt flex od fix rest.
	 */
	public KshmtWtFleBrFiHolTs() {
		super();
	}


	/**
	 * Instantiates a new kshmt flex od fix rest.
	 *
	 * @param kshmtWtFleBrFiHolTsPK the kshmt flex od fix rest PK
	 */
	public KshmtWtFleBrFiHolTs(KshmtWtFleBrFiHolTsPK kshmtWtFleBrFiHolTsPK) {
		super();
		this.kshmtWtFleBrFiHolTsPK = kshmtWtFleBrFiHolTsPK;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtFleBrFiHolTsPK != null ? kshmtWtFleBrFiHolTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFleBrFiHolTs)) {
			return false;
		}
		KshmtWtFleBrFiHolTs other = (KshmtWtFleBrFiHolTs) object;
		if ((this.kshmtWtFleBrFiHolTsPK == null && other.kshmtWtFleBrFiHolTsPK != null)
				|| (this.kshmtWtFleBrFiHolTsPK != null
						&& !this.kshmtWtFleBrFiHolTsPK.equals(other.kshmtWtFleBrFiHolTsPK))) {
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
		return this.kshmtWtFleBrFiHolTsPK;
	}


}
