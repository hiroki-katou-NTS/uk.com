package nts.uk.ctx.at.shared.dom.ot.zerotime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
/**
 * @author phongtq
 * 0時跨ぎ計算設定
 */
@AllArgsConstructor
@Getter
@Builder
public class ZeroTime extends AggregateRoot {

	/** 会社ID */
	private String companyId;

	/** 0時跨ぎ計算を行なう */
	private int calcFromZeroTime;

	/*平日0時跨ぎ*/
	/** 法定内休日 */
	private int legalHd;

	/** 法定外休日 */
	private int nonLegalHd;

	/** 法定外祝日 */
	private int nonLegalPublicHd;
	
	
	/*法定内休日　0時跨ぎ*/
	/** 平日 */
	private int weekday1;

	/** 法定外休日 */
	private int nonLegalHd1;

	/** 法定外祝日 */
	private int nonLegalPublicHd1;

	
	/*法定外祝日*/
	/** 平日 */
	private int weekday2;

	/** 法定内休日 */
	private int legalHd2;

	/** 法定外祝日 */
	private int nonLegalHd2;

	
	/*法定外休日*/
	/** 平日 */
	private int weekday3;

	/** 法定内休日 */
	private int legalHd3;

	/** 法定外祝日 */
	private int nonLegalPublicHd3;
	
	
	
	private List<WeekdayHoliday> weekdayHoliday;
	
	private List<HdFromWeekday> overdayHolidayAtten;
	
	private List<HdFromHd> overdayCalcHoliday;

	public static ZeroTime createFromJavaType(String companyId, int calcFromZeroTime, int legalHd, int nonLegalHd,
			int nonLegalPublicHd, int weekday1, int nonLegalHd1, int nonLegalPublicHd1,
			int weekday2, int legalHd2, int nonLegalHd2, int weekday3, int legalHd3,
			int nonLegalPublicHd3,List<WeekdayHoliday>  weekdayHoliday, List<HdFromWeekday> overdayHolidayAtten, List<HdFromHd> overdayCalcHoliday) {
		return new ZeroTime(companyId, calcFromZeroTime, legalHd, nonLegalHd, nonLegalPublicHd,
				weekday1, nonLegalHd1, nonLegalPublicHd1, weekday2, legalHd2,
				nonLegalHd2, weekday3, legalHd3, nonLegalPublicHd3, weekdayHoliday, overdayHolidayAtten, overdayCalcHoliday);
	}
}
