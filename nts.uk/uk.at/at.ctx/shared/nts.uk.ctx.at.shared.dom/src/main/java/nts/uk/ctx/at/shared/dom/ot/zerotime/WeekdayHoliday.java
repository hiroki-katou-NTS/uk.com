package nts.uk.ctx.at.shared.dom.ot.zerotime;

import java.math.BigDecimal;

/**
 * @author phongtq
 * 平日から休日の0時跨ぎ設定
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

/**
 * 
 * @author phongtq
 *
 */
@AllArgsConstructor
@Getter
public class WeekdayHoliday extends DomainObject {

	/** 会社ID */
	private String companyId;

	/** 変更前の残業枠NO */
	private BigDecimal overworkFrameNo;

	/** 変更後の残業枠NO */
	private int weekdayNo;

	/** 変更後の法定外休出NO */
	private int excessHolidayNo;

	/** 変更後の祝日休出NO */
	private int excessSphdNo;

	/**
	 * Create from Java Type of WeekdayHoliday
	 * 
	 * @param companyId
	 * @param overTimeFrameNo
	 * @param legalHdNo
	 * @param nonLegalHdNo
	 * @param nonLegalPublicHdNo
	 * @return
	 */
	public static WeekdayHoliday createFromJavaType(String companyId, BigDecimal overworkFrameNo, int weekdayNo,
			int excessHolidayNo, int excessSphdNo) {
		return new WeekdayHoliday(companyId, overworkFrameNo, weekdayNo,excessHolidayNo, excessSphdNo);
	}
	
	public int useFrameNo(HolidayAtr atr) {
		switch(atr) {
		case NON_STATUTORY_HOLIDAYS:
			return excessHolidayNo;
		case PUBLIC_HOLIDAY:
			return excessSphdNo;
		case STATUTORY_HOLIDAYS:
			return weekdayNo;
		default:
			throw new RuntimeException("unknown HolidayAtr:"+ atr);
		}
	}
}
