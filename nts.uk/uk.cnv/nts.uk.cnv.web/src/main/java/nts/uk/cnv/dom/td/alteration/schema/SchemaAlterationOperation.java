package nts.uk.cnv.dom.td.alteration.schema;

import lombok.AllArgsConstructor;
import nts.uk.cnv.dom.td.alteration.AlterationType;

@AllArgsConstructor
public enum SchemaAlterationOperation {

	/** テーブルを追加 */
	CREATE_TABLE(AlterationType.TABLE_CREATE),
	
	/** テーブルを削除 */
	DROP_TABLE(AlterationType.TABLE_DROP),
	
	/** テーブル名を変更 */
	RENAME_TABLE(AlterationType.TABLE_NAME_CHANGE),
	
	;
	
	public final AlterationType alterType;
}
