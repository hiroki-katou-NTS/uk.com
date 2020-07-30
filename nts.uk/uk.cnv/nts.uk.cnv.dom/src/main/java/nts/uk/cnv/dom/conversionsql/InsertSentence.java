package nts.uk.cnv.dom.conversionsql;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

/**
 * INSERT句
 * @author ai_muto
 *
 */
@AllArgsConstructor
public class InsertSentence {
	/** テーブル名 **/
	private TableName table;
	/** 列の式リスト **/
	private List<ColumnExpression> expressions;
	
	public void addExpression(ColumnExpression value) {
		expressions.add(value);
	}

	public String sql(String select) {
		return
				"INSERT INTO " + table.fullName() + " (" + "\r\n    " +
				String.join("," + "\r\n    ", expressions.stream().map(e -> e.sql()).collect(Collectors.toList())) + "\r\n" +
				")" + "\r\n" +
				"(" + "\r\n" +
				select +
				")";
	}
}
