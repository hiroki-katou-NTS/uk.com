/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KcbmtCompanyWorkSet.
 */
@Entity
@Setter
@Getter
@Table(name = "KSCMT_COMPANY_WORK_SET")
public class KcbmtCompanyWorkSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kcbmt company work set PK. */
	@EmbeddedId
	protected KcbmtCompanyWorkSetPK kcbmtCompanyWorkSetPK;

	/** The worktype code. */
	@Column(name = "WORK_TYPE_CD")
	private String worktypeCode;
	
	/** The working code. */
	@Column(name = "WORKING_CD")
	private String workingCode;



	

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kcbmtCompanyWorkSetPK;
	}



	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((kcbmtCompanyWorkSetPK == null) ? 0 : kcbmtCompanyWorkSetPK.hashCode());
		return result;
	}



	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		KcbmtCompanyWorkSet other = (KcbmtCompanyWorkSet) obj;
		if (kcbmtCompanyWorkSetPK == null) {
			if (other.kcbmtCompanyWorkSetPK != null)
				return false;
		} else if (!kcbmtCompanyWorkSetPK.equals(other.kcbmtCompanyWorkSetPK))
			return false;
		return true;
	}

}
