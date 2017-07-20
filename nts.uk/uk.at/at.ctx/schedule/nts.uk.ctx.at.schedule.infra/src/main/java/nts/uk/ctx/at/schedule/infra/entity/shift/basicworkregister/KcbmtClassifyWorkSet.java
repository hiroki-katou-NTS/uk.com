/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Setter
@Getter
@Table(name = "KCBST_CLASSIFY_WORK_SET")
public class KcbmtClassifyWorkSet extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kcbmt classify work set PK. */
	@EmbeddedId
	protected KcbmtClassifyWorkSetPK kcbmtClassifyWorkSetPK;

	/** The wd work type code. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "WD_WORK_TYPE_CD")
	private String wdWorkTypeCode;

	/** The wd working code. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "WD_WORKING_CD")
	private String wdWorkingCode;

	/** The nwd law work type code. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "NWDLAW_WORK_TYPE_CD")
	private String nwdLawWorkTypeCode;

	/** The nwd law working code. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "NWDLAW_WORKING_CD")
	private String nwdLawWorkingCode;

	/** The nwd ext work type code. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "NWDEXT_WORK_TYPE_CD")
	private String nwdExtWorkTypeCode;

	/** The nwd ext working code. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "NWDEXT_WORKING_CD")
	private String nwdExtWorkingCode;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kcbmtClassifyWorkSetPK;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((kcbmtClassifyWorkSetPK == null) ? 0 : kcbmtClassifyWorkSetPK.hashCode());
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
		KcbmtClassifyWorkSet other = (KcbmtClassifyWorkSet) obj;
		if (kcbmtClassifyWorkSetPK == null) {
			if (other.kcbmtClassifyWorkSetPK != null)
				return false;
		} else if (!kcbmtClassifyWorkSetPK.equals(other.kcbmtClassifyWorkSetPK))
			return false;
		return true;
	}

}
