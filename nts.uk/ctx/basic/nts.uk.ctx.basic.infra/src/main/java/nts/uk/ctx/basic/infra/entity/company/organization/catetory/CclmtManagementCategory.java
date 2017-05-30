/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.catetory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class CclmtManagementCategory.
 */
@Getter
@Setter
@Entity
@Table(name = "CCLMT_MANAGEMENT_CATEGORY")
public class CclmtManagementCategory extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cclmt management category PK. */
	@EmbeddedId
	protected CclmtManagementCategoryPK cclmtManagementCategoryPK;

	/** The name. */
	@Column(name = "NAME")
	private String name;

	/**
	 * Instantiates a new cclmt management category.
	 */
	public CclmtManagementCategory() {
	}

	/**
	 * Instantiates a new cclmt management category.
	 *
	 * @param cclmtManagementCategoryPK
	 *            the cclmt management category PK
	 */
	public CclmtManagementCategory(CclmtManagementCategoryPK cclmtManagementCategoryPK) {
		this.cclmtManagementCategoryPK = cclmtManagementCategoryPK;
	}

	/**
	 * Instantiates a new cclmt management category.
	 *
	 * @param ccid
	 *            the ccid
	 * @param code
	 *            the code
	 */
	public CclmtManagementCategory(String ccid, String code) {
		this.cclmtManagementCategoryPK = new CclmtManagementCategoryPK(ccid, code);
	}

	/**
	 * Gets the cclmt management category PK.
	 *
	 * @return the cclmt management category PK
	 */
	public CclmtManagementCategoryPK getCclmtManagementCategoryPK() {
		return cclmtManagementCategoryPK;
	}

	/**
	 * Sets the cclmt management category PK.
	 *
	 * @param cclmtManagementCategoryPK
	 *            the new cclmt management category PK
	 */
	public void setCclmtManagementCategoryPK(CclmtManagementCategoryPK cclmtManagementCategoryPK) {
		this.cclmtManagementCategoryPK = cclmtManagementCategoryPK;
	}

	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cclmtManagementCategoryPK != null ? cclmtManagementCategoryPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof CclmtManagementCategory)) {
			return false;
		}
		CclmtManagementCategory other = (CclmtManagementCategory) object;
		if ((this.cclmtManagementCategoryPK == null && other.cclmtManagementCategoryPK != null)
			|| (this.cclmtManagementCategoryPK != null
				&& !this.cclmtManagementCategoryPK.equals(other.cclmtManagementCategoryPK))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.CclmtManagementCategory[ cclmtManagementCategoryPK="
			+ cclmtManagementCategoryPK + " ]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.cclmtManagementCategoryPK;
	}

}
