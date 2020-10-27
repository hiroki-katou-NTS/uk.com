/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtDtOtTimeSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_DT_OT_TIME_SET")
public class KshmtDtOtTimeSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt dt ot time set PK. */
	@EmbeddedId
	protected KshmtDtOtTimeSetPK kshmtDtOtTimeSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The treat time work. */
	@Column(name = "TREAT_TIME_WORK")
	private int treatTimeWork;

	/** The treat early ot work. */
	@Column(name = "TREAT_EARLY_OT_WORK")
	private int treatEarlyOtWork;

	/** The unit. */
	@Column(name = "UNIT")
	private int unit;

	/** The rounding. */
	@Column(name = "ROUNDING")
	private int rounding;

	/** The time str. */
	@Column(name = "TIME_STR")
	private int timeStr;

	/** The time end. */
	@Column(name = "TIME_END")
	private int timeEnd;

	/** The ot frame no. */
	@Column(name = "OT_FRAME_NO")
	private int otFrameNo;

	/** The legal ot frame no. */
	@Column(name = "LEGAL_OT_FRAME_NO")
	private Integer legalOtFrameNo;

	/** The payoff order. */
	@Column(name = "PAYOFF_ORDER")
	private Integer payoffOrder;

	/** The upd start time. */
	@Column(name = "UPD_START_TIME")
	private int updStartTime;

	/**
	 * Instantiates a new kshmt dt ot time set.
	 */
	public KshmtDtOtTimeSet() {
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
		hash += (kshmtDtOtTimeSetPK != null ? kshmtDtOtTimeSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtDtOtTimeSet)) {
			return false;
		}
		KshmtDtOtTimeSet other = (KshmtDtOtTimeSet) object;
		if ((this.kshmtDtOtTimeSetPK == null && other.kshmtDtOtTimeSetPK != null)
				|| (this.kshmtDtOtTimeSetPK != null
						&& !this.kshmtDtOtTimeSetPK.equals(other.kshmtDtOtTimeSetPK))) {
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
		return this.kshmtDtOtTimeSetPK;
	}

}
