/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem.calculation;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.i18n.custom.IInternationalization;

/**
 * The Class RoundingEnumDto.
 */
@Getter
@Setter
public class RoundingEnumDto {

	/** The unit. */
	private List<EnumConstant> unit;

	/** The rounding. */
	private List<EnumConstant> rounding;

	/**
	 * Amount.
	 *
	 * @return the rounding enum dto
	 */
	public static RoundingEnumDto amount(IInternationalization i18n) {
		RoundingEnumDto dto = new RoundingEnumDto();
		dto.setRounding(
				EnumAdaptor.convertToValueNameList(nts.uk.ctx.at.shared.dom.common.amountrounding.Rounding.class, i18n));
		dto.setUnit(EnumAdaptor.convertToValueNameList(nts.uk.ctx.at.shared.dom.common.amountrounding.Unit.class, i18n));
		return dto;
	}

	/**
	 * Number.
	 *
	 * @return the rounding enum dto
	 */
	public static RoundingEnumDto number(IInternationalization i18n) {
		RoundingEnumDto dto = new RoundingEnumDto();
		dto.setRounding(
				EnumAdaptor.convertToValueNameList(nts.uk.ctx.at.shared.dom.common.numberrounding.Rounding.class, i18n));
		dto.setUnit(EnumAdaptor.convertToValueNameList(nts.uk.ctx.at.shared.dom.common.numberrounding.Unit.class, i18n));
		return dto;
	}

	/**
	 * Time.
	 *
	 * @return the rounding enum dto
	 */
	public static RoundingEnumDto time(IInternationalization i18n) {
		RoundingEnumDto dto = new RoundingEnumDto();
		dto.setRounding(
				EnumAdaptor.convertToValueNameList(nts.uk.ctx.at.shared.dom.common.timerounding.Rounding.class, i18n));
		dto.setUnit(EnumAdaptor.convertToValueNameList(nts.uk.ctx.at.shared.dom.common.timerounding.Unit.class, i18n));
		return dto;
	}
}
