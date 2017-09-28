/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.record.dom.optitem.EmpConditionAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemUsageAtr;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;

/**
 * The Class OptItemEnumDto.
 */
@Setter
@Getter
public class OptItemEnumDto {

	/** The item atr. */
	private List<EnumConstant> itemAtr;

	/** The use atr. */
	private List<EnumConstant> useAtr;

	/** The emp condition atr. */
	private List<EnumConstant> empConditionAtr;

	/** The performance atr. */
	private List<EnumConstant> performanceAtr;

	/**
	 * Inits the.
	 *
	 * @return the opt item enum dto
	 */
	public static OptItemEnumDto init() {
		OptItemEnumDto dto = new OptItemEnumDto();
		dto.setEmpConditionAtr(EnumAdaptor.convertToValueNameList(EmpConditionAtr.class));
		dto.setItemAtr(EnumAdaptor.convertToValueNameList(OptionalItemAtr.class));
		dto.setPerformanceAtr(EnumAdaptor.convertToValueNameList(PerformanceAtr.class));
		dto.setUseAtr(EnumAdaptor.convertToValueNameList(OptionalItemUsageAtr.class));

		return dto;
	}
}
