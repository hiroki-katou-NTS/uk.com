/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.infra.entity.mypage.setting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class CcgmtPartItemSetPK.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class CcgmtPartItemSetPK implements Serializable {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
	public String cid;

	/** The top page part id. */
	@Column(name = "TOP_PAGE_PART_ID")
	public String topPagePartId;
}
