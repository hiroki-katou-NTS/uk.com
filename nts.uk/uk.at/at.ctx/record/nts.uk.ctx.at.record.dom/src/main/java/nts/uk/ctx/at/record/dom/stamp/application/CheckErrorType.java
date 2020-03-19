package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.Arrays;
import java.util.List;

/**
 * エラー種類
 * @author phongtq
 *
 */
public enum CheckErrorType {
	
	/** 打刻漏れ */
	IMPRINT_LEAKAGE(0, "打刻漏れ", "CheckErrorType_IMPRINT_LEAKAGE"),

	/** 休日打刻 */
	HOKIDAY_EMBOSSING(1, "休日打刻", "CheckErrorType_HOKIDAY_EMBOSSING"),

	/** 残業乖離 */
	OVERTIME_DIVERGGENCE(2, "残業乖離", "CheckErrorType_OVERTIME_DIVERGGENCE");

	/** The value. */
	public int value;
	
	/** The name id. */
	public  String nameId;

	/** The description. */
	public  String description;

	/** The Constant values. */
	private final static CheckErrorType[] values = CheckErrorType.values();

	/**
	 * Instantiates a new closure id.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private CheckErrorType (int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the use division
	 */
	public static CheckErrorType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CheckErrorType val : CheckErrorType.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
	
	/**
	 * エラー種類に対応するエラーアラームを取得する
	 * @param value
	 * @return
	 */
	public List<String> getErrorAlarm(){
		if(this == CheckErrorType.IMPRINT_LEAKAGE)
			return  Arrays.asList("S001");
		if(this == CheckErrorType.HOKIDAY_EMBOSSING)
			return Arrays.asList("S005");
		return Arrays.asList("D001", "D003");
	} 
}
