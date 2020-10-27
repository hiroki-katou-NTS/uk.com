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
 * The Class KshmtWtFloBrFlAllTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLO_BR_FL_ALL_TS")
public class KshmtWtFloBrFlAllTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flow flow rt set PK. */
	@EmbeddedId
	protected KshmtWtFloBrFlAllTsPK kshmtWtFloBrFlAllTsPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The rest time. */
	@Column(name = "REST_TIME")
	private int restTime;

	/** The passage time. */
	@Column(name = "PASSAGE_TIME")
	private int passageTime;

	/**
	 * Instantiates a new kshmt flow flow rt set.
	 */
	public KshmtWtFloBrFlAllTs() {
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
		hash += (kshmtWtFloBrFlAllTsPK != null ? kshmtWtFloBrFlAllTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFloBrFlAllTs)) {
			return false;
		}
		KshmtWtFloBrFlAllTs other = (KshmtWtFloBrFlAllTs) object;
		if ((this.kshmtWtFloBrFlAllTsPK == null && other.kshmtWtFloBrFlAllTsPK != null)
				|| (this.kshmtWtFloBrFlAllTsPK != null
						&& !this.kshmtWtFloBrFlAllTsPK.equals(other.kshmtWtFloBrFlAllTsPK))) {
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
		return this.kshmtWtFloBrFlAllTsPK;
	}

}
