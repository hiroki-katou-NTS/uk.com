/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset;

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
import nts.uk.ctx.at.shared.infra.entity.worktime.common.KshmtWtComPK;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtDif.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_DIF")
public class KshmtWtDif extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt diff time work set PK. */
	@EmbeddedId
	protected KshmtWtDifPK kshmtWtDifPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The dt calc method. */
	@Column(name = "DT_CALC_METHOD")
	private int dtCalcMethod;

	/** The dt common rest set. */
	@Column(name = "DT_COMMON_REST_SET")
	private int dtCommonRestSet;

	/** The dt is plan actual not match master refe. */
	@Column(name = "DT_PLAN_NOT_MATCH")
	private int dtIsPlanActualNotMatchMasterRefe;

	/** The use half day. */
	@Column(name = "USE_HALF_DAY")
	private int useHalfDay;

	/** The ot set. */
	@Column(name = "OT_SET")
	private int otSet;

	/** The change ahead. */
	@Column(name = "CHANGE_AHEAD")
	private int changeAhead;

	/** The change behind. */
	@Column(name = "CHANGE_BEHIND")
	private int changeBehind;

	/** The front rear atr. */
	@Column(name = "FRONT_REAR_ATR")
	private int frontRearAtr;

	/** The time rounding unit. */
	@Column(name = "TIME_ROUNDING_UNIT")
	private int timeRoundingUnit;

	/** The upd start time. */
	@Column(name = "UPD_START_TIME")
	private int updStartTime;
	
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
	
	/** The lst kshmt dt half rest time. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtWtDifBrWekTs> lstKshmtWtDifBrWekTs;// ok

	/** The lst kshmt dt work time set. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtWtDifWorkTs> lstKshmtWtDifWorkTs;// ok

	/** The lst kshmt dt ot time set. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtWtDifOverTs> lstKshmtWtDifOverTs;// ok

	/** The lst kshmt dt hol rest time. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtWtDifBrHolTs> lstKshmtWtDifBrHolTs;// ok

	/** The lst kshmt diff time hol set. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtWtDifHolTs> lstKshmtWtDifHolTs;// ok

	/** The lst kshmt dt stamp reflect. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true) })
	private List<KshmtWtDifStmpRefTs> lstKshmtWtDifStmpRefTs;// ok

	/** The lst kshmt worktime common set. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = false, updatable = false) })
	private List<KshmtWtCom> lstKshmtWtCom;

	/**
	 * Instantiates a new kshmt diff time work set.
	 */
	public KshmtWtDif() {
		super();
	}

	/**
	 * Gets the kshmt worktime common set.
	 *
	 * @return the kshmt worktime common set
	 */
	public KshmtWtCom getKshmtWtCom() {
		if (CollectionUtil.isEmpty(this.lstKshmtWtCom)) {
			this.lstKshmtWtCom = new ArrayList<KshmtWtCom>();
		}
		return this.lstKshmtWtCom.stream().filter(entityCommon -> {
			KshmtWtComPK pk = entityCommon.getKshmtWtComPK();
			return pk.getWorkFormAtr() == WorkTimeDailyAtr.REGULAR_WORK.value
					&& pk.getWorktimeSetMethod() == WorkTimeMethodSet.DIFFTIME_WORK.value;
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
		hash += (kshmtWtDifPK != null ? kshmtWtDifPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtDif)) {
			return false;
		}
		KshmtWtDif other = (KshmtWtDif) object;
		if ((this.kshmtWtDifPK == null && other.kshmtWtDifPK != null)
				|| (this.kshmtWtDifPK != null
						&& !this.kshmtWtDifPK.equals(other.kshmtWtDifPK))) {
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
		return this.kshmtWtDifPK;
	}

}
