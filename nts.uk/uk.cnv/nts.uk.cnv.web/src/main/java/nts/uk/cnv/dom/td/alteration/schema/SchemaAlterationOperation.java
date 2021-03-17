package nts.uk.cnv.dom.td.alteration.schema;

public enum SchemaAlterationOperation {

	/** テーブルを追加 */
	CREATE_TABLE,
	
	/** テーブルを削除 */
	DROP_TABLE,
	
	/** テーブル名を変更 */
	RENAME_TABLE,
}
