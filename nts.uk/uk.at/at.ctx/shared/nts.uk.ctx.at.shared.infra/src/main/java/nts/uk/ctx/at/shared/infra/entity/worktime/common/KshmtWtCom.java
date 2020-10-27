/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.common;

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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWtCom.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WT_COM")
public class KshmtWtCom extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt worktime common set PK. */
	@EmbeddedId
	protected KshmtWtComPK kshmtWtComPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The over day calc set. */
	@Column(name = "OVER_DAY_CALC_SET")
	private int overDayCalcSet;

	/** The use interval exemp time. */
	@Column(name = "USE_INTERVAL_EXEMP_TIME")
	private int useIntervalExempTime;

	/** The interval exemp unit. */
	@Column(name = "INTERVAL_EXEMP_UNIT")
	private int intervalExempUnit;

	/** The interval exemp rounding. */
	@Column(name = "INTERVAL_EXEMP_ROUNDING")
	private int intervalExempRounding;

	/** The interval time. */
	@Column(name = "INTERVAL_TIME")
	private int intervalTime;

	/** The use interval time. */
	@Column(name = "USE_INTERVAL_TIME")
	private int useIntervalTime;

	/** The raising salary set. */
	@Column(name = "RAISING_SALARY_SET")
	private String raisingSalarySet;

	/** The nur timezone work use. */
	@Column(name = "NUR_TIMEZONE_WORK_USE")
	private int nurTimezoneWorkUse;

	/** The emp time deduct. */
	@Column(name = "EMP_TIME_DEDUCT")
	private int empTimeDeduct;

	/** The child care work use. */
	@Column(name = "CHILD_CARE_WORK_USE")
	private int childCareWorkUse;

	/** The late night unit. */
	@Column(name = "LATE_NIGHT_UNIT")
	private int lateNightUnit;

	/** The late night rounding. */
	@Column(name = "LATE_NIGHT_ROUNDING")
	private int lateNightRounding;

	/** The holiday calc is calculate. */
	@Column(name = "HD_CAL_IS_CALCULATE")
	private int holidayCalcIsCalculate;

	/** The kshmt substitution sets. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true),
			@JoinColumn(name = "WORK_FORM_ATR", referencedColumnName = "WORK_FORM_ATR", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_SET_METHOD", referencedColumnName = "WORKTIME_SET_METHOD", insertable = true, updatable = true) })
	private List<KshmtWtComHdcom> kshmtWtComHdcoms;

	/** The kshmt medical time sets. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true),
			@JoinColumn(name = "WORK_FORM_ATR", referencedColumnName = "WORK_FORM_ATR", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_SET_METHOD", referencedColumnName = "WORKTIME_SET_METHOD", insertable = true, updatable = true) })
	private List<KshmtWtComMedical> kshmtWtComMedicals;

	/** The kshmt special round outs. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true),
			@JoinColumn(name = "WORK_FORM_ATR", referencedColumnName = "WORK_FORM_ATR", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_SET_METHOD", referencedColumnName = "WORKTIME_SET_METHOD", insertable = true, updatable = true) })
	private List<KshmtWtComGooutRound> kshmtWtComGooutRounds;

	/** The kshmt late early set. */
	@OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD"),
			@PrimaryKeyJoinColumn(name = "WORK_FORM_ATR", referencedColumnName = "WORK_FORM_ATR"),
			@PrimaryKeyJoinColumn(name = "WORKTIME_SET_METHOD", referencedColumnName = "WORKTIME_SET_METHOD") })
	private KshmtWtComLatetimeMng kshmtWtComLatetimeMng;

	/** The kshmt other late earlies. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true),
			@JoinColumn(name = "WORK_FORM_ATR", referencedColumnName = "WORK_FORM_ATR", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_SET_METHOD", referencedColumnName = "WORKTIME_SET_METHOD", insertable = true, updatable = true) })
	private List<KshmtWtComLatetime> kshmtOtherLateEarlies;

	/** The kshmt piority sets. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true),
			@JoinColumn(name = "WORK_FORM_ATR", referencedColumnName = "WORK_FORM_ATR", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_SET_METHOD", referencedColumnName = "WORKTIME_SET_METHOD", insertable = true, updatable = true) })
	private List<KshmtPioritySet> kshmtPioritySets;

	/** The kshmt rounding sets. */
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD", insertable = true, updatable = true),
			@JoinColumn(name = "WORK_FORM_ATR", referencedColumnName = "WORK_FORM_ATR", insertable = true, updatable = true),
			@JoinColumn(name = "WORKTIME_SET_METHOD", referencedColumnName = "WORKTIME_SET_METHOD", insertable = true, updatable = true) })
	private List<KshmtRoundingSet> kshmtRoundingSets;

	/** The kshmt temp worktime set. */
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD"),
			@PrimaryKeyJoinColumn(name = "WORK_FORM_ATR", referencedColumnName = "WORK_FORM_ATR"),
			@PrimaryKeyJoinColumn(name = "WORKTIME_SET_METHOD", referencedColumnName = "WORKTIME_SET_METHOD") })
	private KshmtWtComTemporary kshmtWtComTemporary;

	/** The Kshmt worktime go out set. */
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "WORKTIME_CD", referencedColumnName = "WORKTIME_CD"),
			@PrimaryKeyJoinColumn(name = "WORK_FORM_ATR", referencedColumnName = "WORK_FORM_ATR"),
			@PrimaryKeyJoinColumn(name = "WORKTIME_SET_METHOD", referencedColumnName = "WORKTIME_SET_METHOD") })
	private KshmtWtComGoout KshmtWtComGoout;

	/**
	 * Instantiates a new kshmt worktime common set.
	 */
	public KshmtWtCom() {
		super();
	}

	/**
	 * Instantiates a new kshmt worktime common set.
	 *
	 * @param kshmtWtComPK
	 *            the kshmt worktime common set PK
	 */
	public KshmtWtCom(KshmtWtComPK kshmtWtComPK) {
		this.kshmtWtComPK = kshmtWtComPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWtComPK != null ? kshmtWtComPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWtCom)) {
			return false;
		}
		KshmtWtCom other = (KshmtWtCom) object;
		if ((this.kshmtWtComPK == null && other.kshmtWtComPK != null)
				|| (this.kshmtWtComPK != null
						&& !this.kshmtWtComPK.equals(other.kshmtWtComPK))) {
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
		return this.kshmtWtComPK;
	}

}
