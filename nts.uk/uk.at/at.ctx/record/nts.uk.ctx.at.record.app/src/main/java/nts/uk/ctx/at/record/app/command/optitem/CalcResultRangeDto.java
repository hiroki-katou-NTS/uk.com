/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optitem;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.optitem.*;

/**
 * The Class CalculationResultRangeDto.
 */
@Getter
@Setter
public class CalcResultRangeDto implements CalcResultRangeGetMemento {

	/** The upper check. */
	private boolean upperCheck;

	/** The lower check. */
	private boolean lowerCheck;
	
	private NumberRangeDto numberRange;
	
	private TimeRangeDto timeRange;
	
	private AmountRangeDto amountRange;

	private Integer timeInputUnit;

	private Integer numberInputUnit;

	private Integer amountInputUnit;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeGetMemento#
	 * getUpperLimit()
	 */
	@Override
	public CalcRangeCheck getUpperLimit() {
		return this.upperCheck ? CalcRangeCheck.SET : CalcRangeCheck.NOT_SET;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeGetMemento#
	 * getLowerLimit()
	 */
	@Override
	public CalcRangeCheck getLowerLimit() {
		return this.lowerCheck ? CalcRangeCheck.SET : CalcRangeCheck.NOT_SET;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeGetMemento#
	 * getNumberRange()
	 */
	@Override
	public Optional<NumberRange> getNumberRange() {
		return Optional.of(this.numberRange.toDomain());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeGetMemento#
	 * getTimeRange()
	 */
	@Override
	public Optional<TimeRange> getTimeRange() {
		return Optional.of(this.timeRange.toDomain());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrecord.CalculationResultRangeGetMemento#
	 * getAmountRange()
	 */
	@Override
	public Optional<AmountRange> getAmountRange() {
		return Optional.of(this.amountRange.todomain());
	}

	@Override
	public Optional<DailyResultInputUnit> getInputUnit() {
		if (this.timeInputUnit == null && this.numberInputUnit == null && this.amountInputUnit == null)
			return Optional.empty();
		return Optional.of(new DailyResultInputUnit(
				Optional.ofNullable(this.timeInputUnit == null ? null : EnumAdaptor.valueOf(this.timeInputUnit, TimeItemInputUnit.class)),
				Optional.ofNullable(this.numberInputUnit == null ? null : EnumAdaptor.valueOf(this.numberInputUnit, NumberItemInputUnit.class)),
				Optional.ofNullable(this.amountInputUnit == null ? null : EnumAdaptor.valueOf(this.amountInputUnit, AmountItemInputUnit.class))
		));
	}

}
