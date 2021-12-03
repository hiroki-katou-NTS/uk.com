package nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee;

import nts.arc.enums.EnumAdaptor;

/**
 * 打刻エリア制限方法
 * @author NWS_vandv
 *
 */
public enum StampingAreaLimit {
	
	/** エリア制限しない */
	NO_AREA_RESTRICTION(0, "Enum_UseClassificationAtr_NOT_USE"),

	/** エリア内のみ許可 */
	ALLOWED_ONLY_WITHIN_THE_AREA(1, "Enum_UseClassificationAtr_USE"),
	
	/** 所属職場のみ許可 */
	ONLY_THE_WORKPLACE_BELONG_ALLOWED(2, "Enum_UseClassificationAtr_USE");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	private StampingAreaLimit(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	public static StampingAreaLimit toEnum(int value){
		return EnumAdaptor.valueOf(value, StampingAreaLimit.class);
	}
	
}
