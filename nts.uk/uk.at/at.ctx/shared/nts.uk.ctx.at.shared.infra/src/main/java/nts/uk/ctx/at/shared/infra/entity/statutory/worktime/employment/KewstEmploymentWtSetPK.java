/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime.employment;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * The Class JewtstEmploymentWtSetPK.
 */
@Embeddable
@Data
public class KewstEmploymentWtSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The y K. */
	@Column(name = "Y_K")
	private int yK;

	/** The ctg. */
	@Column(name = "WT_CTG")
	private int ctg;

	/** The type. */
	@Column(name = "WT_TYPE")
	private int type;

	/** The empt cd. */
	@Column(name = "EMPCD")
	private String emptCd;

	/**
	 * Instantiates a new jewtst employment wt set PK.
	 */
	public KewstEmploymentWtSetPK() {
	}

	/**
	 * Instantiates a new jewtst employment wt set PK.
	 *
	 * @param cid the cid
	 * @param yK the y K
	 * @param ctg the ctg
	 * @param type the type
	 * @param emptCd the empt cd
	 */
	public KewstEmploymentWtSetPK(String cid, int yK, int ctg, int type, String emptCd) {
		this.cid = cid;
		this.yK = yK;
		this.ctg = ctg;
		this.type = type;
		this.emptCd = emptCd;
	}

}
