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
 * The Class KshmtDtStampReflect.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_DT_STAMP_REFLECT")
public class KshmtDtStampReflect extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt dt stamp reflect PK. */
	@EmbeddedId
	protected KshmtDtStampReflectPK kshmtDtStampReflectPK;

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
	 * Instantiates a new kshmt dt stamp reflect.
	 */
	public KshmtDtStampReflect() {
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
		hash += (kshmtDtStampReflectPK != null ? kshmtDtStampReflectPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtDtStampReflect)) {
			return false;
		}
		KshmtDtStampReflect other = (KshmtDtStampReflect) object;
		if ((this.kshmtDtStampReflectPK == null && other.kshmtDtStampReflectPK != null)
				|| (this.kshmtDtStampReflectPK != null
						&& !this.kshmtDtStampReflectPK.equals(other.kshmtDtStampReflectPK))) {
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
		return this.kshmtDtStampReflectPK;
	}
}
