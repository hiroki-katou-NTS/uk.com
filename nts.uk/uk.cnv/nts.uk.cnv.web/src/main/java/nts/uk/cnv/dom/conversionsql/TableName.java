package nts.uk.cnv.dom.conversionsql;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * テーブル名
 * @author ai_muto
 */
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TableName {
//	/** DBインスタンス名 ex) PCNAME\MSSQLSERVER 
//	 * リンクサーバーの機能の使用を想定、いったん保留
//	 */
//	protected String instanceName;
	
	/** DB名 ex) KINJIROU **/
	protected String databaseName;
	
	/** スキーマ名 ex) dbo **/
	protected String schema;
	
	/** テーブル名 ex) BPSMT_PERSON */
	protected String name;
	
	/** エイリアス */
	protected String alias;

	public String fullName() {
		return String.join(".", Arrays.asList(this.databaseName, this.schema, this.name));
	}
}