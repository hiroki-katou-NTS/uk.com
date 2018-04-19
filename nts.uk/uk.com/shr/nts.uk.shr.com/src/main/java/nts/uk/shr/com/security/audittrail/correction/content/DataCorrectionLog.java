package nts.uk.shr.com.security.audittrail;

import lombok.Getter;

/**
 * 対象情報
 */
public class LogTargetInfo {
	
	/** 対象ユーザ */
	@Getter
	private final UserInfo targetUser;
	
	/** 対象データ */
	@Getter
	private final TargetDataType targetDataType;
	
	/** 対象データKEY情報 */
	@Getter
	private final TargetDataKey targetDataKey;
	
	/** 修正区分 */
	@Getter
	private final CorrectionAttr correctionAttr;
	
	/** 修正項目 */
	@Getter
	private final ItemInfo correctedItem;
	
	/** 並び順 */
	@Getter
	private final int showOrder;
	
	/** 備考 */
	@Getter
	private final String remark;

	/**
	 * 備考はOptional
	 * @param targetUser
	 * @param targetDataType
	 * @param targetDataKey
	 * @param correctionAttr
	 * @param correctedItem
	 * @param showOrder
	 */
	public LogTargetInfo(
			UserInfo targetUser,
			TargetDataType targetDataType,
			TargetDataKey targetDataKey,
			CorrectionAttr correctionAttr,
			ItemInfo correctedItem,
			int showOrder) {
		
		this(targetUser, targetDataType, targetDataKey, correctionAttr, correctedItem, showOrder, "");
	}
	
	public LogTargetInfo(
			UserInfo targetUser,
			TargetDataType targetDataType,
			TargetDataKey targetDataKey,
			CorrectionAttr correctionAttr,
			ItemInfo correctedItem,
			int showOrder,
			String remark) {
		
		this.targetUser = targetUser;
		this.targetDataType = targetDataType;
		this.targetDataKey = targetDataKey;
		this.correctionAttr = correctionAttr;
		this.correctedItem = correctedItem;
		this.showOrder = showOrder;
		this.remark = remark != null ? remark : ""; 
	}
}
