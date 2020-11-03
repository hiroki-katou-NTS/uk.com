package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.EndDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.StartDate;

/**
 * @author thanhpv
 * 抽出期間（日単位）
 */

@Getter
@Setter
@NoArgsConstructor
public class ExtractionPeriodDaily extends ExtractionRangeBase {

	/** 開始日 */
	private StartDate startDate;

	/** 終了日 */
	private EndDate endDate;

	/**
	 * Instantiates a new Extraction period daily.
	 *
	 * @param extractionId    the extraction id
	 * @param extractionRange the extraction range
	 * @param startDate       the start date
	 * @param endDate         the end date
	 */
	public ExtractionPeriodDaily(String extractionId, int extractionRange, StartDate startDate, EndDate endDate) {
		super(extractionId, extractionRange);
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Checks constraint extraction period daily.
	 *
	 * @throws BusinessException the Business exception
	 */
	@Override
	protected void checkConstraint() throws BusinessException {
		// 開始日の指定方法が「実行日からの日数を指定する」で、終了日の指定方法が「実行日からの日数を指定する」の場合
		if (this.startDate.getStartSpecify() == StartSpecify.DAYS && this.endDate.getEndSpecify() == EndSpecify.DAYS) {
			PreviousClassification startDayPrevious = this.startDate.getStartDays().map(Days::getDayPrevious).orElse(null);
			PreviousClassification endDayPrevious = this.endDate.getEndDays().map(Days::getDayPrevious).orElse(null);
			// Checks constraint 1
			// ①開始日．前・先区分＝前で、終了日．前・先区分＝前の場合は
			if (startDayPrevious == PreviousClassification.BEFORE && endDayPrevious == PreviousClassification.BEFORE) {
				// 開始日．日数指定の日＞＝終了日．日数指定の日でなければならない
				if (this.startDate.getStartDays().get().getDay().lessThan(this.endDate.getEndDays().get().getDay())) {
					throw new BusinessException("Msg_812");
				}
			}
			// Checks constraint 2
			// ②開始日．前・先区分＝先で、終了日．前・先区分＝先の場合は
			if (startDayPrevious == PreviousClassification.AHEAD && endDayPrevious == PreviousClassification.AHEAD) {
				// 開始日．日数指定の日＜＝終了日．日数指定の日でなければならない
				if (this.startDate.getStartDays().get().getDay().greaterThan(this.endDate.getEndDays().get().getDay())) {
					throw new BusinessException("Msg_812");
				}
			}
			boolean startIsToday = this.startDate.getStartDays().map(Days::isToday).orElse(false);
			boolean endIsToday = this.endDate.getEndDays().map(Days::isToday).orElse(false);
			// Checks constraint 3
			// ③開始日．当日とする＝Trueで、終了日．当日とする＝Falseの場合、終了日．前・先区分＝先でなければならない
			if (startIsToday && !endIsToday && endDayPrevious != PreviousClassification.AHEAD) {
				throw new BusinessException("Msg_812");
			}
			// Checks constraint 4
			// ④開始日．当日とする＝Falseで、終了日．当日とする＝Trueの場合、開始日．前・先区分＝前でなければならない
			if (!startIsToday && endIsToday && startDayPrevious != PreviousClassification.BEFORE) {
				throw new BusinessException("Msg_812");
			}
		}

		// 開始日の指定方法が「締め日を指定する」で、終了日の指定方法が「締め日を指定する」の場合
		if (this.startDate.getStartSpecify() == StartSpecify.MONTH && this.endDate.getEndSpecify() == EndSpecify.MONTH) {
			PreviousClassification startMonthPrevious = this.startDate.getStartMonth().map(Month::getMonthPrevious).orElse(null);
			PreviousClassification endMonthPrevious = this.endDate.getEndMonth().map(Month::getMonthPrevious).orElse(null);
			// Checks constraint 1
			// ①開始日．前・先区分＝前で、終了日．前・先区分＝前の場合は
			if (startMonthPrevious == PreviousClassification.BEFORE && endMonthPrevious == PreviousClassification.BEFORE) {
				// 開始日．締め日指定の月数＞＝終了日．締め日指定の月数でなければならない
				if ((this.startDate.getStartMonth().get().getMonth()) < (this.endDate.getEndMonth().get().getMonth())) {
					throw new BusinessException("Msg_812");
				}
			}
			// Checks constraint 2
			// ②開始日．前・先区分＝先で、終了日．前・先区分＝先の場合は
			if (startMonthPrevious == PreviousClassification.AHEAD && endMonthPrevious == PreviousClassification.AHEAD) {
				// 開始日．締め日指定の月数＜＝終了日．締め日指定の月数でなければならない
				if ((this.startDate.getStartMonth().get().getMonth()) > (this.endDate.getEndMonth().get().getMonth())) {
					throw new BusinessException("Msg_812");
				}
			}
			boolean startIsCurrentMonth = this.startDate.getStartMonth().map(Month::isCurrentMonth).orElse(false);
			boolean endIsCurrentMonth = this.endDate.getEndMonth().map(Month::isCurrentMonth).orElse(false);
			// Checks constraint 3
			// ③開始日．当月とする＝Trueで、終了日．当月とする＝Falseの場合、終了日．前・先区分＝先でなければならない
			if (startIsCurrentMonth && !endIsCurrentMonth && endMonthPrevious != PreviousClassification.AHEAD) {
				throw new BusinessException("Msg_812");
			}
			// Checks constraint 4
			// ④開始日．当月とする＝Falseで、終了日．当月とする＝Trueの場合、開始日．前・先区分＝前でなければならない
			if (!startIsCurrentMonth && endIsCurrentMonth && startMonthPrevious != PreviousClassification.BEFORE) {
				throw new BusinessException("Msg_812");
			}
		}
	}

}
