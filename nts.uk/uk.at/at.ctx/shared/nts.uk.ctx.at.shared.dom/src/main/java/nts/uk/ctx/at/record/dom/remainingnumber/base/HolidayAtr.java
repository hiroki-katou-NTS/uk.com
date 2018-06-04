package nts.uk.ctx.at.record.dom.remainingnumber.base;

import lombok.AllArgsConstructor;

/**
 * 休日区分
 * @author admin
 *
 */
@AllArgsConstructor
public enum HolidayAtr {
	
	// 法定内休日	
	STATUTORY_HOLIDAYS(0),
	
	// 法定外休日
	NON_STATUTORYHOLIDAY(1),
	
	// 祝日
	PUBLICHOLIDAY(2);
	
	public final int value;
}
