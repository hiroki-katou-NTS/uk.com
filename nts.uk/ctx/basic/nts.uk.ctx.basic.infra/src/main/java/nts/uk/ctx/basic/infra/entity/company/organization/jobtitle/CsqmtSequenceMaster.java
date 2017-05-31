/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.jobtitle;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class CsqmtSequenceMaster.
 */

@Setter
@Entity
@Getter
@Table(name = "CSQMT_SEQUENCE_MASTER")
public class CsqmtSequenceMaster extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The csqmt sequence master PK. */
	@EmbeddedId
	protected CsqmtSequenceMasterPK csqmtSequenceMasterPK;

	/** The order. */
	@Column(name = "ORDER")
	private Integer order;

	/** The sequence name. */
	@Column(name = "SEQ_NAME")
	private String sequenceName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.csqmtSequenceMasterPK;
	}

	/**
	 * Instantiates a new csqmt sequence master.
	 */
	public CsqmtSequenceMaster() {
		super();
	}

	/**
	 * Instantiates a new csqmt sequence master.
	 *
	 * @param csqmtSequenceMasterPK the csqmt sequence master PK
	 */
	public CsqmtSequenceMaster(CsqmtSequenceMasterPK csqmtSequenceMasterPK) {
		super();
		this.csqmtSequenceMasterPK = csqmtSequenceMasterPK;
	}

	/**
	 * Instantiates a new csqmt sequence master.
	 *
	 * @param companyId the company id
	 * @param sequenceCode the sequence code
	 */
	public CsqmtSequenceMaster(String companyId, String sequenceCode) {
		super();
		this.csqmtSequenceMasterPK = new CsqmtSequenceMasterPK(companyId, sequenceCode);
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((csqmtSequenceMasterPK == null) ? 0 : csqmtSequenceMasterPK.hashCode());
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
		CsqmtSequenceMaster other = (CsqmtSequenceMaster) obj;
		if (csqmtSequenceMasterPK == null) {
			if (other.csqmtSequenceMasterPK != null)
				return false;
		} else if (!csqmtSequenceMasterPK.equals(other.csqmtSequenceMasterPK))
			return false;
		return true;
	}

}
