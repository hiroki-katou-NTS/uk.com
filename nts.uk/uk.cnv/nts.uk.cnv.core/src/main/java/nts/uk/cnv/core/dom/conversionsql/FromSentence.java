package nts.uk.cnv.core.dom.conversionsql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nemunoki.oruta.shr.tabledefinetype.DatabaseSpec;

/**
 * FROM句
 * @author ai_muto
 *
 */
@AllArgsConstructor
@Getter
public class FromSentence {
	/** 基準テーブル **/
	private Optional<TableFullName> baseTable;
	/** 結合テーブル **/
	private List<Join> joinTables;

	public FromSentence() {
		this.baseTable = Optional.empty();
		this.joinTables = new ArrayList<>();
	}

	public void addJoin(Join join) {

		if (join.joinAtr == JoinAtr.Main) {
			if(!baseTable.isPresent()) {
				baseTable = Optional.of(join.tableName);
			}
			return;
		}

		if (baseTable.isPresent() && baseTable.get().name.equals(join.tableName)) return;
		if (joinTables.stream().anyMatch(j -> j.tableName.equals(join.tableName))) return;

		joinTables.add(join);
	}

	public String sql(DatabaseSpec spec) {
		if (!baseTable.isPresent()) return "";

		String join = "";
		if(!joinTables.isEmpty()) {
			join = String.join("\r\n", joinTables.stream().map(f -> f.sql(spec)).collect(Collectors.toList())) + "\r\n";
		}

		return " FROM "
			+ baseTable.get().fullName()
			+ (baseTable.get().getAlias() == null || baseTable.get().getAlias().isEmpty()
				? ""
				: " AS "+ baseTable.get().getAlias())
			+ "\r\n" + join;
	}
}

