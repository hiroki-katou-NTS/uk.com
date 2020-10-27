/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flexset;

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
 * The Class KshmtWtFleBrFlHol.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLE_BR_FL_HOL")
public class KshmtWtFleBrFlHol extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flex od rt set PK. */
	@EmbeddedId
	protected KshmtWtFleBrFlHolPK kshmtWtFleBrFlHolPK;

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

	/** The kshmt flex od fix rests. */
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<KshmtWtFleBrFiHolTs> kshmtWtFleBrFiHolTss;

	/** The kshmt flex hol sets. */
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<KshmtWtFleHolTs> kshmtWtFleHolTss;

	/** The kshmt flex od rest sets. */
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<KshmtWtFleBrFlHolTs> kshmtWtFleBrFlHolTss;

	/**
	 * Instantiates a new kshmt flex od rt set.
	 */
	public KshmtWtFleBrFlHol() {
		super();
	}

	/**
	 * Instantiates a new kshmt flex od rt set.
	 *
	 * @param kshmtWtFleBrFlHolPK
	 *            the kshmt flex od rt set PK
	 */
	public KshmtWtFleBrFlHol(KshmtWtFleBrFlHolPK kshmtWtFleBrFlHolPK) {
		super();
		this.kshmtWtFleBrFlHolPK = kshmtWtFleBrFlHolPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtFleBrFlHolPK != null ? kshmtWtFleBrFlHolPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFleBrFlHol)) {
			return false;
		}
		KshmtWtFleBrFlHol other = (KshmtWtFleBrFlHol) object;
		if ((this.kshmtWtFleBrFlHolPK == null && other.kshmtWtFleBrFlHolPK != null)
				|| (this.kshmtWtFleBrFlHolPK != null
						&& !this.kshmtWtFleBrFlHolPK.equals(other.kshmtWtFleBrFlHolPK))) {
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
		return this.kshmtWtFleBrFlHolPK;
	}

}
