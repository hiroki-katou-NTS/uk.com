package nts.uk.ctx.at.shared.dom.ot.zerotime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
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

	
	/*法定外休日*/
	/** 平日 */
	private int weekday2;

	/** 法定内休日 */
	private int legalHd2;

	/** 法定外祝日 */
	private int nonLegalHd2;

	
	/*法定外祝日*/
	/** 平日 */
	private int weekday3;

	/** 法定内休日 */
	private int legalHd3;

	/** 法定外休日 */
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
	
	public boolean isCalcOneOverYesterDayToDay(WorkType beforeWorkType,WorkType nowWorkType) {
		//休日
		if(nowWorkType.getDailyWork().isHolidayOrPause() || nowWorkType.getDailyWork().isHolidayWork()) {
			//休日→休日
			if(beforeWorkType.getDailyWork().isHolidayOrPause() || beforeWorkType.getDailyWork().isHolidayWork()) {
				switch(beforeWorkType.afterDay()) {
				case STATUTORY_HOLIDAYS:
					switch(nowWorkType.beforeDay()) {
					case STATUTORY_HOLIDAYS:
						return false;
					case NON_STATUTORY_HOLIDAYS:
						return nonLegalHd1 == 1 ? true : false;
					case PUBLIC_HOLIDAY:
						return nonLegalPublicHd1 == 1 ? true : false; 
					default:
						throw new RuntimeException("unknown HolidayAtr otherDay"+nowWorkType.beforeDay());
					}
				case NON_STATUTORY_HOLIDAYS:
					switch(nowWorkType.beforeDay()) {
					case STATUTORY_HOLIDAYS:
						return legalHd2 == 1 ? true : false;
					case NON_STATUTORY_HOLIDAYS:
						return false;
					case PUBLIC_HOLIDAY:
						return nonLegalHd2 == 1 ? true : false;
					default:
						throw new RuntimeException("unknown HolidayAtr otherDay"+nowWorkType.beforeDay());
					}
				case PUBLIC_HOLIDAY:
					switch(nowWorkType.beforeDay()) {
					case STATUTORY_HOLIDAYS:
						return legalHd3 == 1 ? true : false;
					case NON_STATUTORY_HOLIDAYS:
						return nonLegalPublicHd3 == 1 ? true : false;
					case PUBLIC_HOLIDAY:
						return false;
					default:
						throw new RuntimeException("unknown HolidayAtr otherDay"+nowWorkType.beforeDay());
					}
				default:
					throw new RuntimeException("unknown HolidayAtr nowDay:"+nowWorkType.afterDay());
				}
			}
			//平日→休日
			else {
				switch(nowWorkType.beforeDay()) {
				case STATUTORY_HOLIDAYS:
					return legalHd == 1 ? true : false ;
				case NON_STATUTORY_HOLIDAYS:
					return nonLegalHd == 1 ? true : false ;
				case PUBLIC_HOLIDAY:
					return nonLegalPublicHd == 1 ? true : false ;
				default:
					throw new RuntimeException("unknown HolidayAtr otherDay"+beforeWorkType.beforeDay());
				}
			}
		}
		//平日
		else {
			//休日 →　平日
			if(beforeWorkType.getDailyWork().isHolidayOrPause()) {
				switch(beforeWorkType.afterDay()) {
				case STATUTORY_HOLIDAYS:
					return weekday1 == 1 ? true : false ;
				case NON_STATUTORY_HOLIDAYS:
					return weekday2 == 1 ? true : false ;
				case PUBLIC_HOLIDAY:
					return weekday3 == 1 ? true : false ;
				default:
					throw new RuntimeException("unknown HolidayAtr otherDay"+beforeWorkType.beforeDay());
				}
			}
			//平日→平日
			else {
				return false;
			}
		}
	}
	
	public boolean isCalcOneOverToDayTomorrow(WorkType otherWorkType,WorkType nowWorkType) {
		//休日
		if(nowWorkType.getDailyWork().isHolidayOrPause() || nowWorkType.getDailyWork().isHolidayWork()) {
			//休日→休日
			if(otherWorkType.getDailyWork().isHolidayOrPause() || otherWorkType.getDailyWork().isHolidayWork()) {
				switch(nowWorkType.afterDay()) {
				case STATUTORY_HOLIDAYS:
					switch(otherWorkType.beforeDay()) {
					case STATUTORY_HOLIDAYS:
						return false;
					case NON_STATUTORY_HOLIDAYS:
						return nonLegalHd1 == 1 ? true : false;
					case PUBLIC_HOLIDAY:
						return nonLegalPublicHd1 == 1 ? true : false; 
					default:
						throw new RuntimeException("unknown HolidayAtr otherDay"+otherWorkType.beforeDay());
					}
				case NON_STATUTORY_HOLIDAYS:
					switch(otherWorkType.beforeDay()) {
					case STATUTORY_HOLIDAYS:
						return legalHd2 == 1 ? true : false;
					case NON_STATUTORY_HOLIDAYS:
						return false;
					case PUBLIC_HOLIDAY:
						return nonLegalHd2 == 1 ? true : false;
					default:
						throw new RuntimeException("unknown HolidayAtr otherDay"+otherWorkType.beforeDay());
					}
				case PUBLIC_HOLIDAY:
					switch(otherWorkType.beforeDay()) {
					case STATUTORY_HOLIDAYS:
						return legalHd3 == 1 ? true : false;
					case NON_STATUTORY_HOLIDAYS:
						return nonLegalPublicHd3 == 1 ? true : false;
					case PUBLIC_HOLIDAY:
						return false;
					default:
						throw new RuntimeException("unknown HolidayAtr otherDay"+otherWorkType.beforeDay());
					}
				default:
					throw new RuntimeException("unknown HolidayAtr nowDay:"+nowWorkType.afterDay());
				}
			}
			//休日→平日
			else {
				switch(nowWorkType.afterDay()) {
				case STATUTORY_HOLIDAYS:
					return weekday1 == 1 ? true : false;
				case NON_STATUTORY_HOLIDAYS:
					return weekday2 == 1 ? true : false;
				case PUBLIC_HOLIDAY:
					return weekday3 == 1 ? true : false;
				default:
					throw new RuntimeException("unknown HolidayAtr:"+otherWorkType.beforeDay());				
				}
			}
		}
		//平日
		else {
			//平日→休日
			if(otherWorkType.getDailyWork().isHolidayOrPause() || otherWorkType.getDailyWork().isHolidayWork()) {
				switch(otherWorkType.beforeDay()) {
				case STATUTORY_HOLIDAYS:
					return legalHd == 1 ? true : false;
				case NON_STATUTORY_HOLIDAYS:
					return nonLegalHd == 1 ? true : false;
				case PUBLIC_HOLIDAY:
					return nonLegalPublicHd == 1 ? true : false;
				default:
					throw new RuntimeException("unknown HolidayAtr:"+otherWorkType.beforeDay());
				}
			}
			//平日→平日
			else {
				return false;
			}
		}
	}
}
