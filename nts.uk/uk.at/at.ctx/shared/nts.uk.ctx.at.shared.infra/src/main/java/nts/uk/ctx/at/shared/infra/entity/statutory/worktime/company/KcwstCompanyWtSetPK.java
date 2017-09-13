/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * The Class JcwtstCompanyWtSetPK.
 */
@Embeddable
@Data
public class KcwstCompanyWtSetPK implements Serializable {

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

	/**
	 * Instantiates a new jcwtst company wt set PK.
	 */
	public KcwstCompanyWtSetPK() {
	}

	/**
	 * Instantiates a new jcwtst company wt set PK.
	 *
	 * @param cid
	 *            the cid
	 * @param yK
	 *            the y K
	 * @param ctg
	 *            the ctg
	 * @param type
	 *            the type
	 */
	public KcwstCompanyWtSetPK(String cid, int yK, int ctg, int type) {
		this.cid = cid;
		this.yK = yK;
		this.ctg = ctg;
		this.type = type;
	}

}
