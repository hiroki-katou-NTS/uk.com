package nts.uk.cnv.dom.conversionsql;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

/**
 * WHERE句
 * @author ai_muto
 *
 */
@AllArgsConstructor
public class WhereSentence {
	/** 左辺 */
	private ColumnName left;
		
	/** 比較演算子 */
	private RelationalOperator operator;

	/**
	 * 右辺
	 * 比較演算子がIsNullまたはIsNotNullの場合はempty
	 */
	private Optional<ColumnExpression> right;
	
	private String sql() {
		return left.sql() + " " + operator.getSign() + " " + right.orElse(new ColumnExpression()).sql();
	}
	
	public static String join(List<WhereSentence> sentences) {
		return
				! sentences.isEmpty()
				? " WHERE " + "\r\n" +	String.join(" AND " + "\r\n", sentences.stream().map(s -> s.sql()).collect(Collectors.toList()))
				: "";
	}
}
