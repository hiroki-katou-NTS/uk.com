package nts.uk.cnv.core.dom.conversiontable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nemunoki.oruta.shr.tabledefinetype.databasetype.DatabaseType;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;

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

	/** 移行用WorkDB名 **/
	private String workDatabaseName;
	/** 移行用Workスキーマ名 **/
	private String workSchema;

	/** 契約コード (オンプレの場合、オール0) **/
	private String ContractCode;

	private ConversionCodeType type;

	public static ConversionInfo createDummry() {
		return new ConversionInfo(
				DatabaseType.sqlserver,
				"ERP",
				"dbo",
				"UK",
				"dbo",
				"UK_CNV",
				"dbo",
				"000000000000",
				ConversionCodeType.INSERT
			);
	}

	public TableFullName getTargetTable(String tableName) {
		return new TableFullName(
				this.targetDatabaseName,
				this.targetSchema, tableName, type.getTagetAlias());
	}

	public TableFullName getSourceTable(String sourceName, String alias) {
		return new TableFullName(
				this.sourceDatabaseName,
				this.sourceSchema, sourceName, alias);
	}

	public Join getJoin(ConversionSource source) {
		return this.type == ConversionCodeType.INSERT
				? source.getMainJoin(sourceDatabaseName, sourceSchema)
				: source.getInnerJoin(sourceDatabaseName, sourceSchema);
	}
}