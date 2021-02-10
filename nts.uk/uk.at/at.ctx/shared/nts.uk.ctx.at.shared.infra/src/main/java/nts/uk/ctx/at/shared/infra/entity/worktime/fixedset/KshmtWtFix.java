/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.fixedset;

import java.io.Serializable;
import java.util.ArrayList;
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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWorktimeCommonSetPK;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtWtFix.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FIX")
public class KshmtWtFix extends UkJpaEntity implements Serializable {

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
	
	/** The is plan actual not match master refe. */
	@Column(name = "PLAN_NOT_MATCH")
	private int isPlanActualNotMatchMasterRefe;
	
	/** The ot calc method. */
	@Column(name = "OT_CALC_METHOD")
	private Integer otCalcMethod;
	
	/** The ot in law. */
	@Column(name = "OT_IN_LAW")
	private Integer otInLaw;
	
	/** The ot not in law. */
	@Column(name = "OT_NOT_IN_LAW")
	private Integer otNotInLaw;
	
	/** The exceeded pred calc method. */
	@Column(name = "EXCEEDED_PRED_CALC_METHOD")
	private Integer exceededPredCalcMethod;
	
	/** The exceeded pred ot frame no. */
	@Column(name = "EXCEEDED_PRED_OT_FRAME_NO")
	private Integer exceededPredOtFrameNo;

	/** The kshmt fixed half rest sets. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtWtFixBrWekTs> kshmtFixedHalfRestSets;

	/** The kshmt fixed work time sets. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtWtFixWorkTs> kshmtFixedWorkTimeSets;

	/** The kshmt fixed ot time sets. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtWtFixOverTs> kshmtFixedOtTimeSets;

	/** The lst kshmt fixed hol rest set. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtWtFixBrHolTs> lstKshmtFixedHolRestSet;

	/** The lst kshmt fixed hol time set. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtWtFixHolTs> lstKshmtFixedHolTimeSet;

	/** The lst kshmt fixed stamp reflect. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtWtFixStmpRefTs> lstKshmtFixedStampReflect;

	/** The lst kshmt worktime common set. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = false, updatable = false) })
	private List<KshmtWtCom> lstKshmtWorktimeCommonSet;

	/**
	 * Instantiates a new kshmt fixed work set.
	 */
	public KshmtWtFix() {
		super();
	}

	/**
	 * Instantiates a new kshmt fixed work set.
	 *
	 * @param kshmtFixedWorkSetPK the kshmt fixed work set PK
	 */
	public KshmtWtFix(KshmtFixedWorkSetPK kshmtFixedWorkSetPK) {
		super();
		this.kshmtFixedWorkSetPK = kshmtFixedWorkSetPK;
	}

	/**
	 * Gets the kshmt worktime common set.
	 *
	 * @return the kshmt worktime common set
	 */
	public KshmtWtCom getKshmtWorktimeCommonSet() {
		if (CollectionUtil.isEmpty(this.lstKshmtWorktimeCommonSet)) {
			this.lstKshmtWorktimeCommonSet = new ArrayList<KshmtWtCom>();
		}
		return this.lstKshmtWorktimeCommonSet.stream().filter(entityCommon -> {
			KshmtWorktimeCommonSetPK pk = entityCommon.getKshmtWorktimeCommonSetPK();
			return pk.getWorkFormAtr() == WorkTimeDailyAtr.REGULAR_WORK.value
					&& pk.getWorktimeSetMethod() == WorkTimeMethodSet.FIXED_WORK.value;
		}).findFirst().orElse(null);
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
		if (!(object instanceof KshmtWtFix)) {
			return false;
		}
		KshmtWtFix other = (KshmtWtFix) object;
		if ((this.kshmtFixedWorkSetPK == null && other.kshmtFixedWorkSetPK != null)
				|| (this.kshmtFixedWorkSetPK != null && !this.kshmtFixedWorkSetPK.equals(other.kshmtFixedWorkSetPK))) {
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
