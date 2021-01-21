package nts.uk.ctx.pereg.infra.repository.mastercopy;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 既にデータが存在する時の処理方法
 */
@RequiredArgsConstructor
public enum CopyMethodOnConflict {

	/** コピーしない */
	DO_NOTHING(0),
	
	/** 既存データを全て削除してコピー */
	REPLACE_ALL(1),
	
	/** 重複しないデータのみ追加 */
	ADD_NEW_ONLY(2),
	;
	
	public final int value;
	
	public static CopyMethodOnConflict valueOf(int value) {
		return EnumAdaptor.valueOf(value, CopyMethodOnConflict.class);
	}
}
