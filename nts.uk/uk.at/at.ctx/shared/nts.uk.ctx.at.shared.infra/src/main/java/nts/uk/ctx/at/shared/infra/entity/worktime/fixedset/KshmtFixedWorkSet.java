/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.fixedset;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.infra.entity.worktime.KshmtWorktimeCommonSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtFixedWorkSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_FIXED_WORK_SET")
public class KshmtFixedWorkSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt fixed work set PK. */
	@EmbeddedId
	protected KshmtFixedWorkSetPK kshmtFixedWorkSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The use half day. */
	@Column(name = "USE_HALF_DAY")
	private int useHalfDay;

	/** The legal ot set. */
	@Column(name = "LEGAL_OT_SET")
	private int legalOtSet;

	/** The calc method. */
	@Column(name = "CALC_METHOD")
	private int calcMethod;

	/** The lev rest calc type. */
	@Column(name = "LEV_REST_CALC_TYPE")
	private int levRestCalcType;

	/** The kshmt fixed half rest sets. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtFixedHalfRestSet> kshmtFixedHalfRestSets;

	/** The kshmt fixed work time sets. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtFixedWorkTimeSet> kshmtFixedWorkTimeSets;

	/** The kshmt fixed ot time sets. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtFixedOtTimeSet> kshmtFixedOtTimeSets;

	/** The lst kshmt fixed hol rest set. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtFixedHolRestSet> lstKshmtFixedHolRestSet;
	
	/** The lst kshmt fixed hol time set. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtFixedHolTimeSet> lstKshmtFixedHolTimeSet;
	
	/** The lst kshmt fixed stamp reflect. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtFixedStampReflect> lstKshmtFixedStampReflect;
	
	/** The kshmt worktime common set. */
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
		@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true)
	})
	private KshmtWorktimeCommonSet kshmtWorktimeCommonSet;

	/**
	 * Instantiates a new kshmt fixed work set.
	 */
	public KshmtFixedWorkSet() {
		super();
	}
	
	/**
	 * Instantiates a new kshmt fixed work set.
	 *
	 * @param kshmtFixedWorkSetPK the kshmt fixed work set PK
	 */
	public KshmtFixedWorkSet(KshmtFixedWorkSetPK kshmtFixedWorkSetPK) {
		super();
		this.kshmtFixedWorkSetPK = kshmtFixedWorkSetPK;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFixedWorkSetPK != null ? kshmtFixedWorkSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtFixedWorkSet)) {
			return false;
		}
		KshmtFixedWorkSet other = (KshmtFixedWorkSet) object;
		if ((this.kshmtFixedWorkSetPK == null && other.kshmtFixedWorkSetPK != null)
				|| (this.kshmtFixedWorkSetPK != null
						&& !this.kshmtFixedWorkSetPK.equals(other.kshmtFixedWorkSetPK))) {
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
		return this.kshmtFixedWorkSetPK;
	}

}
