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
 * The Class KshmtDtHalfRestTime.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_DT_HALF_REST_TIME")
public class KshmtDtHalfRestTime extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt dt half rest time PK. */
	@EmbeddedId
	protected KshmtDtHalfRestTimePK kshmtDtHalfRestTimePK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The start time. */
	@Column(name = "START_TIME")
	private int startTime;

	/** The end time. */
	@Column(name = "END_TIME")
	private int endTime;

	/** The upd start time. */
	@Column(name = "UPD_START_TIME")
	private int updStartTime;

	/**
	 * Instantiates a new kshmt dt half rest time.
	 */
	public KshmtDtHalfRestTime() {
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
		hash += (kshmtDtHalfRestTimePK != null ? kshmtDtHalfRestTimePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtDtHalfRestTime)) {
			return false;
		}
		KshmtDtHalfRestTime other = (KshmtDtHalfRestTime) object;
		if ((this.kshmtDtHalfRestTimePK == null && other.kshmtDtHalfRestTimePK != null)
				|| (this.kshmtDtHalfRestTimePK != null
						&& !this.kshmtDtHalfRestTimePK.equals(other.kshmtDtHalfRestTimePK))) {
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
		return this.kshmtDtHalfRestTimePK;
	}

}
