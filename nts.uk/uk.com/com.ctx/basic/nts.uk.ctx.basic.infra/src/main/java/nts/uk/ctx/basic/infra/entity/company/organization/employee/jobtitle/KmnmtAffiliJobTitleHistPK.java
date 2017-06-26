/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employee.jobtitle;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmnmtAffiliJobTitleHistPK.
 */
@Getter
@Setter
@Embeddable
public class KmnmtAffiliJobTitleHistPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The hist id. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "HIST_ID")
	private String histId;

	/** The sid. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "SID")
	private String sid;

	/** The pos id. */
	@Basic(optional = false)
	@NotNull
	@Column(name = "POS_ID")
	private String posId;

	public KmnmtAffiliJobTitleHistPK() {
	}

	public KmnmtAffiliJobTitleHistPK(String histId, String sid, String posId) {
		this.histId = histId;
		this.sid = sid;
		this.posId = posId;
	}

	public String getHistId() {
		return histId;
	}

	public void setHistId(String histId) {
		this.histId = histId;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

}
