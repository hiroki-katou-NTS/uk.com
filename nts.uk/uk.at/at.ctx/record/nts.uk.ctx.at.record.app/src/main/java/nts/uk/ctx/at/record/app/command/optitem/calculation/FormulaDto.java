/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optitem.calculation;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalcFormulaSetting;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaId;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaName;
import nts.uk.ctx.at.record.dom.optitem.calculation.Rounding;
import nts.uk.ctx.at.record.dom.optitem.calculation.Symbol;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FormulaDto.
 */
@Getter
@Setter
public class FormulaDto implements FormulaGetMemento {

	/** The formula id. */
	// 計算式ID
	private String formulaId;

	/** The optional item no. */
	// 任意項目NO
	private String optionalItemNo;

	/** The symbol value. */
	// 記号
	private String symbolValue;

	/** The formula atr. */
	// 属性
	private int formulaAtr;

	/** The formula name. */
	// 計算式名称
	private String formulaName;

	/** The formula setting. */
	// 計算式設定
	private CalcFormulaSettingDto calcFormulaSetting;

	// ===================== Optional ======================= //
	/** The monthly rounding. */
	// 月別端数処理
	private RoundingDto monthlyRounding;

	/** The daily rounding. */
	// 日別端数処理
	private RoundingDto dailyRounding;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getOptionalItemFormulaId()
	 */
	@Override
	public FormulaId getOptionalItemFormulaId() {
		return new FormulaId(this.formulaId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getOptionalItemNo()
	 */
	@Override
	public OptionalItemNo getOptionalItemNo() {
		return new OptionalItemNo(this.optionalItemNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getOptionalItemFormulaName()
	 */
	@Override
	public FormulaName getOptionalItemFormulaName() {
		return new FormulaName(this.formulaName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getOptionalItemFormulaSetting()
	 */
	@Override
	public CalcFormulaSetting getOptionalItemFormulaSetting() {
		return new CalcFormulaSetting(this.calcFormulaSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getCalculationFormulaAtr()
	 */
	@Override
	public FormulaAtr getCalculationFormulaAtr() {
		return EnumAdaptor.valueOf(this.formulaAtr, FormulaAtr.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#getSymbol(
	 * )
	 */
	@Override
	public Symbol getSymbol() {
		return new Symbol(this.symbolValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getMonthlyRounding()
	 */
	@Override
	public Rounding getMonthlyRounding() {
		return new Rounding(this.monthlyRounding);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getDailyRounding()
	 */
	@Override
	public Rounding getDailyRounding() {
		return new Rounding(this.dailyRounding);
	}
}
