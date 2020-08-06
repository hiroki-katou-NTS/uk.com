package nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param;

import lombok.AllArgsConstructor;

/**
 * 60H超休エラー
 * @author masaaki_jinno
 *
 */
@AllArgsConstructor
public enum HolidayOver60hError {
	/** 60H超休 時間単位超過有給残数不足エラー */
	SHORTAGE_AL_OF_UNIT_TIME(0);
	
	public final int value;
}
