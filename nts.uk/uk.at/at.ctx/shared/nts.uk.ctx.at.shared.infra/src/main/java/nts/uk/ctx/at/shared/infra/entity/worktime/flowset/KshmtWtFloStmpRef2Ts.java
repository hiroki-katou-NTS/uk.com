/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flowset;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtFloStmpRef2Ts.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLO_STMP_REF2_TS")
public class KshmtWtFloStmpRef2Ts extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt fstamp reflect time PK. */
	@EmbeddedId
	protected KshmtWtFloStmpRef2TsPK kshmtWtFloStmpRef2TsPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The two reflect basic time. */
	@Column(name = "TWO_REFLECT_BASIC_TIME")
	private int twoReflectBasicTime;

	/** The lst kshmt flow stamp reflect. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
		@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtWtFloStmpRefTs> lstKshmtWtFloStmpRefTs;
	
	/**
	 * Instantiates a new kshmt fstamp reflect time.
	 */
	public KshmtWtFloStmpRef2Ts() {
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
		hash += (kshmtWtFloStmpRef2TsPK != null ? kshmtWtFloStmpRef2TsPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFloStmpRef2Ts)) {
			return false;
		}
		KshmtWtFloStmpRef2Ts other = (KshmtWtFloStmpRef2Ts) object;
		if ((this.kshmtWtFloStmpRef2TsPK == null && other.kshmtWtFloStmpRef2TsPK != null)
				|| (this.kshmtWtFloStmpRef2TsPK != null
						&& !this.kshmtWtFloStmpRef2TsPK.equals(other.kshmtWtFloStmpRef2TsPK))) {
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
		return this.kshmtWtFloStmpRef2TsPK;
	}

}
