/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtComHdcom.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_COM_HDCOM")
public class KshmtWtComHdcom extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt substitution set PK. */
	@EmbeddedId
	protected KshmtWtComHdcomPK kshmtWtComHdcomPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The use atr. */
	@Column(name = "USE_ATR")
	private int useAtr;

	/** The tranfer atr. */
	@Column(name = "TRANFER_ATR")
	private int tranferAtr;

	/** The one day time. */
	@Column(name = "ONE_DAY_TIME")
	private int oneDayTime;

	/** The half day time. */
	@Column(name = "HALF_DAY_TIME")
	private int halfDayTime;

	/** The certain time. */
	@Column(name = "CERTAIN_TIME")
	private int certainTime;

	/**
	 * Instantiates a new kshmt substitution set.
	 */
	public KshmtWtComHdcom() {
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
		hash += (kshmtWtComHdcomPK != null ? kshmtWtComHdcomPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtComHdcom)) {
			return false;
		}
		KshmtWtComHdcom other = (KshmtWtComHdcom) object;
		if ((this.kshmtWtComHdcomPK == null && other.kshmtWtComHdcomPK != null)
				|| (this.kshmtWtComHdcomPK != null
						&& !this.kshmtWtComHdcomPK.equals(other.kshmtWtComHdcomPK))) {
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
		return this.kshmtWtComHdcomPK;
	}

}
