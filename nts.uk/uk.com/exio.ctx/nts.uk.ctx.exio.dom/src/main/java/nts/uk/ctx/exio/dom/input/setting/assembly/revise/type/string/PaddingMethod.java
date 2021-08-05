package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum PaddingMethod {
	
	/** 0:前ゼロ */
	ZERO_BEFORE(0, "Enum_PaddingMethod_ZERO_BEFORE"),
	
	/** 1:後ゼロ */
	ZERO_AFTER(1, "Enum_PaddingMethod_ZERO_AFTER"),
	
	/** 2:前スペース */
	SPACE_BEFORE(2, "Enum_PaddingMethod_SPACE_BEFORE"),
	
	/** 3: 後スペース */
	SPACE_AFTER(3, "Enum_PaddingMethod_SPACE_AFTER"),
	
	;
	
	/** The value. */
	public final int value;
	
	/** The name id. */
	public final String nameId;
	
	public static PaddingMethod valueOf(int value) {
		return EnumAdaptor.valueOf(value, PaddingMethod.class);
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
