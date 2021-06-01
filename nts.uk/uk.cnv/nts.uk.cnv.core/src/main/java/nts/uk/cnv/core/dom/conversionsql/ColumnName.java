package nts.uk.cnv.core.dom.conversionsql;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 列名
 * @author ai_muto
 *
 */
@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class ColumnName {

	/** テーブルエイリアス */
	private String alias;
	/** 列名 */
	private String name;

	public ColumnName(String name) {
		this.alias = "";
		this.name = name;
	}

	public String sql() {
		return (alias.isEmpty())
				? name
				: String.join(".", Arrays.asList(alias, name));
	}
}
