/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalcResultOfAnyItem;

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
	}
	
	/**
	 * 上限下限チェック
	 * @return
	 */
	public CalcResultOfAnyItem checkRange(CalcResultOfAnyItem calcResultOfAnyItem,OptionalItemAtr optionalItemAtr) {
		if(this.upperLimit.isSET()) {
			Integer upperValue = getUpperLimitValue(calcResultOfAnyItem,optionalItemAtr);
			calcResultOfAnyItem = calcResultOfAnyItem.reCreateCalcResultOfAnyItem(upperValue, optionalItemAtr);
		}
		if(this.lowerLimit.isSET()) {
			Integer lowerValue = getLowerLimitValue(calcResultOfAnyItem,optionalItemAtr);
			calcResultOfAnyItem = calcResultOfAnyItem.reCreateCalcResultOfAnyItem(lowerValue, optionalItemAtr);
		}
		return calcResultOfAnyItem;
	}
	
		
	/**
	 * 上限値の制御
	 * @param optionalItemAtr
	 * @return
	 */
	public Integer getUpperLimitValue(CalcResultOfAnyItem calcResultOfAnyItem,OptionalItemAtr optionalItemAtr) {
		switch(optionalItemAtr) {
		case TIME:
			if(this.timeRange.isPresent()&&calcResultOfAnyItem.getTime().isPresent()) {
				Optional<TimeRangeValue> timeUpperLimit = this.timeRange.get().getUpperLimit();
				if(timeUpperLimit.isPresent()) {
					//値 > 上限値　の場合　値←上限値とする。
					if(calcResultOfAnyItem.getTime().get() > timeUpperLimit.get().v()) {
						return timeUpperLimit.get().v();							
					}else {
						return calcResultOfAnyItem.getTime().get();
					}	
				}
			}
		return 0;
		case NUMBER:
			if(this.numberRange.isPresent()&&calcResultOfAnyItem.getCount().isPresent()) {
				Optional<NumberRangeValue> numberUpperLimit = this.numberRange.get().getUpperLimit();
				if(numberUpperLimit.isPresent()) {
					//値 > 上限値　の場合　値←上限値とする。
					if(calcResultOfAnyItem.getCount().get() > numberUpperLimit.get().v().intValue()) {
						return numberUpperLimit.get().v().intValue();						
					}else {
						return calcResultOfAnyItem.getCount().get();
					}	
				}
			}
			return 0;
		case AMOUNT:
			if(this.amountRange.isPresent()&&calcResultOfAnyItem.getMoney().isPresent()) {
				Optional<AmountRangeValue> amountUpperLimit = this.amountRange.get().getUpperLimit();
				if(amountUpperLimit.isPresent()) {
					//値 > 上限値　の場合　値←上限値とする。
					if(calcResultOfAnyItem.getMoney().get() > amountUpperLimit.get().v()) {
						return amountUpperLimit.get().v();					
					}else {
						return calcResultOfAnyItem.getMoney().get();
					}	
				}
			}
			return 0;
		default:
			throw new RuntimeException("unknown value of enum OptionalItemAtr");
		}
	}
	
	
	/**
	 * 下限値の制御
	 * @param optionalItemAtr
	 * @return
	 */
	public Integer getLowerLimitValue(CalcResultOfAnyItem calcResultOfAnyItem,OptionalItemAtr optionalItemAtr) {
		switch(optionalItemAtr) {
		case TIME:
			if(this.timeRange.isPresent()&&calcResultOfAnyItem.getTime().isPresent()) {
				Optional<TimeRangeValue> timeLowerLimit = this.timeRange.get().getLowerLimit();
				if(timeLowerLimit.isPresent()) {
					//値 < 下限値　の場合　値←下限値とする。
					if(calcResultOfAnyItem.getTime().get() < timeLowerLimit.get().v()) {
						return timeLowerLimit.get().v();							
					}else {
						return calcResultOfAnyItem.getTime().get();
					}	
				}
			}
		return 0;
		case NUMBER:
			if(this.numberRange.isPresent()&&calcResultOfAnyItem.getCount().isPresent()) {
				Optional<NumberRangeValue> numberLowerLimit = this.numberRange.get().getLowerLimit();
				if(numberLowerLimit.isPresent()) {
					//値 < 下限値　の場合　値←下限値とする。
					if(calcResultOfAnyItem.getCount().get() < numberLowerLimit.get().v().intValue()) {
						return numberLowerLimit.get().v().intValue();						
					}else {
						return calcResultOfAnyItem.getCount().get();
					}	
				}
			}
			return 0;
		case AMOUNT:
			if(this.amountRange.isPresent()&&calcResultOfAnyItem.getMoney().isPresent()) {
				Optional<AmountRangeValue> amountLowerLimit = this.amountRange.get().getLowerLimit();
				if(amountLowerLimit.isPresent()) {
					//値 < 下限値　の場合　値←下限値とする。
					if(calcResultOfAnyItem.getMoney().get() < amountLowerLimit.get().v()) {
						return amountLowerLimit.get().v();					
					}else {
						return calcResultOfAnyItem.getMoney().get();
					}	
				}
			}
			return 0;
		default:
			throw new RuntimeException("unknown value of enum OptionalItemAtr");
		}
	}
	
	
	
}
