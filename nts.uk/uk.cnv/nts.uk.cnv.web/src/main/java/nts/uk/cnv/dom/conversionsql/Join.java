package nts.uk.cnv.dom.conversionsql;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Join {
	/** テーブル名 */
	public TableName tableName;
	/** 結合区分 */
	public JoinAtr joinAtr;
	/** 結合条件 */
	public List<OnSentence> onSentences;

	public String sql() {
		return
				" " + joinAtr.getSql() + " " + tableName.fullName() + " AS " + tableName.getAlias() + "\r\n" +
				OnSentence.join(onSentences);
	}
	
}