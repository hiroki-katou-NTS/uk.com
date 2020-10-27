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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class SptmtPartItemting.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "SPTMT_PART_ITEM")
public class SptmtPartItem extends ContractUkJpaEntity {

	@EmbeddedId
	public SptmtPartItemPK sptmtPartItemPK;

	/** The use atr. */
	@Column(name = "USE_ATR")
	public int useAtr;

	@Override
	protected Object getKey() {
		return sptmtPartItemPK;
	}

}
