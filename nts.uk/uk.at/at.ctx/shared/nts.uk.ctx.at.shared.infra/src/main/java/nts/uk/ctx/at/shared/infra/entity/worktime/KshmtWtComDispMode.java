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
 * The Class KshmtWtComDispMode.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_COM_DISP_MODE")
public class KshmtWtComDispMode extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt worktime disp mode PK. */
	@EmbeddedId
	private KshmtWtComDispModePK kshmtWtComDispModePK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The disp mode. */
	@Column(name = "DISP_MODE")
	private int dispMode;

	/**
	 * Instantiates a new kshmt worktime disp mode.
	 */
	public KshmtWtComDispMode() {
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
		hash += (kshmtWtComDispModePK != null ? kshmtWtComDispModePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtComDispMode)) {
			return false;
		}
		KshmtWtComDispMode other = (KshmtWtComDispMode) object;
		if ((this.kshmtWtComDispModePK == null && other.kshmtWtComDispModePK != null)
				|| (this.kshmtWtComDispModePK != null
						&& !this.kshmtWtComDispModePK.equals(other.kshmtWtComDispModePK))) {
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
		return this.kshmtWtComDispModePK;
	}
}