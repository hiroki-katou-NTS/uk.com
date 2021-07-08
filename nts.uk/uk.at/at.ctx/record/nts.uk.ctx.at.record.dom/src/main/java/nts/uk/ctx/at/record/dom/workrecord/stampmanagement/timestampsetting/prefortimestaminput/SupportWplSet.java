package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

/**
 * 応援職場設定方法
 * @author ThanhPV
 *
 */
public enum SupportWplSet {
	
	/** 打刻職場を利用 */
	USE_THE_STAMPED_WORKPLACE(0, "打刻職場を利用"),

	/** 打刻時に選択 */
	SELECT_AT_THE_TIME_OF_STAMPING(1, "打刻時に選択");

	/** The value. */
	public int value;
	
	/** The name id. */
	public  String nameId;

	/** The Constant values. */
	private final static SupportWplSet[] values = SupportWplSet.values();

	private SupportWplSet (int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	public static SupportWplSet valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SupportWplSet val : SupportWplSet.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
