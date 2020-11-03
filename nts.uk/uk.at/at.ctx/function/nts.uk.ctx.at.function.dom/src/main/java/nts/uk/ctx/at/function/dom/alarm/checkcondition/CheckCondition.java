package nts.uk.ctx.at.function.dom.alarm.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeCheckPeriod36;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Domain チェック条件
 *
 * @author nws-minhnb
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CheckCondition extends DomainObject {

	/**
	 * アラームリストのカテゴリ
	 */
	private AlarmCategory alarmCategory;

	/**
	 * チェック条件コード一覧
	 *
	 * @see AlarmCheckConditionByCategory
	 */
	private List<AlarmCheckConditionCode> checkConditionList;

	/**
	 * 抽出期間
	 */
	private List<ExtractionRangeBase> extractPeriodList;

	/**
	 * 36協定チェック期間
	 */
	private Optional<AgreeCheckPeriod36> optionalAgreeCheckPeriod36;

	/**
	 * Gets check condition list.
	 *
	 * @return the check condition <code>String</code> list
	 */
	public List<String> getCheckConditionList() {
		return this.checkConditionList.stream().map(AlarmCheckConditionCode::v).collect(Collectors.toList());
	}

	/**
	 * 日次
	 *
	 * @return boolean
	 */
	public boolean isDaily() {
		return this.alarmCategory == AlarmCategory.DAILY;
	}

	/**
	 * 月次
	 *
	 * @return boolean
	 */
	public boolean isMonthly() {
		return this.alarmCategory == AlarmCategory.MONTHLY;
	}

	/**
	 * 複数月
	 *
	 * @return boolean
	 */
	public boolean isMultipleMonth() {
		return this.alarmCategory == AlarmCategory.MULTIPLE_MONTH;
	}

	/**
	 * スケジュール4週
	 *
	 * @return boolean
	 */
	public boolean is4W4D() {
		return this.alarmCategory == AlarmCategory.SCHEDULE_4WEEK;
	}

	/**
	 * 工数チェック
	 *
	 * @return boolean
	 */
	public boolean isManHourCheck() {
		return this.alarmCategory == AlarmCategory.MAN_HOUR_CHECK;
	}

	/**
	 * ３６協定
	 *
	 * @return boolean
	 */
	public boolean isAgrrement() {
		return this.alarmCategory == AlarmCategory.AGREEMENT;
	}

	/**
	 * 年休
	 *
	 * @return boolean
	 */
	public boolean isAttHoliday() {
		return this.alarmCategory == AlarmCategory.ATTENDANCE_RATE_FOR_HOLIDAY;
	}

}
