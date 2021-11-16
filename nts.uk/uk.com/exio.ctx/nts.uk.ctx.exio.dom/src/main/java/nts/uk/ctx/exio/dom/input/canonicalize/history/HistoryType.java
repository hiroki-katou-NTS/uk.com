package nts.uk.ctx.exio.dom.input.canonicalize.history;

public enum HistoryType {
	//* 履歴*/
	NORMAL,
	//* 連続かつ永続する履歴*/
	PERSISTENT,
	//* 連続する履歴*/
	CONTINUOUS,
	//* 連続かつ永続する履歴（常に1つ以上存在）*/
	PERSISTENERESIDENT,
	//* 重複禁止する履歴 */
	UNDUPLICATABLE,
	;
	
	
	public Class<?> GetHistoryClass(){
		switch(this){
			case NORMAL:
				return ExternalImportHistory.class;
			case PERSISTENT:
				return ExternalImportPersistentHistory.class;
			case CONTINUOUS:
				return ExternalImportContinuousHistory.class;
			case PERSISTENERESIDENT:
				return ExternalImportPersistentResidentHistory.class;
			case UNDUPLICATABLE:
				return ExternalImportUnduplicatableHistory.class;
			default:
				throw new RuntimeException("unknown history type:"+this);
		}
	}
}
