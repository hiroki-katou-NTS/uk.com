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
import nts.uk.ctx.at.record.dom.optitem.EmpConditionAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemUsageAtr;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.AddSubOperator;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.MinusSegment;
import nts.uk.ctx.at.record.dom.optitem.calculation.OperatorAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.SettingItemOrder;
import nts.uk.ctx.at.record.dom.optitem.calculation.SettingMethod;

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
	public static OptItemEnumDto init() {
		OptItemEnumDto dto = new OptItemEnumDto();
		dto.setEmpConditionAtr(EnumAdaptor.convertToValueNameList(EmpConditionAtr.class));
		dto.setItemAtr(EnumAdaptor.convertToValueNameList(OptionalItemAtr.class));
		dto.setPerformanceAtr(EnumAdaptor.convertToValueNameList(PerformanceAtr.class));
		dto.setUseAtr(EnumAdaptor.convertToValueNameList(OptionalItemUsageAtr.class));
		dto.setFormulaAtr(EnumAdaptor.convertToValueNameList(OptionalItemAtr.class));
		dto.setCalcAtr(EnumAdaptor.convertToValueNameList(CalculationAtr.class));
		dto.setMinusSegment(EnumAdaptor.convertToValueNameList(MinusSegment.class));
		dto.setOperatorAtr(EnumAdaptor.convertToValueNameList(OperatorAtr.class));
		dto.setSettingMethod(EnumAdaptor.convertToValueNameList(SettingMethod.class));
		dto.setDispOrder(EnumAdaptor.convertToValueNameList(SettingItemOrder.class));
		dto.setAddSubAtr(EnumAdaptor.convertToValueNameList(AddSubOperator.class));
		dto.setAmountRounding(RoundingEnumDto.amount());
		dto.setTimeRounding(RoundingEnumDto.time());
		dto.setNumberRounding(RoundingEnumDto.number());

		return dto;
	}
}
