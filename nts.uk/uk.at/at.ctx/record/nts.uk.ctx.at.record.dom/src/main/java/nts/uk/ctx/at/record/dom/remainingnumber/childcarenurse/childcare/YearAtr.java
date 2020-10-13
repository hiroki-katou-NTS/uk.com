package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import nts.arc.enums.EnumAdaptor;

/**
 * 本年、翌年の期間区分
 *
 * @author yuri_tamakoshi
 */
public enum YearAtr {

	/**
	 * 本年
	 */
	THIS_YEAR(0),
	/**
	 * 翌年
	 */
	NEXT_YEAR(1);

	public int value;

	YearAtr(int type){
		this.value = type;
	}

	public static YearAtr toEnum(int value){
		return EnumAdaptor.valueOf(value, YearAtr.class);
	}
}