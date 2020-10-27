/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.sixtyhours;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class KshstSixtyHourVacationSetting extends ContractUkJpaEntity {
	/** The manage distinct. */
	@Column(name = "IS_MANAGE")
	private int manageDistinct;

	/** The sixty hour extra. */
	@Column(name = "SIXTY_HOUR_EXTRA")
	private int sixtyHourExtra;

	/** The time digest tive. */
	@Column(name = "DIGESTIVE_UNIT")
	private int timeDigestTive;

}
