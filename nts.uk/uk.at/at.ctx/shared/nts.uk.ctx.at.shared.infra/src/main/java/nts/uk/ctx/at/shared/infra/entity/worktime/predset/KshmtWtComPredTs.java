/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.predset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtComPredTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_COM_PRED_TS")
public class KshmtWtComPredTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt work time sheet set PK. */
	@EmbeddedId
	protected KshmtWtComPredTsPK kshmtWtComPredTsPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The use atr. */
	@Column(name = "USE_ATR")
	private int useAtr;

	/** The start time. */
	@Column(name = "START_TIME")
	private int startTime;

	/** The end time. */
	@Column(name = "END_TIME")
	private int endTime;

	/**
	 * Instantiates a new kshmt work time sheet set.
	 */
	public KshmtWtComPredTs() {
		super();
	}


	/**
	 * Instantiates a new kshmt work time sheet set.
	 *
	 * @param kshmtWtComPredTsPK the kshmt work time sheet set PK
	 */
	public KshmtWtComPredTs(KshmtWtComPredTsPK kshmtWtComPredTsPK) {
		super();
		this.kshmtWtComPredTsPK = kshmtWtComPredTsPK;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtComPredTsPK != null ? kshmtWtComPredTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtComPredTs)) {
			return false;
		}
		KshmtWtComPredTs other = (KshmtWtComPredTs) object;
		if ((this.kshmtWtComPredTsPK == null && other.kshmtWtComPredTsPK != null)
				|| (this.kshmtWtComPredTsPK != null
						&& !this.kshmtWtComPredTsPK.equals(other.kshmtWtComPredTsPK))) {
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
		return this.kshmtWtComPredTsPK;
	}


}
