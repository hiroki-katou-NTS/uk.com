package nts.uk.ctx.at.shared.app.find.ot.zerotime;

import java.util.List;

import lombok.Data;

/**
 * @author phongtq
 * 0時跨ぎ計算設定
 */

@Data
public class ZeroTimeDto {
	/** 会社ID */
	private String companyId;

	/** 0時跨ぎ計算を行なう */
	private int calcFromZeroTime;

	/** 法定内休日 */
	private int legalHd;

	/** 法定外休日 */
	private int nonLegalHd;

	/** 法定外祝日 */
	private int nonLegalPublicHd;

	/** 平日 */
	private int weekday1;

	/** 法定外休日 */
	private int nonLegalHd1;

	/** 法定外祝日 */
	private int nonLegalPublicHd1;

	/** 平日 */
	private int weekday2;

	/** 法定外休日 */
	private int legalHd2;

	/** 法定外祝日 */
	private int nonLegalHd2;

	/** 平日 */
	private int weekday3;

	/** 法定外休日 */
	private int legalHd3;

	/** 法定外休日 */
	private int nonLegalPublicHd3;
	
	/**平日から休日の0時跨ぎ設定*/
	private List<WeekdayHolidayDto> weekdayHoliday;
	
	/**休日から平日への0時跨ぎ設定*/
	private List<HdFromWeekdayDto> overdayHolidayAtten;
	
	/**休日から休日への0時跨ぎ設定*/
	private List<HdFromHdDto> overdayCalcHoliday;
	
	
}
