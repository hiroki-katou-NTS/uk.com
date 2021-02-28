package nts.uk.cnv.dom.cnv.conversionsql;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.cnv.service.ConversionInfo;

@Getter
@AllArgsConstructor
public class Join {
	/** テーブル名 */
	public TableName tableName;
	/** 結合区分 */
	public JoinAtr joinAtr;
	/** 結合条件 */
	public List<OnSentence> onSentences;

	public String sql(ConversionInfo info) {
		return
				" " + joinAtr.getSql() + " " + tableName.fullName() + " AS " + tableName.getAlias() + "\r\n" +
				OnSentence.join(onSentences, info);
	}

}