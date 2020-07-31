package nts.uk.cnv.dom.conversionsql;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * FROM句
 * @author ai_muto
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FromSentence {
	/** 基準テーブル **/
	private Optional<TableName> baseTable;
	/** 結合テーブル **/
	private List<Join> joinTables;
	
	public void addJoin(Join join) {
		
		if (join.joinAtr == JoinAtr.Main) {
			if(!baseTable.isPresent()) {
				baseTable = Optional.of(join.tableName);
			}
			return;
		}
		
		if (joinTables.stream().anyMatch(j -> j.tableName.equals(join.tableName))) return;
		
		joinTables.add(join);
	}

	public String sql() {
		if (!baseTable.isPresent()) return "";
		
		String join = "";
		if(!joinTables.isEmpty()) {
			join = String.join("\r\n", joinTables.stream().map(f -> f.sql()).collect(Collectors.toList())) + "\r\n";
		}
		
		return " FROM " + baseTable.get().fullName() + " AS "+ baseTable.get().getAlias() + "\r\n" + join;
	}
}

