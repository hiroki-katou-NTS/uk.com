/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The Class KscmtWorkMonthSetPK.
 */
@Getter
@Setter
@Embeddable
public class KscmtWorkMonthSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	private String cid;

	/** The m pattern cd. */
	@Column(name = "M_PATTERN_CD")
	private String mPatternCd;

	/** The ymd K. */
	@Column(name = "YMD_K")
	@Convert(converter = GeneralDateToDBConverter.class)
	private GeneralDate ymdK;

	/**
	 * Instantiates a new kwmmt work month set PK.
	 */
	public KscmtWorkMonthSetPK() {
		super();
	}

	/**
	 * Instantiates a new kwmmt work month set PK.
	 *
	 * @param cid
	 *            the cid
	 * @param mPatternCd
	 *            the m pattern cd
	 * @param ymdK
	 *            the ymd K
	 */
	public KscmtWorkMonthSetPK(String cid, String mPatternCd, GeneralDate ymdK) {
		super();
		this.cid = cid;
		this.mPatternCd = mPatternCd;
		this.ymdK = ymdK;
	}

}
