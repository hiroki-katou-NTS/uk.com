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
 * The Class KshmtFlexHaFixRest.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FLEX_HA_FIX_REST")
public class KshmtFlexHaFixRest extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flex ha fix rest PK. */
	@EmbeddedId
	protected KshmtFlexHaFixRestPK kshmtFlexHaFixRestPK;

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
	 * Instantiates a new kshmt flex ha fix rest.
	 */
	public KshmtFlexHaFixRest() {
		super();
	}
	
	/**
	 * Instantiates a new kshmt flex ha fix rest.
	 *
	 * @param kshmtFlexHaFixRestPK the kshmt flex ha fix rest PK
	 */
	public KshmtFlexHaFixRest(KshmtFlexHaFixRestPK kshmtFlexHaFixRestPK) {
		super();
		this.kshmtFlexHaFixRestPK = kshmtFlexHaFixRestPK;
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFlexHaFixRestPK != null ? kshmtFlexHaFixRestPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlexHaFixRest)) {
			return false;
		}
		KshmtFlexHaFixRest other = (KshmtFlexHaFixRest) object;
		if ((this.kshmtFlexHaFixRestPK == null && other.kshmtFlexHaFixRestPK != null)
				|| (this.kshmtFlexHaFixRestPK != null
						&& !this.kshmtFlexHaFixRestPK.equals(other.kshmtFlexHaFixRestPK))) {
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
		return this.kshmtFlexHaFixRestPK;
	}

}
