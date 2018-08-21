package nts.uk.shr.com.security.audittrail.correction.content.pereg;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 情報操作区分
 */
@RequiredArgsConstructor
public enum InfoOperateAttr {

	/** 追加 */
	ADD(1),
	
	/** 更新 */
	UPDATE(2),
	
	/** 削除 */
	DELETE(3),
	
	/** 履歴追加 */
	ADD_HISTORY(4),
	
	/** 履歴削除 */
	DELETE_HISTORY(5),
	
	;
	public final int value;
	
	public static InfoOperateAttr valueOf(int value) {
		return EnumAdaptor.valueOf(value, InfoOperateAttr.class);
	}
}
