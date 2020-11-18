package nts.uk.cnv.dom.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.cnv.dom.tabledefinetype.databasetype.DatabaseType;

/**
 * コンバート情報
 * @author ai_muto
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConversionInfo {
	/** DB種類 **/
	private DatabaseType datebaseType;
	/** 変換元DB名 **/
	private String sourceDatabaseName;
	/** 変換元スキーマ名 **/
	private String sourceSchema;
	/** 変換先DB名 **/
	private String targetDatabaseName;
	/** 変換先スキーマ名 **/
	private String targetSchema;

	/** 契約コード (オンプレの場合、オール0) **/
	private String ContractCode;


	public static ConversionInfo createDummry() {
		return new ConversionInfo(
				DatabaseType.sqlserver,
				"SOURCE_DB",
				"dbo",
				"TARGET_DB",
				"dbo",
				"000000000000"
			);
	}
}