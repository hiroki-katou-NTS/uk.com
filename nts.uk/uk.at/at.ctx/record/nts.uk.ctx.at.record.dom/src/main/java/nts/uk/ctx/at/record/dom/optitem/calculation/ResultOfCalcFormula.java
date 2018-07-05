package nts.uk.ctx.at.record.dom.optitem.calculation;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;

/**
 * 計算式の結果
 * @author keisuke_hoshina
 *
 */
@Getter
public class ResultOfCalcFormula {
	FormulaId calculationFormulaId;
	Optional<Integer> count;
	Optional<Integer> time;
	Optional<Integer> money;
	
	/**
	 * constructor
	 * @param id FormulaId
	 * @param count count
	 * @param time time 
	 * @param money money
	 */
	private ResultOfCalcFormula(FormulaId id,Optional<Integer> count, Optional<Integer> time, Optional<Integer> money) {
		this.calculationFormulaId = id;
		this.count = count;
		this.time = time;
		this.money = money;
	}
	
	
	/**
	 * ファクトリメソッド
	 * 任意項目計算後のOutput
	 * @param id 計算式ID
	 * @param optionalItemAtr 任意項目の属性
	 * @param element 計算値
	 * @return 計算式の結果クラス
	 */
	public static ResultOfCalcFormula of(FormulaId id,OptionalItemAtr optionalItemAtr,double calcValue) {
		switch(optionalItemAtr) {
			case NUMBER:
				return new ResultOfCalcFormula(id, Optional.of((int)calcValue), Optional.of(0), Optional.of(0));
			case TIME:
				return new ResultOfCalcFormula(id, Optional.of(0), Optional.of((int)calcValue), Optional.of(0));
			case AMOUNT:
				return new ResultOfCalcFormula(id, Optional.of(0), Optional.of(0), Optional.of((int)calcValue));
			default:
				throw new RuntimeException("unknown optionalItemAtr:"+optionalItemAtr);
		}
	}
	
	
	/**
	 *　渡された属性に一致する結果を返す
	 * @return
	 */
	public Optional<Integer> getResult(OptionalItemAtr optionalItemAtr){
		switch(optionalItemAtr) {
			case NUMBER:
				return this.count;
			case TIME:
				return this.time;
			case AMOUNT:
				return this.money;
			default:
				throw new RuntimeException("unknown optionalItemAtr:"+optionalItemAtr);
		}
	}
	
	
}
