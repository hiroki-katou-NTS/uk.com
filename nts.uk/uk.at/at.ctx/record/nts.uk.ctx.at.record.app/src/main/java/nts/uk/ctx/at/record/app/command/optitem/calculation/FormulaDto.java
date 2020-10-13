/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optitem.calculation;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.CalcFormulaSetting;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaName;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.Rounding;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.Symbol;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder.DispOrder;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder.FormulaDispOrderGetMemento;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FormulaDto.
 */
@Getter
@Setter
public class FormulaDto implements FormulaGetMemento, FormulaDispOrderGetMemento {

	/** The formula id. */
	private String formulaId;

	/** The optional item no. */
	private int optionalItemNo;

	/** The symbol value. */
	private String symbolValue;

	/** The order no. */
	private int orderNo;

	/** The formula atr. */
	private int formulaAtr;

	/** The formula name. */
	private String formulaName;

	/** The calculation atr. */
	private int calcAtr;

	// ===================== Optional ======================= //
	/** The monthly rounding. */
	private RoundingDto monthlyRounding;

	/** The daily rounding. */
	private RoundingDto dailyRounding;

	/** The formula setting. */
	private FormulaSettingDto formulaSetting;

	/** The item selection. */
	private ItemSelectionDto itemSelection;

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
	public FormulaId getFormulaId() {
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
	public FormulaName getFormulaName() {
		return new FormulaName(this.formulaName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getOptionalItemFormulaSetting()
	 */
	@Override
	public CalcFormulaSetting getCalcFormulaSetting() {
		if (this.calcAtr == CalculationAtr.FORMULA_SETTING.value) {
			return new CalcFormulaSetting(this.formulaSetting);
		}
		return new CalcFormulaSetting(this.itemSelection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getCalculationFormulaAtr()
	 */
	@Override
	public OptionalItemAtr getFormulaAtr() {
		return EnumAdaptor.valueOf(this.formulaAtr, OptionalItemAtr.class);
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
	public Optional<Rounding> getMonthlyRounding() {
		return Optional.of(new Rounding(this.monthlyRounding));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getDailyRounding()
	 */
	@Override
	public Optional<Rounding> getDailyRounding() {
		return Optional.of(new Rounding(this.dailyRounding));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.disporder.
	 * FormulaDispOrderGetMemento#getDispOrder()
	 */
	@Override
	public DispOrder getDispOrder() {
		return new DispOrder(this.orderNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getCalcFormulaAtr()
	 */
	@Override
	public CalculationAtr getCalcFormulaAtr() {
		return CalculationAtr.valueOf(this.calcAtr);
	}
}
