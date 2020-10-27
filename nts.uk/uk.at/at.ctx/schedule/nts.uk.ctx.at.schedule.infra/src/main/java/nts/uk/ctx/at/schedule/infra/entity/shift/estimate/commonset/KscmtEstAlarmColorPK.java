/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.commonset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscmtEstAlarmColorPK.
 */
@Getter
@Setter
@Embeddable
public class KscmtEstAlarmColorPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The estimate condition. */
	@Column(name = "EST_CONDITION")
	private int estimateCondition;

	/**
	 * Instantiates a new kscst est alarm color PK.
	 */
	public KscmtEstAlarmColorPK() {
		super();
	}

	/**
	 * Instantiates a new kscst est alarm color PK.
	 *
	 * @param cid the cid
	 * @param guideCondition the guide condition
	 */
	public KscmtEstAlarmColorPK(String cid, Integer guideCondition) {
		this.cid = cid;
		this.estimateCondition = guideCondition;
	}

}
