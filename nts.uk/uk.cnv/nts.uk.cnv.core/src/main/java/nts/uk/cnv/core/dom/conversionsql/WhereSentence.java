package nts.uk.cnv.core.dom.conversionsql;

import java.util.ArrayList;
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
	private final ColumnName left;

	/** 比較演算子 */
	private final RelationalOperator operator;

	/**
	 * 右辺
	 * 比較演算子がIsNullまたはIsNotNullの場合はempty
	 */
	private final Optional<ColumnExpression> right;
	
	public static WhereSentence equal(String column, ColumnExpression right) {
		return new WhereSentence(new ColumnName(column), RelationalOperator.Equal, Optional.of(right));
	}

	private String sql() {
		return left.sql() + " " + operator.getSign() + " " + right.orElse(new ColumnExpression("")).sql();
	}

	public static String join(List<WhereSentence> sentences) {
		return
				! sentences.isEmpty()
				? " WHERE " + "\r\n" +	String.join(" AND " + "\r\n", sentences.stream().map(s -> s.sql()).collect(Collectors.toList()))
				: "";
	}

	public static List<WhereSentence> parse(String condition) {
		List<WhereSentence> whereList = new ArrayList<>();

		for (String cond : condition.split("AND")) {
			for(RelationalOperator operator : RelationalOperator.values()) {
				if(cond.contains(operator.getSign())) {
					String[] expressions = cond.split(operator.getSign());

					 whereList.add(new WhereSentence(
							 new ColumnName("", expressions[0]),
							 operator,
							 Optional.of(new ColumnExpression(expressions[1]))
						));
					 break;
				}
			}
		}

		return whereList;
	}
}
