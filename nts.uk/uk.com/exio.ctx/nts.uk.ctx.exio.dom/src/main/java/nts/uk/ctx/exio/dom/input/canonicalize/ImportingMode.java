package nts.uk.ctx.exio.dom.input.canonicalize;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;

/**
 * 受入モード
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ImportingMode {

	/**
	 * 既存データが存在しないデータのみ受け入れる
	 * このモードの場合、既存データがあれば正準化データを生成しない
	 */
	INSERT_ONLY(1),
	
	/**
	 * 既存データが存在するデータのみ受け入れる
	 * 唯一、歯抜けのデータを受け入れることができるモード
	 * このモードの場合、既存データが無ければ正準化データを生成しない
	 */
	UPDATE_ONLY(2),

	/**
	 * 受入対象データを削除して受け入れる
	 * このモードの場合、削除すべき既存データの検出を正準化処理で行い、ドメイン移送側でその結果を参照して削除する
	 */
	DELETE_RECORD_BEFOREHAND(3),
	
	/**
	 * 受入対象グループのデータをすべて削除して受け入れる
	 * このモードの場合、正準化処理で削除判定はせず、ドメイン移送側で削除を実行する
	 */
	DELETE_DOMAIN_BEFOREHAND(4),
	;
	
	public final int value;
	
	public static ImportingMode valueOf(int value) {
		return EnumAdaptor.valueOf(value, ImportingMode.class);
	}
	
	/**
	 * レコードの存在有無によって受け入れできるかどうか判定する
	 * @param isExisting 既存データがあるか
	 * @return
	 */
	public boolean canImport(boolean isExisting) {
		
		// 対象データを受け入れないケースは以下の2つ：
		// 1: INSERT_ONLY だが既存データがある
		// 2: UPDATE_ONLY だが既存データが無い
		boolean notImportable =
				this == INSERT_ONLY && isExisting
				|| this == UPDATE_ONLY && !isExisting;
		
		return !notImportable;
	}
	
	public ConversionCodeType getType() {
		return (this == UPDATE_ONLY)
				? ConversionCodeType.UPDATE
				: ConversionCodeType.INSERT;
	}
}
