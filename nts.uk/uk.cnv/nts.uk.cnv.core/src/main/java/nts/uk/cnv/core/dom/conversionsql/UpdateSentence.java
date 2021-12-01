package nts.uk.cnv.core.dom.conversionsql;

import java.util.ArrayList;
import java.util.List;

/**
 * UPDATE句
 * @author ai_muto
 *
 */
public class UpdateSentence {
	/** テーブル名 **/
	private TableFullName table;
	private List<SetSentence> set;

	public UpdateSentence(TableFullName table) {
		this.table = table;
		this.set = new ArrayList<>();
	}

	public void add(ColumnName column, ColumnExpression value) {
		set.add(new SetSentence(column, value));
	}

	public String sql() {
		return "UPDATE " + table.alias + "\r\n"
				+ SetSentence.join(this.set);
	}
}
