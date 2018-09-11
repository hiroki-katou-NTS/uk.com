/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.infra.entity.mypage.setting;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class CcgmtPartItemSetting.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CCGMT_PART_ITEM_SET")
public class CcgmtPartItemSet extends UkJpaEntity {

	@EmbeddedId
	public CcgmtPartItemSetPK ccgmtPartItemSetPK;

	/** The use atr. */
	@Column(name = "USE_ATR")
	public int useAtr;

	@Override
	protected Object getKey() {
		return ccgmtPartItemSetPK;
	}

}
