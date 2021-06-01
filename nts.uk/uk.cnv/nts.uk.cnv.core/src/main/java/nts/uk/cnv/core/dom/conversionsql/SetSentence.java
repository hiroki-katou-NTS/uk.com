package nts.uk.cnv.core.dom.conversionsql;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

/**
 * SET句
 * @author ai_muto
 *
 */
@AllArgsConstructor
public class SetSentence {
	/** 左辺 */
	private ColumnName left;

	/** 右辺 */
	private ColumnExpression right;

	private String sql() {
		return left.sql() + " = " + right.sql();
	}

	public static String join(List<SetSentence> sentences) {
		return " SET \r\n"
				+ String.join(",\r\n", sentences.stream().map(s -> s.sql()).collect(Collectors.toList()));
	}
}
