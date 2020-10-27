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
 * The Class KshmtShorttimeTs.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_SHORTTIME_TS")
public class KshmtShorttimeTs extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The bshmt schild care frame PK. */
	@EmbeddedId
	protected KshmtShorttimeTsPK kshmtShorttimeTsPK;

	
	/** The str clock. */
	@Column(name = "STR_CLOCK")
	private Integer strClock;
	
	/** The end clock. */
	@Column(name = "END_CLOCK")
	private Integer endClock;

	/** The bshmt worktime hist item. */
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "SID", referencedColumnName = "KSHMT_SHORTTIME_HIST_ITEM.SID", insertable = false,
				updatable = false),
			@JoinColumn(name = "HIST_ID", referencedColumnName = "KSHMT_SHORTTIME_HIST_ITEM.HIST_ID", insertable = false,
				updatable = false) })
	private KshmtShorttimeHistItem kshmtShorttimeHistItem;
	
	/**
	 * Instantiates a new bshmt schild care frame.
	 */
	public KshmtShorttimeTs() {
		super();
	}

	/**
	 * Instantiates a new bshmt schild care frame.
	 *
	 * @param kshmtShorttimeTsPK the bshmt schild care frame PK
	 */
	public KshmtShorttimeTs(KshmtShorttimeTsPK kshmtShorttimeTsPK) {
		this.kshmtShorttimeTsPK = kshmtShorttimeTsPK;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtShorttimeTsPK != null ? kshmtShorttimeTsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtShorttimeTs)) {
			return false;
		}
		KshmtShorttimeTs other = (KshmtShorttimeTs) object;
		if ((this.kshmtShorttimeTsPK == null && other.kshmtShorttimeTsPK != null)
				|| (this.kshmtShorttimeTsPK != null
						&& !this.kshmtShorttimeTsPK.equals(other.kshmtShorttimeTsPK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtShorttimeTsPK;
	}

}
