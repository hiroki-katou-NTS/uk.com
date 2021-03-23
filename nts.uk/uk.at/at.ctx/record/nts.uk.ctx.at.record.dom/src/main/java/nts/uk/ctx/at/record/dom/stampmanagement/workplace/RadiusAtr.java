package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import nts.arc.enums.EnumAdaptor;

/**
 * Enum : 半径
 * @author tutk
 *
 */
public enum RadiusAtr {
	/** 50ｍ */
	M_50(0),
	/** 100ｍ */
	M_100(1),
	/** 200ｍ */
	M_200(2),
	/** 300ｍ */
	M_300(3),
	/** 400ｍ */
	M_400(4),
	/** 500ｍ */
	M_500(5),
	/** 600ｍ */
	M_600(6),
	/** 700ｍ */
	M_700(7),
	/** 800ｍ */
	M_800(8),
	/** 900ｍ */
	M_900(9),
	/** 1000ｍ */
	M_1000(10);
	
	public int value;
	
	RadiusAtr(int type){
		this.value = type;
	}
	
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
	
}
