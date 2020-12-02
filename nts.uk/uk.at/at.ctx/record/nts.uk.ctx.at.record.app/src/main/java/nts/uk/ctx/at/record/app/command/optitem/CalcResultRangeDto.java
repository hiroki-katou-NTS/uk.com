/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optitem;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.optitem.AmountRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.CalcRangeCheck;
import nts.uk.ctx.at.shared.dom.scherec.optitem.CalcResultRangeGetMemento;
import nts.uk.ctx.at.shared.dom.scherec.optitem.NumberRange;
import nts.uk.ctx.at.shared.dom.scherec.optitem.TimeRange;

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

	// ===================== Optional ======================= //
//	/** The number upper. */
//	private Double numberUpper;
//
//	/** The number lower. */
//	private Double numberLower;
//
//	/** The time upper. */
//	private Integer timeUpper;
//
//	/** The time lower. */
//	private Integer timeLower;
//
//	/** The amount upper. */
//	private Integer amountUpper;
//
//	/** The amount lower. */
//	private Integer amountLower;
	
	private NumberRangeDto numberRange;
	
	private TimeRangeDto timeRange;
	
	private AmountRangeDto amountRange;

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

}
