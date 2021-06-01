package nts.uk.cnv.core.dom.conversionsql;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;

@Getter
@AllArgsConstructor
public class Join {
	/** テーブル名 */
	public TableFullName tableName;
	/** 結合区分 */
	public JoinAtr joinAtr;
	/** 結合条件 */
	public List<OnSentence> onSentences;

	public static Join createMain(TableFullName baseTableName) {
		return new Join(
				baseTableName,
				JoinAtr.Main,
				null
			);
	}

	public String sql(DatabaseSpec spec) {
		return
				" " + joinAtr.getSql() + " " + tableName.fullName() + " AS " + tableName.getAlias() + "\r\n" +
				OnSentence.join(onSentences, spec);
	}

}