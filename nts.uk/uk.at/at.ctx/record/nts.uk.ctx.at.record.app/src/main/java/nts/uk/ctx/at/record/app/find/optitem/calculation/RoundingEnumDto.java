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
	public static RoundingEnumDto amount() {
		RoundingEnumDto dto = new RoundingEnumDto();
		dto.setRounding(
				EnumAdaptor.convertToValueNameList(nts.uk.ctx.at.shared.dom.common.amountrounding.Rounding.class));
		dto.setUnit(EnumAdaptor.convertToValueNameList(nts.uk.ctx.at.shared.dom.common.amountrounding.Unit.class));
		return dto;
	}

	/**
	 * Number.
	 *
	 * @return the rounding enum dto
	 */
	public static RoundingEnumDto number() {
		RoundingEnumDto dto = new RoundingEnumDto();
		dto.setRounding(
				EnumAdaptor.convertToValueNameList(nts.uk.ctx.at.shared.dom.common.numberrounding.Rounding.class));
		dto.setUnit(EnumAdaptor.convertToValueNameList(nts.uk.ctx.at.shared.dom.common.numberrounding.Unit.class));
		return dto;
	}

	/**
	 * Time.
	 *
	 * @return the rounding enum dto
	 */
	public static RoundingEnumDto time() {
		RoundingEnumDto dto = new RoundingEnumDto();
		dto.setRounding(
				EnumAdaptor.convertToValueNameList(nts.uk.ctx.at.shared.dom.common.timerounding.Rounding.class));
		dto.setUnit(EnumAdaptor.convertToValueNameList(nts.uk.ctx.at.shared.dom.common.timerounding.Unit.class));
		return dto;
	}
}
