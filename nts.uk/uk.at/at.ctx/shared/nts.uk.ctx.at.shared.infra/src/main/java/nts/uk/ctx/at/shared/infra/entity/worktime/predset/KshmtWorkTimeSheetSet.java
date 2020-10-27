/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime.predset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWorkTimeSheetSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_WORK_TIME_SHEET_SET")
public class KshmtWorkTimeSheetSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kshmt work time sheet set PK. */
	@EmbeddedId
	protected KshmtWorkTimeSheetSetPK kshmtWorkTimeSheetSetPK;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The use atr. */
	@Column(name = "USE_ATR")
	private int useAtr;

	/** The start time. */
	@Column(name = "START_TIME")
	private int startTime;

	/** The end time. */
	@Column(name = "END_TIME")
	private int endTime;

	/**
	 * Instantiates a new kshmt work time sheet set.
	 */
	public KshmtWorkTimeSheetSet() {
		super();
	}


	/**
	 * Instantiates a new kshmt work time sheet set.
	 *
	 * @param kshmtWorkTimeSheetSetPK the kshmt work time sheet set PK
	 */
	public KshmtWorkTimeSheetSet(KshmtWorkTimeSheetSetPK kshmtWorkTimeSheetSetPK) {
		super();
		this.kshmtWorkTimeSheetSetPK = kshmtWorkTimeSheetSetPK;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshmtWorkTimeSheetSetPK != null ? kshmtWorkTimeSheetSetPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshmtWorkTimeSheetSet)) {
			return false;
		}
		KshmtWorkTimeSheetSet other = (KshmtWorkTimeSheetSet) object;
		if ((this.kshmtWorkTimeSheetSetPK == null && other.kshmtWorkTimeSheetSetPK != null)
				|| (this.kshmtWorkTimeSheetSetPK != null
						&& !this.kshmtWorkTimeSheetSetPK.equals(other.kshmtWorkTimeSheetSetPK))) {
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
		return this.kshmtWorkTimeSheetSetPK;
	}


}
