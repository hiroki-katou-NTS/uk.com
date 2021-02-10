/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.flowset;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtCom;
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWorktimeCommonSetPK;
import nts.uk.ctx.at.shared.infra.repository.worktime.flowset.ResttimeAtr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtWtFlo.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_FLO")
public class KshmtWtFlo extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt flow work set PK. */
	@EmbeddedId
	protected KshmtFlowWorkSetPK kshmtFlowWorkSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The fixed change atr. */
	@Column(name = "FIXED_CHANGE_ATR")
	private int fixedChangeAtr;

	/** The calc str time set. */
	@Column(name = "CALC_STR_TIME_SET")
	private int calcStrTimeSet;

	/** The legal ot set. */
	@Column(name = "LEGAL_OT_SET")
	private int legalOtSet;

	/** The kshmt flow rest set. */
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID", updatable = false, insertable = false),
		@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", updatable = false, insertable = false) })
	private KshmtWtFloBrFlAll kshmtFlowRestSet;
	
	/** The kshmt flow time zone. */
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID", updatable = false, insertable = false),
		@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", updatable = false, insertable = false) })
	private KshmtWtFloWorkTs kshmtFlowTimeZone;
	
	/** The kshmt fstamp reflect time. */
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID", updatable = false, insertable = false),
		@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", updatable = false, insertable = false) })
	private KshmtWtFloStmpRef2Ts kshmtFstampReflectTime;
	
	/** The lst kshmt flow rt set. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID"),
		@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD") })
	private List<KshmtWtFloBrFl> lstKshmtFlowRtSet;
	
	/** The lst kshmt ot time zone. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID"),
		@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD") })
	private List<KshmtWtFloOverTs> lstKshmtOtTimeZone;
	
	/** The lst kshmt fwork holiday time. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID"),
		@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD") })
	private List<KshmtWtFloHolTs> lstKshmtFworkHolidayTime;
	
	/** The lst kshmt worktime common set. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "CID", referencedColumnName = "CID"),
		@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD") })
	private List<KshmtWtCom> lstKshmtWorktimeCommonSet;
	
	/**
	 * Instantiates a new kshmt flow work set.
	 */
	public KshmtWtFlo() {
		super();
	}
	
	/**
	 * Instantiates a new kshmt flow work set.
	 *
	 * @param kshmtFlowWorkSetPK the kshmt flow work set PK
	 */
	public KshmtWtFlo(KshmtFlowWorkSetPK kshmtFlowWorkSetPK) {
		super();
		this.kshmtFlowWorkSetPK = kshmtFlowWorkSetPK;
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
		return this.lstKshmtWorktimeCommonSet.stream()
				.filter(entityCommon -> {
					KshmtWorktimeCommonSetPK pk = entityCommon.getKshmtWorktimeCommonSetPK();
					return pk.getWorkFormAtr() == WorkTimeDailyAtr.REGULAR_WORK.value
							&& pk.getWorktimeSetMethod() == WorkTimeMethodSet.FLOW_WORK.value;
				})
				.findFirst()
				.orElse(null);
	}
	
	/**
	 * Gets the flow off day work rt set.
	 *
	 * @return the flow off day work rt set
	 */
	public KshmtWtFloBrFl getFlowOffDayWorkRtSet() {
		if (CollectionUtil.isEmpty(this.lstKshmtFlowRtSet)) {
			this.lstKshmtFlowRtSet = new ArrayList<KshmtWtFloBrFl>();
		}		
		return this.lstKshmtFlowRtSet.stream()
				.filter(entity -> entity.getKshmtFlowRtSetPK().getResttimeAtr() == ResttimeAtr.OFF_DAY.value)
				.findFirst()
				.orElse(null);
	}

	/**
	 * Gets the flow half day work rt set.
	 *
	 * @return the flow half day work rt set
	 */
	public KshmtWtFloBrFl getFlowHalfDayWorkRtSet() {
		if (CollectionUtil.isEmpty(this.lstKshmtFlowRtSet)) {
			this.lstKshmtFlowRtSet = new ArrayList<KshmtWtFloBrFl>();
		}		
		return this.lstKshmtFlowRtSet.stream()
				.filter(entity -> entity.getKshmtFlowRtSetPK().getResttimeAtr() == ResttimeAtr.HALF_DAY.value)
				.findFirst()
				.orElse(null);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtFlowWorkSetPK != null ? kshmtFlowWorkSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtFlo)) {
			return false;
		}
		KshmtWtFlo other = (KshmtWtFlo) object;
		if ((this.kshmtFlowWorkSetPK == null && other.kshmtFlowWorkSetPK != null)
				|| (this.kshmtFlowWorkSetPK != null
						&& !this.kshmtFlowWorkSetPK.equals(other.kshmtFlowWorkSetPK))) {
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
		return this.kshmtFlowWorkSetPK;
	}

}
