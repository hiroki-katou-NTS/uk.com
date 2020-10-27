/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWorktimeDispMode.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WORKTIME_DISP_MODE")
public class KshmtWorktimeDispMode extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt worktime disp mode PK. */
	@EmbeddedId
	private KshmtWorktimeDispModePK kshmtWorktimeDispModePK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The disp mode. */
	@Column(name = "DISP_MODE")
	private int dispMode;

	/**
	 * Instantiates a new kshmt worktime disp mode.
	 */
	public KshmtWorktimeDispMode() {
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
		hash += (kshmtWorktimeDispModePK != null ? kshmtWorktimeDispModePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWorktimeDispMode)) {
			return false;
		}
		KshmtWorktimeDispMode other = (KshmtWorktimeDispMode) object;
		if ((this.kshmtWorktimeDispModePK == null && other.kshmtWorktimeDispModePK != null)
				|| (this.kshmtWorktimeDispModePK != null
						&& !this.kshmtWorktimeDispModePK.equals(other.kshmtWorktimeDispModePK))) {
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
		return this.kshmtWorktimeDispModePK;
	}
}