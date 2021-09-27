package nts.uk.cnv.core.dom.conversionsql;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * INSERT句
 * @author ai_muto
 *
 */
public class InsertSentence {
	/** テーブル名 **/
	private TableFullName table;
	/** 列の式リスト **/
	private List<ColumnExpression> expressions;

	public InsertSentence(TableFullName table) {
		this.table = table;
		this.expressions = new ArrayList<>();
	}

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
				");";
	}
}
