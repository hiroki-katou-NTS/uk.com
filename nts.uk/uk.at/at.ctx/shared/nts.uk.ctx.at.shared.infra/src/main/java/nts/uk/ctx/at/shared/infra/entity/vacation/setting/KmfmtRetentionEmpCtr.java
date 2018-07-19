/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting;

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

/**
 * 雇用積立年休設定
 * The Class KmfmtRetentionEmpCtr.
 */
@Entity
@Setter
@Getter
@Table(name = "KMFMT_RETENTION_EMP_CTR")
public class KmfmtRetentionEmpCtr extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The kmfmt retention emp ctr PK. */
	@EmbeddedId
	protected KmfmtRetentionEmpCtrPK kmfmtRetentionEmpCtrPK;

	/** The year amount. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "NUMBER_OF_YEAR")
	private short yearAmount;

	/** The max days retention. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "MAX_NUMBER_OF_DAYS")
	private short maxDaysRetention;

	/** The management ctr atr. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "MANAGEMENT_CTR_ATR")
	private short managementCtrAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kmfmtRetentionEmpCtrPK != null ? kmfmtRetentionEmpCtrPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KmfmtRetentionEmpCtr)) {
			return false;
		}
		KmfmtRetentionEmpCtr other = (KmfmtRetentionEmpCtr) object;
		if ((this.kmfmtRetentionEmpCtrPK == null && other.kmfmtRetentionEmpCtrPK != null)
				|| (this.kmfmtRetentionEmpCtrPK != null
						&& !this.kmfmtRetentionEmpCtrPK.equals(other.kmfmtRetentionEmpCtrPK))) {
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
		return this.kmfmtRetentionEmpCtrPK;
	}

}
