/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime.workplace;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * The Class JwpwtstWorkplaceWtSetPK.
 */
@Embeddable
@Data
public class KwwstWorkplaceWtSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 17)
	@Column(name = "CID")
	private String cid;

	/** The y K. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "Y_K")
	private int yK;

	/** The ctg. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "WT_CTG")
	private int ctg;

	/** The type. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "WT_TYPE")
	private int type;

	/** The wkp id. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "WKPID")
	private String wkpId;

	/**
	 * Instantiates a new jwpwtst workplace wt set PK.
	 */
	public KwwstWorkplaceWtSetPK() {
	}

	/**
	 * Instantiates a new jwpwtst workplace wt set PK.
	 *
	 * @param cid the cid
	 * @param yK the y K
	 * @param ctg the ctg
	 * @param type the type
	 * @param wkpId the wkp id
	 */
	public KwwstWorkplaceWtSetPK(String cid, int yK, int ctg, int type, String wkpId) {
		this.cid = cid;
		this.yK = yK;
		this.ctg = ctg;
		this.type = type;
		this.wkpId = wkpId;
	}

}
