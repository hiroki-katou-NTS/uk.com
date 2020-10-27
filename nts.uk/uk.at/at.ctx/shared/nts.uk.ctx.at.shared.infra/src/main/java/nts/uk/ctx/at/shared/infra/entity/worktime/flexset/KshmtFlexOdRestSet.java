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
 * The Class KshmtFlexOdRestSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FLEX_OD_REST_SET")
public class KshmtFlexOdRestSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flex od rest set PK. */
	@EmbeddedId
	protected KshmtFlexOdRestSetPK kshmtFlexOdRestSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The flow rest time. */
	@Column(name = "FLOW_REST_TIME")
	private int flowRestTime;

	/** The flow passage time. */
	@Column(name = "FLOW_PASSAGE_TIME")
	private int flowPassageTime;

	/**
	 * Instantiates a new kshmt flex od rest set.
	 */
	public KshmtFlexOdRestSet() {
		super();
	}

	/**
	 * Instantiates a new kshmt flex od rest set.
	 *
	 * @param kshmtFlexOdRestSetPK
	 *            the kshmt flex od rest set PK
	 */
	public KshmtFlexOdRestSet(KshmtFlexOdRestSetPK kshmtFlexOdRestSetPK) {
		super();
		this.kshmtFlexOdRestSetPK = kshmtFlexOdRestSetPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFlexOdRestSetPK != null ? kshmtFlexOdRestSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFlexOdRestSet)) {
			return false;
		}
		KshmtFlexOdRestSet other = (KshmtFlexOdRestSet) object;
		if ((this.kshmtFlexOdRestSetPK == null && other.kshmtFlexOdRestSetPK != null)
				|| (this.kshmtFlexOdRestSetPK != null
						&& !this.kshmtFlexOdRestSetPK.equals(other.kshmtFlexOdRestSetPK))) {
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
		return this.kshmtFlexOdRestSetPK;
	}

}
