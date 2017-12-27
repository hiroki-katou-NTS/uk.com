package nts.uk.ctx.at.record.dom.optitem.calculation;

import java.util.Optional;

import lombok.Getter;

/**
 * 計算式の結果
 * @author keisuke_hoshina
 *
 */
@Getter
public class ResultOfCalcFormula {
	String calculationFormulaId;
	Optional<Integer> count;
	Optional<Integer> time;
	Optional<Integer> money;
}
