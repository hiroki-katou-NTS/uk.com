/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.classification;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;	

/**
 * The Class CclmtClassificationPK.
 */
@Getter
@Setter
@Embeddable
public class CclmtClassificationPK implements Serializable {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ccid. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CCID")
	private String ccid;

	/** The classification code. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CLSCD")
	private String classificationCode;

	/**
	 * Instantiates a new cclmt classification PK.
	 */
	public CclmtClassificationPK() {
	}

	/**
	 * Instantiates a new cclmt classification PK.
	 *
	 * @param ccid the ccid
	 * @param code the code
	 */
	public CclmtClassificationPK(String ccid, String classificationCode) {
		this.ccid = ccid;
		this.classificationCode = classificationCode;
	}

}
