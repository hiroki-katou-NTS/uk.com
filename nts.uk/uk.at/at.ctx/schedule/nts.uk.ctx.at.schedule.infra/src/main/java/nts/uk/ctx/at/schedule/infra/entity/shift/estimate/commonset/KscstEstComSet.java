/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.commonset;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KscstEstGuideSetting.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCST_EST_COM_SET")
public class KscstEstComSet extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Id
	@Column(name = "CID")
	private String cid;

	/** The time yearly disp cond. */
	@Column(name = "TIME_Y_DISP")
	private int timeYearlyDispCond;

	/** The time monthly disp cond. */
	@Column(name = "TIME_M_DISP")
	private int timeMonthlyDispCond;

	/** The time year alarm check cond. */
	@Column(name = "TIME_Y_ALARM_CHECK")
	private int timeYearAlarmCheckCond;
	
	/** The time month alarm check cond. */
	@Column(name = "TIME_M_ALARM_CHECK")
	private int timeMonthAlarmCheckCond;

	/** The price yearly disp cond. */
	@Column(name = "PRICE_Y_DISP")
	private int priceYearlyDispCond;

	/** The price monthly disp cond. */
	@Column(name = "PRICE_M_DISP")
	private int priceMonthlyDispCond;

	/** The price year alarm check cond. */
	@Column(name = "PRICE_Y_ALARM_CHECK")
	private int priceYearAlarmCheckCond;
	
	/** The price month alarm check cond. */
	@Column(name = "PRICE_M_ALARM_CHECK")
	private int priceMonthAlarmCheckCond;

	/** The num of day yearly disp cond. */
	@Column(name = "DAYS_Y_DISP")
	private int numOfDayYearlyDispCond;

	/** The num of day monthly disp cond. */
	@Column(name = "DAYS_M_DISP")
	private int numOfDayMonthlyDispCond;

	/** The num of day alarm check cond. */
	@Column(name = "DAYS_Y_ALARM_CHECK")
	private int daysYearAlarmCheckCond;
	
	/** The num of day alarm check cond. */
	@Column(name = "DAYS_M_ALARM_CHECK")
	private int daysMonthAlarmCheckCond;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "kscstEstGuideSetting", orphanRemoval = true)
	public List<KscstEstAlarmColor> kscstEstAlarmColors;

	/**
	 * Instantiates a new kscst est guide setting.
	 */
	public KscstEstComSet() {
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
		if (!(object instanceof KscstEstComSet)) {
			return false;
		}
		KscstEstComSet other = (KscstEstComSet) object;
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
