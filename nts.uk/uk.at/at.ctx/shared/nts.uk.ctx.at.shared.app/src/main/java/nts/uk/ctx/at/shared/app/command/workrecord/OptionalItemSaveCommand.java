/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.workrecord;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.app.find.workrecord.CalculationResultRangeDto;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRange;
import nts.uk.ctx.at.shared.dom.workrecord.EmpConditionAtr;
import nts.uk.ctx.at.shared.dom.workrecord.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.workrecord.OptionalItemGetMemento;
import nts.uk.ctx.at.shared.dom.workrecord.OptionalItemName;
import nts.uk.ctx.at.shared.dom.workrecord.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.workrecord.OptionalItemUsageAtr;
import nts.uk.ctx.at.shared.dom.workrecord.PerformanceAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OptionalItemSaveCommand.
 */
@Getter
@Setter
public class OptionalItemSaveCommand implements OptionalItemGetMemento {

	/** The optional item no. */
	// 任意項目NO
	private String optionalItemNo;

	/** The optional item name. */
	// 任意項目名称
	private String optionalItemName;

	/** The optional item atr. */
	// 属性
	private int optionalItemAtr;

	/** The usage classification. */
	// 任意項目利用区分
	private int usageClassification;

	/** The emp condition classification. */
	// 雇用条件区分
	private int empConditionClassification;

	/** The performance classification. */
	// 実績区分
	private int performanceClassification;

	/** The calculation result range. */
	// 計算結果の範囲
	private CalculationResultRangeDto calculationResultRange;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.OptionalItemGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrecord.OptionalItemGetMemento#
	 * getOptionalItemNo()
	 */
	@Override
	public OptionalItemNo getOptionalItemNo() {
		return new OptionalItemNo(this.optionalItemNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrecord.OptionalItemGetMemento#
	 * getOptionalItemName()
	 */
	@Override
	public OptionalItemName getOptionalItemName() {
		return new OptionalItemName(this.optionalItemName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrecord.OptionalItemGetMemento#
	 * getOptionalItemAttribute()
	 */
	@Override
	public OptionalItemAtr getOptionalItemAtr() {
		return EnumAdaptor.valueOf(this.optionalItemAtr, OptionalItemAtr.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrecord.OptionalItemGetMemento#
	 * getOptionalItemUsageClassification()
	 */
	@Override
	public OptionalItemUsageAtr getOptionalItemUsageAtr() {
		return EnumAdaptor.valueOf(this.usageClassification, OptionalItemUsageAtr.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrecord.OptionalItemGetMemento#
	 * getEmpConditionClassification()
	 */
	@Override
	public EmpConditionAtr getEmpConditionAtr() {
		return EnumAdaptor.valueOf(this.empConditionClassification, EmpConditionAtr.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrecord.OptionalItemGetMemento#
	 * getPerformanceClassification()
	 */
	@Override
	public PerformanceAtr getPerformanceAtr() {
		return EnumAdaptor.valueOf(this.performanceClassification, PerformanceAtr.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrecord.OptionalItemGetMemento#
	 * getCalculationResultRange()
	 */
	@Override
	public CalculationResultRange getCalculationResultRange() {
		return new CalculationResultRange(this.calculationResultRange);
	}
}
