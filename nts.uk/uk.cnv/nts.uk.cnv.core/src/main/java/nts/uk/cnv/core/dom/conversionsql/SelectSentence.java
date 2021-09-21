package nts.uk.cnv.core.dom.conversionsql;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SELECT句
 * @author ai_muto
 *
 */
@AllArgsConstructor
@Getter
public class SelectSentence {
	private ColumnExpression expression;

	private TreeMap<FormatType, String> formatTable;

	private String columnAlias;

	public String sql() {
		String formatedExpression = this.expression.sql();

		for ( String format : formatTable.values() ) {
				Object[] expressions = createParam(formatedExpression, format);

				formatedExpression = String.format(format, expressions);
		}
		return formatedExpression + " AS " + this.columnAlias;
	}

	/** format中のパラメータの出現回数に合わせて可変長引数のパラメータを生成する */
	private Object[] createParam(String formatedExpression, String format) {
		int count = (format.length() - format.replace("%s", "").length()) / 2;
		Object[] expressions = new String[count];
		for ( int i=0; i<count; i++) expressions[i] = formatedExpression;
		return expressions;
	}

	public static String join(List<SelectSentence> sentences) {
		return
				" SELECT " + "\r\n    " +
				String.join("," + "\r\n    " , sentences.stream().map(s -> s.sql()).collect(Collectors.toList()));
	}

	public static SelectSentence createNotFormat(String alias, String column, String columnAlias) {
		return new SelectSentence(
				new ColumnExpression(alias, column),
				new TreeMap<>(),
				columnAlias
			);
	}
}
