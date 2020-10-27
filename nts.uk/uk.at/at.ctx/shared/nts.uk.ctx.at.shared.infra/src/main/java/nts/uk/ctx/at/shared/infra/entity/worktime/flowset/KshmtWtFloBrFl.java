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
 * The Class KshmtWtFloBrFl.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLO_BR_FL")
public class KshmtWtFloBrFl extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flow rt set PK. */
	@EmbeddedId
	protected KshmtWtFloBrFlPK kshmtWtFloBrFlPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The fix rest time. */
	@Column(name = "FIX_REST_TIME")
	private int fixRestTime;

	/** The use rest after set. */
	@Column(name = "USE_REST_AFTER_SET")
	private int useRestAfterSet;

	/** The after rest time. */
	@Column(name = "AFTER_REST_TIME")
	private int afterRestTime;

	/** The after passage time. */
	@Column(name = "AFTER_PASSAGE_TIME")
	private int afterPassageTime;
	
	/** The lst kshmt flow flow rt set PK. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
		@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true),
		@JoinColumn(name = "RESTTIME_ATR", referencedColumnName = "RESTTIME_ATR", insertable = true, updatable = true)
	})
	private List<KshmtWtFloBrFlAllTs> lstKshmtWtFloBrFlAllTs;
	
	/** The lst kshmt flow fixed rt set PK. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
		@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true),
		@JoinColumn(name = "RESTTIME_ATR", referencedColumnName = "RESTTIME_ATR", insertable = true, updatable = true)
	})
	private List<KshmtWtFloBrFiAllTs> lstKshmtWtFloBrFiAllTs;

	/**
	 * Instantiates a new kshmt flow rt set.
	 */
	public KshmtWtFloBrFl() {
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
		hash += (kshmtWtFloBrFlPK != null ? kshmtWtFloBrFlPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFloBrFl)) {
			return false;
		}
		KshmtWtFloBrFl other = (KshmtWtFloBrFl) object;
		if ((this.kshmtWtFloBrFlPK == null && other.kshmtWtFloBrFlPK != null)
				|| (this.kshmtWtFloBrFlPK != null
						&& !this.kshmtWtFloBrFlPK.equals(other.kshmtWtFloBrFlPK))) {
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
		return this.kshmtWtFloBrFlPK;
	}

}
