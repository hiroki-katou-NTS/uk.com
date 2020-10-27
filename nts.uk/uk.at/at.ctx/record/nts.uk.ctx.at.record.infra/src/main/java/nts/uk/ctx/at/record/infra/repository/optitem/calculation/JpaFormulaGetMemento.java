/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import java.util.Optional;

import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtAnyfRoundPK;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtAnyf;
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

/**
 * The Class JpaFormulaGetMemento.
 */
public class JpaFormulaGetMemento implements FormulaGetMemento {

	/** The entity. */
	private KrcmtAnyf entity;

	/**
	 * Instantiates a new jpa formula get memento.
	 *
	 * @param entity the entity
	 */
	public JpaFormulaGetMemento(KrcmtAnyf entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKrcmtAnyfPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getFormulaId()
	 */
	@Override
	public FormulaId getFormulaId() {
		return new FormulaId(this.entity.getKrcmtAnyfPK().getFormulaId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getOptionalItemNo()
	 */
	@Override
	public OptionalItemNo getOptionalItemNo() {
		return new OptionalItemNo(this.entity.getKrcmtAnyfPK().getOptionalItemNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getFormulaName()
	 */
	@Override
	public FormulaName getFormulaName() {
		return new FormulaName(this.entity.getFormulaName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getCalcFormulaSetting()
	 */
	@Override
	public CalcFormulaSetting getCalcFormulaSetting() {
		CalcFormulaSetting domain;

		switch (this.entity.getCalcAtr()) {

		case KrcmtAnyf.FORMULA_SETTING:
			domain = new CalcFormulaSetting(new JpaFormulaSettingGetMemento(this.entity.getKrcmtAnyfDetail()));
			break;

		case KrcmtAnyf.ITEM_SELECTION:
			domain = new CalcFormulaSetting(new JpaItemSelectionGetMemento(this.entity.getKrcmtAnyfItemSelects()));
			break;

		default:
			throw new RuntimeException("unknown enum value of" + this.entity.getCalcAtr());
		}
		return domain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getCalcFormulaAtr()
	 */
	@Override
	public OptionalItemAtr getFormulaAtr() {
		return OptionalItemAtr.valueOf(this.entity.getFormulaAtr());
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
		return new Symbol(this.entity.getSymbol());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getMonthlyRounding()
	 */
	@Override
	public Optional<Rounding> getMonthlyRounding() {
		JpaRoundingGetMemento memento = new JpaRoundingGetMemento(this.entity.getKrcmtAnyfRounds().stream()
				.filter(item -> item.getKrcmtAnyfRoundPK().getRoundingAtr() == KrcmtAnyfRoundPK.MONTHLY_ATR)
				.findFirst().get());
		return Optional.of(new Rounding(memento));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getDailyRounding()
	 */
	@Override
	public Optional<Rounding> getDailyRounding() {
		JpaRoundingGetMemento memento = new JpaRoundingGetMemento(this.entity.getKrcmtAnyfRounds().stream()
				.filter(item -> item.getKrcmtAnyfRoundPK().getRoundingAtr() == KrcmtAnyfRoundPK.DAILY_ATR)
				.findFirst().get());
		return Optional.of(new Rounding(memento));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaGetMemento#
	 * getCalcFormulaAtr()
	 */
	@Override
	public CalculationAtr getCalcFormulaAtr() {
		return CalculationAtr.valueOf(this.entity.getCalcAtr());
	}

}
