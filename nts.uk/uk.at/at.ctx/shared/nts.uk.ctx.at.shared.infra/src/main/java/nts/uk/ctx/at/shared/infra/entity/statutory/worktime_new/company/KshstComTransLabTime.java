/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshstComTransLabTime.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHST_COM_TRANS_LAB_TIME")
public class KshstComTransLabTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The cid. */
	@Id
	@Column(name = "CID")
	private String cid;

	/** The weekly time. */
	@Column(name = "WEEKLY_TIME")
	private int weeklyTime;

	/** The week str. */
	@Column(name = "WEEK_STR")
	private int weekStr;

	/** The daily time. */
	@Column(name = "DAILY_TIME")
	private int dailyTime;

	/**
	 * Instantiates a new kshst com trans lab time.
	 */
	public KshstComTransLabTime() {
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
		hash += (cid != null ? cid.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstComTransLabTime)) {
			return false;
		}
		KshstComTransLabTime other = (KshstComTransLabTime) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		return true;
	}

	@Override
	protected Object getKey() {
		return this.cid;
	}

}
