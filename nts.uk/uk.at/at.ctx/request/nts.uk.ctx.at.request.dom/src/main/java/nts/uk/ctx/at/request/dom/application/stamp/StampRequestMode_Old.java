package nts.uk.ctx.at.request.dom.application.stamp;

/**
 * 
 * @author Doan Duy Hung
 *	
 */
/**
 * 
 * 打刻申請モード
 *
 */
public enum StampRequestMode_Old {
	
	/**
	 * 外出許可
	 */
	STAMP_GO_OUT_PERMIT(0,"外出許可"),
	
	/**
	 * 出退勤漏れ
	 */
	STAMP_WORK(1,"出退勤漏れ"),
	
	/**
	 * 打刻取消
	 */
	STAMP_CANCEL(2,"打刻取消"),
	
	/**
	 * レコーダイメージ
	 */
	STAMP_ONLINE_RECORD(3,"レコーダイメージ"),
	
	/**
	 * その他
	 */
	OTHER(4,"その他");
	
	public final int value;
	
	public final String name;
	
	StampRequestMode_Old(int value, String name){
		this.value = value;
		this.name = name;
	}
	
	public static StampRequestMode_Old valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (StampRequestMode_Old val : StampRequestMode_Old.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
