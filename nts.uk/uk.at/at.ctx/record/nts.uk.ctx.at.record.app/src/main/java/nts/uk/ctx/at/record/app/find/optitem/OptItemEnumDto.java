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
import nts.uk.ctx.at.record.app.find.optitem.calculation.RoundingEnumDto;
import nts.uk.ctx.at.shared.dom.scherec.optitem.EmpConditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemUsageAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.PerformanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.AddSubOperator;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.MinusSegment;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.OperatorAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.SettingItemOrder;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.SettingMethod;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

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

	/** The formula atr. */
	private List<EnumConstant> formulaAtr;

	/** The calc atr. */
	private List<EnumConstant> calcAtr;

	/** The minus segment. */
	private List<EnumConstant> minusSegment;

	/** The operator atr. */
	private List<EnumConstant> operatorAtr;

	/** The setting method. */
	private List<EnumConstant> settingMethod;

	/** The disp order. */
	private List<EnumConstant> dispOrder;

	/** The add sub atr. */
	private List<EnumConstant> addSubAtr;

	/** The amount rounding. */
	private RoundingEnumDto amountRounding;

	/** The time rounding. */
	private RoundingEnumDto timeRounding;

	/** The number rounding. */
	private RoundingEnumDto numberRounding;

	/**
	 * Inits the.
	 *
	 * @return the opt item enum dto
	 */
	public static OptItemEnumDto init(I18NResourcesForUK i18n) {
		OptItemEnumDto dto = new OptItemEnumDto();
		dto.setEmpConditionAtr(EnumAdaptor.convertToValueNameList(EmpConditionAtr.class, i18n));
		dto.setItemAtr(EnumAdaptor.convertToValueNameList(OptionalItemAtr.class, i18n));
		dto.setPerformanceAtr(EnumAdaptor.convertToValueNameList(PerformanceAtr.class, i18n));
		dto.setUseAtr(EnumAdaptor.convertToValueNameList(OptionalItemUsageAtr.class, i18n));
		dto.setFormulaAtr(EnumAdaptor.convertToValueNameList(OptionalItemAtr.class, i18n));
		dto.setCalcAtr(EnumAdaptor.convertToValueNameList(CalculationAtr.class, i18n));
		dto.setMinusSegment(EnumAdaptor.convertToValueNameList(MinusSegment.class, i18n));
		dto.setOperatorAtr(EnumAdaptor.convertToValueNameList(OperatorAtr.class, i18n));
		dto.setSettingMethod(EnumAdaptor.convertToValueNameList(SettingMethod.class, i18n));
		dto.setDispOrder(EnumAdaptor.convertToValueNameList(SettingItemOrder.class, i18n));
		dto.setAddSubAtr(EnumAdaptor.convertToValueNameList(AddSubOperator.class, i18n));
		dto.setAmountRounding(RoundingEnumDto.amount(i18n));
		dto.setTimeRounding(RoundingEnumDto.time(i18n));
		dto.setNumberRounding(RoundingEnumDto.number(i18n));

		return dto;
	}
}
