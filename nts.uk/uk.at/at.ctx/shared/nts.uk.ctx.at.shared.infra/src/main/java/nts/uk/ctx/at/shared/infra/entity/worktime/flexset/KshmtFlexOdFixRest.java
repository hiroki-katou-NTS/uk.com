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
 * The Class KshmtFlexOdFixRest.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FLEX_OD_FIX_REST")
public class KshmtFlexOdFixRest extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flex od fix rest PK. */
	@EmbeddedId
	protected KshmtFlexOdFixRestPK kshmtFlexOdFixRestPK;

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
	public KshmtFlexOdFixRest() {
		super();
	}


	/**
	 * Instantiates a new kshmt flex od fix rest.
	 *
	 * @param kshmtFlexOdFixRestPK the kshmt flex od fix rest PK
	 */
	public KshmtFlexOdFixRest(KshmtFlexOdFixRestPK kshmtFlexOdFixRestPK) {
		super();
		this.kshmtFlexOdFixRestPK = kshmtFlexOdFixRestPK;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFlexOdFixRestPK != null ? kshmtFlexOdFixRestPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlexOdFixRest)) {
			return false;
		}
		KshmtFlexOdFixRest other = (KshmtFlexOdFixRest) object;
		if ((this.kshmtFlexOdFixRestPK == null && other.kshmtFlexOdFixRestPK != null)
				|| (this.kshmtFlexOdFixRestPK != null
						&& !this.kshmtFlexOdFixRestPK.equals(other.kshmtFlexOdFixRestPK))) {
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
		return this.kshmtFlexOdFixRestPK;
	}


}
