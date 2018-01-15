/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.classification;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class CclmtClassification.
 */
@Getter
@Setter
@Entity
@Table(name = "CCLMT_CLASS")
public class CclmtClassification extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cclmt classification PK. */
	@EmbeddedId
	protected CclmtClassificationPK cclmtClassificationPK;

	/** The name. */
	@Column(name = "CLSNAME")
	private String name;

	/**
	 * Instantiates a new cclmt classification.
	 */
	public CclmtClassification() {
		super();
	}

	/**
	 * Instantiates a new cclmt classification.
	 *
	 * @param cclmtClassificationPK
	 *            the cclmt classification PK
	 */
	public CclmtClassification(CclmtClassificationPK cclmtClassificationPK) {
		this.cclmtClassificationPK = cclmtClassificationPK;
	}

	/**
	 * Instantiates a new cclmt classification.
	 *
	 * @param ccid
	 *            the ccid
	 * @param code
	 *            the code
	 */
	public CclmtClassification(String ccid, String code) {
		this.cclmtClassificationPK = new CclmtClassificationPK(ccid, code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cclmtClassificationPK != null ? cclmtClassificationPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof CclmtClassification)) {
			return false;
		}
		CclmtClassification other = (CclmtClassification) object;
		if ((this.cclmtClassificationPK == null && other.cclmtClassificationPK != null)
				|| (this.cclmtClassificationPK != null
						&& !this.cclmtClassificationPK.equals(other.cclmtClassificationPK))) {
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
		return this.cclmtClassificationPK;
	}

}
