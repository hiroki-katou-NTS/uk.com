/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.shortworktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class BshmtSchildCareFrame.
 */
@Getter
@Setter
@Entity
@Table(name = "BSHMT_SCHILD_CARE_FRAME")
public class BshmtSchildCareFrame extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The bshmt schild care frame PK. */
	@EmbeddedId
	protected BshmtSchildCareFramePK bshmtSchildCareFramePK;

	
	/** The str clock. */
	@Column(name = "STR_CLOCK")
	private Integer strClock;
	
	/** The end clock. */
	@Column(name = "END_CLOCK")
	private Integer endClock;

	/** The bshmt worktime hist item. */
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "SID", referencedColumnName = "BSHMT_WORKTIME_HIST_ITEM.SID", insertable = false,
				updatable = false),
			@JoinColumn(name = "HIST_ID", referencedColumnName = "BSHMT_WORKTIME_HIST_ITEM.HIST_ID", insertable = false,
				updatable = false) })
	private BshmtWorktimeHistItem bshmtWorktimeHistItem;
	
	/**
	 * Instantiates a new bshmt schild care frame.
	 */
	public BshmtSchildCareFrame() {
		super();
	}

	/**
	 * Instantiates a new bshmt schild care frame.
	 *
	 * @param bshmtSchildCareFramePK the bshmt schild care frame PK
	 */
	public BshmtSchildCareFrame(BshmtSchildCareFramePK bshmtSchildCareFramePK) {
		this.bshmtSchildCareFramePK = bshmtSchildCareFramePK;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (bshmtSchildCareFramePK != null ? bshmtSchildCareFramePK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof BshmtSchildCareFrame)) {
			return false;
		}
		BshmtSchildCareFrame other = (BshmtSchildCareFrame) object;
		if ((this.bshmtSchildCareFramePK == null && other.bshmtSchildCareFramePK != null)
				|| (this.bshmtSchildCareFramePK != null
						&& !this.bshmtSchildCareFramePK.equals(other.bshmtSchildCareFramePK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.bshmtSchildCareFramePK;
	}

}
