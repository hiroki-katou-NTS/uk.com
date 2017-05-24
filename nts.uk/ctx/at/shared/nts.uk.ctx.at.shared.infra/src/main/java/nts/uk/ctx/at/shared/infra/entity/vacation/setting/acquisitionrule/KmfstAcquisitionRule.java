/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.acquisitionrule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KmfstAcquisitionRule.
 */
@Getter
@Setter
@Entity
@Table(name = "KMFST_ACQUISITION_RULE")
public class KmfstAcquisitionRule extends UkJpaEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 17)
	@Column(name = "CID")
	private String cid;
	
	/** The annual paid. */
	@Column(name = "ANNUAL_PAID")
	private int annualPaid;
	
	/** The compensatory day off. */
	@Column(name = "COMPENSATORY_DAY_OFF")
	private int compensatoryDayOff;
	
	/** The sabstitute holiday. */
	@Column(name = "SABSTITUTE_HOLIDAY")
	private int sabstituteHoliday;
	
	/** The funded paid holiday. */
	@Column(name = "FUNDED_PAID_HOLIDAY")
	private int fundedPaidHoliday;
	
	/** The exsess holiday. */
	@Column(name = "EXSESS_HOLIDAY")
	private int exsessHoliday;
	
	/** The special holiday. */
	@Column(name = "SPECIAL_HOLIDAY")
	private int specialHoliday;
	
	/** The setting classfication. */
	@Column(name = "SETTING_CLASSFICATION")
	private int settingClassfication;

	/**
	 * Instantiates a new kmfst acquisition rule.
	 */
	public KmfstAcquisitionRule() {
	}

	/**
	 * Instantiates a new kmfst acquisition rule.
	 *
	 * @param cid the cid
	 */
	public KmfstAcquisitionRule(String cid) {
		this.cid = cid;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof KmfstAcquisitionRule)) {
			return false;
		}
		KmfstAcquisitionRule other = (KmfstAcquisitionRule) object;
		if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
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