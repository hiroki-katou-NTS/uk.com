package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * Enum : 半径
 * @author tutk
 *
 */
@AllArgsConstructor
public enum RadiusAtr {
	/** 50ｍ */
	M_50(0 , "50 M"),
	/** 100ｍ */
	M_100(1 , "100 M"),
	/** 200ｍ */
	M_200(2 , "200 M"),
	/** 300ｍ */
	M_300(3 , "300 M"),
	/** 400ｍ */
	M_400(4 , "400 M"),
	/** 500ｍ */
	M_500(5 , "500 M"),
	/** 600ｍ */
	M_600(6 , "600 M"),
	/** 700ｍ */
	M_700(7 , "700 M"),
	/** 800ｍ */
	M_800(8 , "800 M"),
	/** 900ｍ */
	M_900(9 , "900 M"),
	/** 1000ｍ */
	M_1000(10 , "1000 M");
	
	public int value;
	/** The description. */
	public String description;

	/** The Constant values. */
	private final static RadiusAtr[] values = RadiusAtr.values();
	

	public static RadiusAtr toEnum(int value){
		return EnumAdaptor.valueOf(value, RadiusAtr.class);
	}
	
	public int getValue() {
		switch (this.value) {
		case 0:
			return 50;
		default:
			return this.value * 100;

		}

	}
	public static RadiusAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RadiusAtr val : RadiusAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
}
