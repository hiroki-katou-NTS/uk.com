/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;

/**
 * The Class FormulaSetting.
 */
// 計算式設定
@Getter
public class FormulaSetting extends DomainObject {

	/** The minus segment. */
	// マイナス区分
	private MinusSegment minusSegment;

	/** The operator. */
	// 演算子
	private OperatorAtr operator;

	/** The formula setting items. */
	// 計算式設定項目
	private FormulaSettingItem leftItem;

	// 計算式設定項目
	/** The right item. */
	private FormulaSettingItem rightItem;

	/**
	 * Instantiates a new formula setting.
	 *
	 * @param memento the memento
	 */
	public FormulaSetting(FormulaSettingGetMemento memento) {
		this.minusSegment = memento.getMinusSegment();
		this.operator = memento.getOperatorAtr();
		this.leftItem = memento.getLeftItem();
		this.rightItem = memento.getRightItem();
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
		if (this.isBothItemInput()) {
			throw new BusinessException("Msg_420");
		}
		if (this.rightItem.getSettingMethod() == SettingMethod.NUMERICAL_INPUT && this.isDivideByZero()) {
			throw new BusinessException("Msg_638");
		}
	}

	/**
	 * Checks if is both item input.
	 *
	 * @return true, if is both item input
	 */
	public boolean isBothItemInput() {
		return this.leftItem.getSettingMethod().equals(SettingMethod.NUMERICAL_INPUT)
				&& this.rightItem.getSettingMethod().equals(SettingMethod.NUMERICAL_INPUT);
	}

	/**
	 * Checks if is both item select.
	 *
	 * @return true, if is both item select
	 */
	public boolean isBothItemSelect() {
		return this.leftItem.getSettingMethod().equals(SettingMethod.ITEM_SELECTION)
				&& this.rightItem.getSettingMethod().equals(SettingMethod.ITEM_SELECTION);
	}

	/**
	 * Checks if is operator add or sub.
	 *
	 * @return true, if is operator add or sub
	 */
	public boolean isOperatorAddOrSub() {
		return this.operator == OperatorAtr.ADD || this.operator == OperatorAtr.SUBTRACT;
	}

	/**
	 * Checks if is divide by zero.
	 *
	 * @return true, if is divide by zero
	 */
	private boolean isDivideByZero() {
		if (this.operator == OperatorAtr.DIVIDE && this.rightItem.getInputValue().isPresent()
				&& this.rightItem.getInputValue().get().v() == BigDecimal.ZERO) {
			return true;
		}
		return false;
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FormulaSettingSetMemento memento) {
		memento.setMinusSegment(this.minusSegment);
		memento.setOperatorAtr(this.operator);
		memento.setLeftItem(this.leftItem);
		memento.setRightItem(this.rightItem);
	}
	
	
	/**
	 * 計算式による計算
	 * @return
	 */
	public BigDecimal calculationBycalculationFormula(List<ResultOfCalcFormula> resultOfCalcFormulaList,OptionalItemAtr optionalItemAtr) {
		//計算項目の作成
		CalcItem calcItem = new CalcItem();
		//順番1の処理
		calcItem = createCalcItem(getFormulaSettingItem(SettingItemOrder.LEFT),calcItem,resultOfCalcFormulaList,optionalItemAtr);
		//順番2の処理
		calcItem = createCalcItem(getFormulaSettingItem(SettingItemOrder.RIGHT),calcItem,resultOfCalcFormulaList,optionalItemAtr);
		//計算項目から値を算出
		BigDecimal result = calc(calcItem);
		if(result.compareTo(BigDecimal.ZERO) < 0) {
			if(this.minusSegment.isTreatedAsZero()) {
				return BigDecimal.valueOf(0);
			}
		}
		return result;
	}
	
	
	/**
	 * 設定方法に基づいて計算項目を取得、作成する
	 * @return
	 */
	public CalcItem createCalcItem(FormulaSettingItem formulaSettingItem,CalcItem calcItem,List<ResultOfCalcFormula> resultOfCalcFormulaList,OptionalItemAtr optionalItemAtr) {
		if(formulaSettingItem.getSettingMethod().isItemSelection()) {//項目選択の場合
			if(formulaSettingItem.getFormulaItemId().isPresent()) {
				Optional<ResultOfCalcFormula> resultOfCalcFormula = getResultOfCalcFormula(resultOfCalcFormulaList,formulaSettingItem.getFormulaItemId().get());
				if(resultOfCalcFormula.isPresent()) {//計算式の結果が取得できた場合
					Optional<BigDecimal> result = resultOfCalcFormula.get().getResult(optionalItemAtr);
					if(result.isPresent()) {
						calcItem.setValueByOrder(formulaSettingItem.getDispOrder(),result.get());
						return calcItem;
					}
				}
			}
			//結果が取得できない場合0を入れる　←　これで良い？
			calcItem.setValueByOrder(formulaSettingItem.getDispOrder(),BigDecimal.valueOf(0));
			return calcItem;
		}
		if(formulaSettingItem.getInputValue().isPresent()) {
			//数値入力の場合
			calcItem.setValueByOrder(formulaSettingItem.getDispOrder(), formulaSettingItem.getInputValue().get().v());
			return calcItem;
		}
		//入力値が取得できない場合は0　←　これで良い？
		calcItem.setValueByOrder(formulaSettingItem.getDispOrder(), BigDecimal.valueOf(0));
		return calcItem;
	}
	
	/**
	 * 計算式の結果Listから計算式項目IDに一致する計算式の結果を取得する
	 * @param resultOfCalcFormulaList
	 * @param formulaItemId
	 * @return
	 */
	public Optional<ResultOfCalcFormula> getResultOfCalcFormula(List<ResultOfCalcFormula> resultOfCalcFormulaList,FormulaId formulaItemId){
		List<ResultOfCalcFormula> resultOfCalcFormula = resultOfCalcFormulaList.stream().filter(r -> r.getCalculationFormulaId().equals(formulaItemId)).collect(Collectors.toList());
		if(!resultOfCalcFormula.isEmpty()) {
			return Optional.of(resultOfCalcFormula.get(0));
		}
		return Optional.empty();
	}
	
	/**
	 * 指定された順番の計算式項目設定を取得する
	 * 必ず順番1、2の両方が存在する前提の処理（そうでない場合は修正が必要）
	 * @return
	 */
	public FormulaSettingItem getFormulaSettingItem(SettingItemOrder dispOrder) {
		if(this.leftItem.getDispOrder()==dispOrder) {
			return this.leftItem;
		}
		return this.rightItem;
	}
	
	/**
	 * 自身の持つ演算子を基に計算
	 * @param calcItem
	 * @return
	 */
	public BigDecimal calc(CalcItem calcItem) {
		switch(this.operator) {
		case ADD:
			return calcItem.getLeftItemValue().add(calcItem.getRightItemValue());
		case SUBTRACT:
			return calcItem.getLeftItemValue().subtract(calcItem.getRightItemValue());
		case MULTIPLY:
			return calcItem.getLeftItemValue().multiply(calcItem.getRightItemValue());
		case DIVIDE:
			if (calcItem.getRightItemValue().signum() == 0) return BigDecimal.valueOf(0);
			return calcItem.getLeftItemValue().divide(calcItem.getRightItemValue());
		default:
			throw new RuntimeException("unknown operator:"+operator);
		}
	}
	
	
	
}
