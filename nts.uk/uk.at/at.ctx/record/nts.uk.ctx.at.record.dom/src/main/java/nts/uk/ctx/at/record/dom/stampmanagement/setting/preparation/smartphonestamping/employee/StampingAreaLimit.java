package nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee;

import nts.arc.enums.EnumAdaptor;

/**
 * 打刻エリア制限方法
 * @author NWS_vandv
 *
 */
public enum StampingAreaLimit {
	
	/** エリア制限しない */
	NO_AREA_RESTRICTION(0),

	/** エリア内のみ許可 */
	ALLOWED_ONLY_WITHIN_THE_AREA(1),
	
	/** 所属職場のみ許可 */
	ONLY_THE_WORKPLACE_BELONG_ALLOWED(2);

	/** The value. */
	public final int value;

	/** The name id. */
	
	private StampingAreaLimit(int value) {
		this.value = value;
		
	}
	public static StampingAreaLimit toEnum(int value){
		return EnumAdaptor.valueOf(value, StampingAreaLimit.class);
	}
	
}
