/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class BsymtWorkplaceInfo.
 */
@Getter
@Setter
@Entity
@Table(name = "BSYMT_WORKPLACE_INFO")
public class BsymtWorkplaceInfo extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The bsymt workplace info PK. */
	@EmbeddedId
	protected BsymtWorkplaceInfoPK bsymtWorkplaceInfoPK;

	/** The wkpcd. */
	@Column(name = "WKPCD")
	private String wkpcd;

	/** The wkp name. */
	@Column(name = "WKP_NAME")
	private String wkpName;

	/** The wkp generic name. */
	@Column(name = "WKP_GENERIC_NAME")
	private String wkpGenericName;

	/** The wkp display name. */
	@Column(name = "WKP_DISPLAY_NAME")
	private String wkpDisplayName;

	/** The wkp outside code. */
	@Column(name = "WKP_OUTSIDE_CODE")
	private String wkpOutsideCode;

	/** The bsymt wkp config info. */
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "WKPID", referencedColumnName = "WKPID"),
			@PrimaryKeyJoinColumn(name = "HIST_ID", referencedColumnName = "HIST_ID") })
	public BsymtWorkplaceHist bsymtWorkplaceHist;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (bsymtWorkplaceInfoPK != null ? bsymtWorkplaceInfoPK.hashCode() : 0);
		return hash;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof BsymtWorkplaceInfo)) {
			return false;
		}
		BsymtWorkplaceInfo other = (BsymtWorkplaceInfo) object;
		if ((this.bsymtWorkplaceInfoPK == null && other.bsymtWorkplaceInfoPK != null)
				|| (this.bsymtWorkplaceInfoPK != null
						&& !this.bsymtWorkplaceInfoPK.equals(other.bsymtWorkplaceInfoPK))) {
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
		return this.bsymtWorkplaceInfoPK;
	}

}
