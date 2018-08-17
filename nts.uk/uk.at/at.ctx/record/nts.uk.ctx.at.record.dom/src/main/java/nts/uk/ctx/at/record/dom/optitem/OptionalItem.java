/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.error.BundledBusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.record.dom.optitem.applicable.EmpCondition;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalcResultOfAnyItem;
import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.optitem.calculation.ResultOfCalcFormula;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class OptionalItem.
 */
// 任意項目
// 責務 : 帳票で使用する項目を設定する
// Responsibility: Set items to be used in the form
@Getter
public class OptionalItem extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The optional item no. */
	// 任意項目NO
	private OptionalItemNo optionalItemNo;

	/** The optional item name. */
	// 任意項目名称
	private OptionalItemName optionalItemName;

	/** The usage atr. */
	// 任意項目利用区分
	private OptionalItemUsageAtr usageAtr;

	/** The emp condition atr. */
	// 雇用条件区分
	private EmpConditionAtr empConditionAtr;

	/** The performance atr. */
	// 実績区分
	private PerformanceAtr performanceAtr;

	/** The optional item atr. */
	// 属性
	private OptionalItemAtr optionalItemAtr;

	/** The calculation result range. */
	// 計算結果の範囲
	private CalcResultRange calcResultRange;

	/** The unit. */
	// 単位
	private UnitOfOptionalItem unit;

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		if (this.calcResultRange.hasBothLimit()) {
			BundledBusinessException be = BundledBusinessException.newInstance();
			be.addMessage("Msg_574");
			switch (this.optionalItemAtr) {
			case NUMBER:
				if (this.calcResultRange.getNumberRange().get().isInvalidRange()) {
					be.throwExceptions();
				}
				break;
			case AMOUNT:
				if (this.calcResultRange.getAmountRange().get().isInvalidRange()) {
					be.throwExceptions();
				}
				break;
			case TIME:
				if (this.calcResultRange.getTimeRange().get().isInvalidRange()) {
					be.throwExceptions();
				}
				break;
			default:
				throw new RuntimeException("unknown value of enum OptionalItemAtr");
			}
		}
	}

	/**
	 * Checks if is used.
	 *
	 * @return true, if is used
	 */
	public boolean isUsed() {
		return this.usageAtr.equals(OptionalItemUsageAtr.USE);
	}

	/**
	 * Instantiates a new optional item.
	 *
	 * @param memento the memento
	 */
	public OptionalItem(OptionalItemGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.optionalItemNo = memento.getOptionalItemNo();
		this.optionalItemName = memento.getOptionalItemName();
		this.optionalItemAtr = memento.getOptionalItemAtr();
		this.usageAtr = memento.getOptionalItemUsageAtr();
		this.empConditionAtr = memento.getEmpConditionAtr();
		this.performanceAtr = memento.getPerformanceAtr();
		this.calcResultRange = memento.getCalculationResultRange();
		this.unit = memento.getUnit();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OptionalItemSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setOptionalItemNo(this.optionalItemNo);
		memento.setOptionalItemAtr(this.optionalItemAtr);
		memento.setOptionalItemName(this.optionalItemName);
		memento.setOptionalItemUsageAtr(this.usageAtr);
		memento.setEmpConditionAtr(this.empConditionAtr);
		memento.setPerformanceAtr(this.performanceAtr);
		memento.setCalculationResultRange(this.calcResultRange);
		memento.setUnit(this.unit);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((optionalItemNo == null) ? 0 : optionalItemNo.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OptionalItem other = (OptionalItem) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (optionalItemNo == null) {
			if (other.optionalItemNo != null)
				return false;
		} else if (!optionalItemNo.equals(other.optionalItemNo))
			return false;
		return true;
	}
	
	
	/**
	 * 利用条件の判定
	 * @return
	 */
	public boolean checkTermsOfUse(Optional<EmpCondition> empCondition,Optional<BsEmploymentHistoryImport> bsEmploymentHistOpt) {
		//利用区分をチェック
		if(this.usageAtr.isNotUse()) {
			return false;
		}
		//雇用条件区分をチェック
		if(this.empConditionAtr.isNoCondition()) {
			return true;
		}
		//適用する雇用条件が取得できたかチェック
		if(!empCondition.isPresent()||empCondition.get().getEmpConditions().isEmpty()) {
			return true;
		}
		//雇用条件判断
		return empCondition.get().checkEmpCondition(bsEmploymentHistOpt);
	}
	
	
    /**
     * 計算処理
     * @param companyId
     * @param optionalItem
     * @param formulaList
     * @param dailyRecordDto
     * @return
     */
    public CalcResultOfAnyItem caluculationFormula(String companyId,
    												OptionalItem optionalItem,
    												List<Formula> formulaList,
    												Optional<DailyRecordToAttendanceItemConverter> dailyRecordDto,
    												Optional<MonthlyRecordToAttendanceItemConverter> monthlyRecordDto) {
	
    	//任意項目計算式を記号の昇順でソート
    	formulaList.sort((first,second) -> first.getSymbol().compareTo(second.getSymbol()));
        /*パラメータ：任意項目の計算結果を定義*/
        List<ResultOfCalcFormula> calcResultAnyItem = new ArrayList<>();
        //計算式分ループ
        for(Formula formula : formulaList) {
            calcResultAnyItem.add(formula.dicisionCalc(optionalItem, performanceAtr, calcResultAnyItem, dailyRecordDto, monthlyRecordDto));
        }
        //Listが空だった場合
        if(calcResultAnyItem.isEmpty()) {
            return new CalcResultOfAnyItem(this.optionalItemNo,
            							   Optional.of(0),
										   Optional.of(0),
										   Optional.of(0));
        }
        //一番最後の計算式の結果を取得
        ResultOfCalcFormula resultOfCalcFormula = calcResultAnyItem.get(calcResultAnyItem.size()-1);
        //一番最後の計算式の結果を基に任意項目の計算結果を作成
        CalcResultOfAnyItem result = new CalcResultOfAnyItem(this.optionalItemNo,
				   											 resultOfCalcFormula.getCount(),
				   											 resultOfCalcFormula.getTime(),
				   											 resultOfCalcFormula.getMoney());
        //上限下限チェック
        result = this.calcResultRange.checkRange(result, this.optionalItemAtr);
        
        return result;
    }

}
