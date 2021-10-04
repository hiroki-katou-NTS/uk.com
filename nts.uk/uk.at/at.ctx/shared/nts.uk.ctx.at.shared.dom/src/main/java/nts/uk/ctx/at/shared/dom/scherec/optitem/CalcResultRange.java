/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Supplier;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.CalcResultOfAnyItem;

/**
 * The Class CalculationResultRange.
 */
// 計算結果の範囲
// 責務 : 計算結果の有効範囲をきめる。
// Responsibility: determine the effective range of the calculation result.
@Getter
public class CalcResultRange extends DomainObject {

	/** The upper limit. */
	// 上限値チェック
	private CalcRangeCheck upperLimit;

	/** The lower limit. */
	// 下限値チェック
	private CalcRangeCheck lowerLimit;

	// ===================== Optional ======================= //
	/** The number range. */
	// 回数範囲
	private Optional<NumberRange> numberRange;

	/** The time range. */
	// 時間範囲
	private Optional<TimeRange> timeRange;

	/** The amount range. */
	// 金額範囲
	private Optional<AmountRange> amountRange;

	// 入力単位
	private Optional<DailyResultInputUnit> inputUnit;

	/**
	 * Instantiates a new calculation result range.
	 *
	 * @param memento the memento
	 */
	public CalcResultRange(CalcResultRangeGetMemento memento) {
		this.upperLimit = memento.getUpperLimit();
		this.lowerLimit = memento.getLowerLimit();
		this.numberRange = memento.getNumberRange();
		this.timeRange = memento.getTimeRange();
		this.amountRange = memento.getAmountRange();
		this.inputUnit = memento.getInputUnit();
	}

	/**
	 * Checks for both limit.
	 *
	 * @return true, if successful
	 */
	public boolean hasBothLimit() {
		return this.lowerLimit == CalcRangeCheck.SET && this.upperLimit == CalcRangeCheck.SET;
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(CalcResultRangeSetMemento memento) {
		memento.setUpperLimit(this.upperLimit);
		memento.setLowerLimit(this.lowerLimit);
		memento.setNumberRange(this.numberRange);
		memento.setTimeRange(this.timeRange);
		memento.setAmountRange(this.amountRange);
		memento.setInputUnit(this.inputUnit);
	}
	
	/**
	 * 上限下限チェック
	 * @return
	 */
	public CalcResultOfAnyItem checkRange(CalcResultOfAnyItem calcResultOfAnyItem, OptionalItem optionalItem) {
		if(this.upperLimit.isSET()) {
			BigDecimal upperValue = getUpperLimitValue(calcResultOfAnyItem, optionalItem);
			calcResultOfAnyItem = calcResultOfAnyItem.reCreateCalcResultOfAnyItem(upperValue, optionalItem.getOptionalItemAtr());
		}
		if(this.lowerLimit.isSET()) {
			BigDecimal lowerValue = getLowerLimitValue(calcResultOfAnyItem, optionalItem);
			calcResultOfAnyItem = calcResultOfAnyItem.reCreateCalcResultOfAnyItem(lowerValue, optionalItem.getOptionalItemAtr());
		}
		return calcResultOfAnyItem;
	}
	
		
	/**
	 * 上限値の制御
	 * @param calcResultOfAnyItem
	 * @param optionalItem
	 * @return
	 */
	public BigDecimal getUpperLimitValue(CalcResultOfAnyItem calcResultOfAnyItem, OptionalItem optionalItem) {
		switch(optionalItem.getOptionalItemAtr()) {
		case TIME:
			return this.timeRange.map(range -> {
				
				return getValueOrUpper(() -> calcResultOfAnyItem.getTime(), 
										() -> range.getUpper(optionalItem.getPerformanceAtr()));
			}).orElse(BigDecimal.ZERO);
			
		case NUMBER:
			return this.numberRange.map(range -> {
				
				return getValueOrUpper(() -> calcResultOfAnyItem.getCount(), 
										() -> range.getUpper(optionalItem.getPerformanceAtr()));
			}).orElse(BigDecimal.ZERO);
			
		case AMOUNT:
			return this.amountRange.map(range -> {
				
				return getValueOrUpper(() -> calcResultOfAnyItem.getMoney(), 
										() -> range.getUpper(optionalItem.getPerformanceAtr()));
			}).orElse(BigDecimal.ZERO);
			
		default:
			throw new RuntimeException("unknown value of enum OptionalItemAtr");
		}
	}
	
	/**
	 * 下限値の制御
	 * @param calcResultOfAnyItem
	 * @param optionalItem
	 * @return
	 */
	public BigDecimal getLowerLimitValue(CalcResultOfAnyItem calcResultOfAnyItem, OptionalItem optionalItem) {
		switch(optionalItem.getOptionalItemAtr()) {
		case TIME:
			return this.timeRange.map(range -> {
				
				return getValueOrLower(() -> calcResultOfAnyItem.getTime(),
										() -> range.getLower(optionalItem.getPerformanceAtr()));
			}).orElse(BigDecimal.ZERO);
			
		case NUMBER:
			return this.numberRange.map(range -> {
				
				return getValueOrLower(() -> calcResultOfAnyItem.getCount(),
										() -> range.getLower(optionalItem.getPerformanceAtr()));
			}).orElse(BigDecimal.ZERO);
			
		case AMOUNT:
			return this.amountRange.map(range -> {
				
				return getValueOrLower(() -> calcResultOfAnyItem.getMoney(),
										() -> range.getLower(optionalItem.getPerformanceAtr()));
			}).orElse(BigDecimal.ZERO);
			
		default:
			throw new RuntimeException("unknown value of enum OptionalItemAtr");
		}
	}
	
	private BigDecimal getValueOrUpper(Supplier<Optional<BigDecimal>> target, Supplier<Optional<BigDecimal>> limit) {
		BigDecimal upperLimit = limit.get().orElse(BigDecimal.ZERO);
			
		return target.get().map(c -> {
			/** 値 > 上限値　の場合　値←上限値とする。 */
			if (c.compareTo(upperLimit) > 0) {
				return upperLimit;
			}
			return c;
		}).orElse(BigDecimal.ZERO);
	}
	
	private BigDecimal getValueOrLower(Supplier<Optional<BigDecimal>> target, Supplier<Optional<BigDecimal>> limit) {
		BigDecimal lowerLimit = limit.get().orElse(BigDecimal.ZERO);
			
		return target.get().map(c -> {
			/** 値 < 下限値　の場合　値←下限値とする。 */
			if (c.compareTo(lowerLimit) < 0) {
				return lowerLimit;
			}
			return c;
		}).orElse(BigDecimal.ZERO);
	}
}
