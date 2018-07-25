package nts.uk.ctx.at.shared.dom.specialholiday.periodinformation;

import nts.arc.enums.EnumAdaptor;

/**
 * 期限指定方法
 * 
 * @author tanlv
 *
 */
public enum TimeLimitSpecification {

	/**
	 * 無期限
	 */
	INDEFINITE_PERIOD(0),
	/**
	 * 有効期限を指定する
	 */
	AVAILABLE_GRANT_DATE_DESIGNATE(1),
	/**
	 * 次回付与日まで使用可能
	 */
	AVAILABLE_UNTIL_NEXT_GRANT_DATE(2),
	/**
	 * 使用可能期間を指定する
	 */
	USABLE_PERIOD(3);
	
	public int value;
	
	TimeLimitSpecification(int type){
		this.value = type;
	}
	
	public static TimeLimitSpecification toEnum(int value){
		return EnumAdaptor.valueOf(value, TimeLimitSpecification.class);
	}
	
}
