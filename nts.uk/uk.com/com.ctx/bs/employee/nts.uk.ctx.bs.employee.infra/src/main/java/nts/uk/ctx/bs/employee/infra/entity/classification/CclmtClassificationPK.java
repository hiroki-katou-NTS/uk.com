/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.classification;

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
	@Column(name = "CID")
	private String cid;

	/** The code. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CLSCD")
	private String code;

	/**
	 * Instantiates a new cclmt classification PK.
	 */
	public CclmtClassificationPK() {
		super();
	}

	/**
	 * Instantiates a new cclmt classification PK.
	 *
	 * @param ccid the ccid
	 * @param code the code
	 */
	public CclmtClassificationPK(String cid, String code) {
		this.cid = cid;
		this.code = code;
	}


}
