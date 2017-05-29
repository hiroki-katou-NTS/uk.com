/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.subst;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KclstEmpSubstVacation.
 */
@Getter
@Setter
@Entity
@Table(name = "KCLST_EMP_COMPENS_LEAVE")
public class KsvstEmpSubstVacation extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The kclst emp compens leave PK. */
	@EmbeddedId
	protected KsvstEmpSubstVacationPK kclstEmpSubstVacationPK;

	/** The is manage. */
	@Column(name = "IS_MANAGE")
	private int isManage;

	/** The expiration date set. */
	@Column(name = "EXPIRATION_DATE_SET")
	private int expirationDateSet;

	/** The allow prepaid leave. */
	@Column(name = "ALLOW_PREPAID_LEAVE")
	private int allowPrepaidLeave;

	/**
	 * Instantiates a new kclst emp compens leave.
	 */
	public KsvstEmpSubstVacation() {
		super();
	}

	/**
	 * Instantiates a new kclst emp compens leave.
	 *
	 * @param kclstEmpSubstVacationPK
	 *            the kclst emp compens leave PK
	 */
	public KsvstEmpSubstVacation(KsvstEmpSubstVacationPK kclstEmpSubstVacationPK) {
		this.kclstEmpSubstVacationPK = kclstEmpSubstVacationPK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kclstEmpSubstVacationPK != null ? kclstEmpSubstVacationPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KsvstEmpSubstVacation)) {
			return false;
		}
		KsvstEmpSubstVacation other = (KsvstEmpSubstVacation) object;
		if ((this.kclstEmpSubstVacationPK == null && other.kclstEmpSubstVacationPK != null)
				|| (this.kclstEmpSubstVacationPK != null
						&& !this.kclstEmpSubstVacationPK.equals(other.kclstEmpSubstVacationPK))) {
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
		return this.kclstEmpSubstVacationPK;
	}

}
