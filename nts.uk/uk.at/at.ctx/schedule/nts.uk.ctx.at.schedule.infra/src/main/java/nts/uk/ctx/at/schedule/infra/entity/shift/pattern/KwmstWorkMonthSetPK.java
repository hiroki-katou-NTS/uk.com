/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KwmstWorkMonthSetPK.
 */
@Getter
@Setter
@Embeddable
public class KwmstWorkMonthSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "CID")
	private String cid;

	/** The work type cd. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "WORK_TYPE_CD")
	private String workTypeCd;

	/** The working cd. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "WORKING_CD")
	private String workingCd;

	/**
	 * Instantiates a new kwmst work month set PK.
	 */
	public KwmstWorkMonthSetPK() {
	}

	/**
	 * Instantiates a new kwmst work month set PK.
	 *
	 * @param cid the cid
	 * @param workTypeCd the work type cd
	 * @param workingCd the working cd
	 */
	public KwmstWorkMonthSetPK(String cid, String workTypeCd, String workingCd) {
		this.cid = cid;
		this.workTypeCd = workTypeCd;
		this.workingCd = workingCd;
	}

}
