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
 * The Class KshmtWtDifBrWekTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_DIF_BR_WEK_TS")
public class KshmtWtDifBrWekTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt dt half rest time PK. */
	@EmbeddedId
	protected KshmtWtDifBrWekTsPK kshmtWtDifBrWekTsPK;

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
	public KshmtWtDifBrWekTs() {
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
		hash += (kshmtWtDifBrWekTsPK != null ? kshmtWtDifBrWekTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtDifBrWekTs)) {
			return false;
		}
		KshmtWtDifBrWekTs other = (KshmtWtDifBrWekTs) object;
		if ((this.kshmtWtDifBrWekTsPK == null && other.kshmtWtDifBrWekTsPK != null)
				|| (this.kshmtWtDifBrWekTsPK != null
						&& !this.kshmtWtDifBrWekTsPK.equals(other.kshmtWtDifBrWekTsPK))) {
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
		return this.kshmtWtDifBrWekTsPK;
	}

}
