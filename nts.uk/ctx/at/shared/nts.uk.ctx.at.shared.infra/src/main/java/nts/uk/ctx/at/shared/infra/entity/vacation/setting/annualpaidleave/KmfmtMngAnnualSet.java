/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KmfmtMngAnnualSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KMFMT_MNG_ANNUAL_SET")
public class KmfmtMngAnnualSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Id
	@Column(name = "CID")
	private String cid;

	/** The half day mng atr. */
	@Column(name = "WORK_DAY_CAL")
	private Integer workDayCal;

	/** The half day mng atr. */
	@Column(name = "HALF_DAY_MNG_ATR")
	private Integer halfDayMngAtr;

	/** The mng reference. */
	@Column(name = "MNG_REFERENCE")
	private Integer mngReference;

	/** The c uniform max number. */
	@Column(name = "C_UNIFORM_MAX_NUMBER")
	private BigDecimal cUniformMaxNumber;

	/** The max day one year. */
	@Column(name = "MAX_DAY_ONE_YEAR")
	private BigDecimal maxDayOneYear;

	/** The remain day max num. */
	@Column(name = "REMAIN_DAY_MAX_NUM")
	private Integer remainDayMaxNum;

	/** The retention year. */
	@Column(name = "RETENTION_YEAR")
	private Integer retentionYear;

	/** The year vaca priority. */
	@Column(name = "YEAR_VACA_PRIORITY")
	private Integer yearVacaPriority;

	/** The permit type. */
	@Column(name = "PERMIT_TYPE")
	private Integer permitType;

	/** The remain num display. */
	@Column(name = "REMAIN_NUM_DISPLAY")
	private Integer remainNumDisplay;

	/** The next grant day display. */
	@Column(name = "NEXT_GRANT_DAY_DISPLAY")
	private Integer nextGrantDayDisplay;

	/** The time mng atr. */
	@Column(name = "TIME_MNG_ATR")
	private Integer timeMngAtr;

	/** The time unit. */
	@Column(name = "TIME_UNIT")
	private Integer timeUnit;

	/** The time max day mng. */
	@Column(name = "TIME_MAX_DAY_MNG")
	private Integer timeMaxDayMng;

	/** The time mng reference. */
	@Column(name = "TIME_MNG_REFERENCE")
	private Integer timeMngReference;

	/** The time mng max day. */
	@Column(name = "TIME_MNG_MAX_DAY")
	private Integer timeMngMaxDay;

	/** The enough one day. */
	@Column(name = "ENOUGH_ONE_DAY")
	private Integer enoughOneDay;
	
	/**
	 * Instantiates a new kmfmt mng annual set.
	 */
	public KmfmtMngAnnualSet() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)O
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KmfmtMngAnnualSet)) {
			return false;
		}
		KmfmtMngAnnualSet other = (KmfmtMngAnnualSet) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
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
		return this.cid;
	}
}
