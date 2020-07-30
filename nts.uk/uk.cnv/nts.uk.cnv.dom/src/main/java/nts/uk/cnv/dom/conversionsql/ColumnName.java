package nts.uk.cnv.dom.conversionsql;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 列名
 * @author ai_muto
 *
 */
@NoArgsConstructor
@AllArgsConstructor
public class ColumnName {

	/** テーブルエイリアス */
	private String alias;
	/** 列名 */
	private String name;
	
	public String sql() {
		return String.join(".", Arrays.asList(alias, name));
	}
}
