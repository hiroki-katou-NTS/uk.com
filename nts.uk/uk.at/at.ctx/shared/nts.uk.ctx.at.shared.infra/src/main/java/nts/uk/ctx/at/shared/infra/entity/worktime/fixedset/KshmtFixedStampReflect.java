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
 * The Class KshmtFixedStampReflect.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FIXED_STAMP_REFLECT")
public class KshmtFixedStampReflect extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt fixed stamp reflect PK. */
	@EmbeddedId
	protected KshmtFixedStampReflectPK kshmtFixedStampReflectPK;

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
	 * Instantiates a new kshmt fixed stamp reflect.
	 */
	public KshmtFixedStampReflect() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFixedStampReflectPK != null ? kshmtFixedStampReflectPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFixedStampReflect)) {
			return false;
		}
		KshmtFixedStampReflect other = (KshmtFixedStampReflect) object;
		if ((this.kshmtFixedStampReflectPK == null && other.kshmtFixedStampReflectPK != null)
				|| (this.kshmtFixedStampReflectPK != null
						&& !this.kshmtFixedStampReflectPK.equals(other.kshmtFixedStampReflectPK))) {
			return false;
		}
		return true;
	}

	@Override
	protected Object getKey() {
		return this.kshmtFixedStampReflectPK;
	}

}
