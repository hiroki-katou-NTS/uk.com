package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string;

import org.apache.commons.lang3.StringUtils;

public enum FixedLengthReviseMethod {
	
	/** 0:前ゼロ */
	ZERO_BEFORE(0, "Enum_FixedLengthReviseMethod_ZERO_BEFORE"),
	
	/** 1:後ゼロ */
	ZERO_AFTER(1, "Enum_FixedLengthReviseMethod_ZERO_AFTER"),
	
	/** 2:前スペース */
	SPACE_BEFORE(2, "Enum_FixedLengthReviseMethod_SPACE_BEFORE"),
	
	/** 3: 後スペース */
	SPACE_AFTER(3, "Enum_FixedLengthReviseMethod_SPACE_AFTER"),
	
	;
	
	/** The value. */
	public final int value;
	
	/** The name id. */
	public final String nameId;
	
	private FixedLengthReviseMethod(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	public String complement(String target, int length) {
		switch (this) {
		case ZERO_BEFORE:
			return StringUtils.leftPad(target, length, "0");
		case ZERO_AFTER:
			return StringUtils.rightPad(target, length, "0");
		case SPACE_BEFORE:
			return StringUtils.leftPad(target, length, " ");
		case SPACE_AFTER:
			return StringUtils.rightPad(target, length, " ");
		default:
			throw new RuntimeException("存在しない編集方法が指定されました。");
		}
	}
}
