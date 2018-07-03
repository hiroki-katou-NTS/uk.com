package nts.uk.ctx.exio.dom.exo.outcnddetail;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 出力条件詳細項目
 */
@AllArgsConstructor
@Getter
public class OutCndDetailItem extends AggregateRoot {

	/**
	 * カテゴリID
	 */
	private String categoryId;

	/**
	 * カテゴリ項目NO
	 */
	private CategoryItemNo categoryItemNo;

	/**
	 * 会社ID
	 */
	private Optional<String> cid;

	/**
	 * ユーザID
	 */
	private Optional<String> userId;

	/**
	 * 条件設定コード
	 */
	private Optional<ConditionSettingCd> conditionSettingCd;

	/**
	 * 条件記号
	 */
	private ConditionSymbol conditionSymbol;

	/**
	 * 検索数値
	 */
	private Optional<OutCndNumVal> searchNum;

	/**
	 * 検索数値終了値
	 */
	private Optional<OutCndNumVal> searchNumEndVal;

	/**
	 * 検索数値開始値
	 */
	private Optional<OutCndNumVal> searchNumStartVal;

	/**
	 * 検索文字
	 */
	private Optional<OutCndCharVal> searchChar;

	/**
	 * 検索文字終了値
	 */
	private Optional<OutCndCharVal> searchCharEndVal;

	/**
	 * 検索文字開始値
	 */
	private Optional<OutCndCharVal> searchCharStartVal;

	/**
	 * 検索日付
	 */
	private Optional<GeneralDate> searchDate;

	/**
	 * 検索日付終了値
	 */
	private Optional<GeneralDate> searchDateEnd;

	/**
	 * 検索日付開始値
	 */
	private Optional<GeneralDate> searchDateStart;

	/**
	 * 検索時刻
	 */
	private Optional<AttendanceClock> searchClock;

	/**
	 * 検索時刻終了値
	 */
	private Optional<AttendanceClock> searchClockEndVal;

	/**
	 * 検索時刻開始値
	 */
	private Optional<AttendanceClock> searchClockStartVal;

	/**
	 * 検索時間
	 */
	private Optional<AttendanceTime> searchTime;

	/**
	 * 検索時間終了値
	 */
	private Optional<AttendanceTime> searchTimeEndVal;

	/**
	 * 検索時間開始値
	 */
	private Optional<AttendanceTime> searchTimeStartVal;

	public OutCndDetailItem(String categoryId,
			int categoryItemNo,
			String cid,
			String userId,
			String conditionSettingCd,
			int conditionSymbol,
			BigDecimal searchNum,
			BigDecimal searchNumEndVal,
			BigDecimal searchNumStartVal,
			String searchChar,
			String searchCharEndVal,
			String searchCharStartVal,
			GeneralDate searchDate,
			GeneralDate searchDateEnd,
			GeneralDate searchDateStart,
			Integer searchClock,
			Integer searchClockEndVal,
			Integer searchClockStartVal,
			Integer searchTime,
			Integer searchTimeEndVal,
			Integer searchTimeStartVal) {

		this.categoryId = categoryId;
		this.categoryItemNo = new CategoryItemNo(categoryItemNo);
		this.cid = Optional.of(cid);
		this.userId = Optional.of(userId);
		this.conditionSettingCd = Optional.of(new ConditionSettingCd(conditionSettingCd));
		this.conditionSymbol = EnumAdaptor.valueOf(conditionSymbol, ConditionSymbol.class);
		this.searchNum = Optional.of(new OutCndNumVal(searchNum));
		this.searchNumEndVal = Optional.of(new OutCndNumVal(searchNumEndVal));
		this.searchNumStartVal = Optional.of(new OutCndNumVal(searchNumStartVal)) ;
		this.searchChar = Optional.of(new OutCndCharVal(searchChar));
		this.searchCharEndVal = Optional.of(new OutCndCharVal(searchCharEndVal));
		this.searchCharStartVal = Optional.of(new OutCndCharVal(searchCharStartVal));
		this.searchDate = Optional.of(searchDate);
		this.searchDateEnd = Optional.of(searchDateEnd);
		this.searchDateStart = Optional.of(searchDateStart);
		this.searchClock = Optional.of(new AttendanceClock(searchClock));
		this.searchClockEndVal = Optional.of(new AttendanceClock(searchClockEndVal));
		this.searchClockStartVal = Optional.of(new AttendanceClock(searchClockStartVal));
		this.searchTime = Optional.of(new AttendanceTime(searchTime));
		this.searchTimeEndVal = Optional.of(new AttendanceTime(searchTimeEndVal));
		this.searchTimeStartVal = Optional.of(new AttendanceTime(searchTimeStartVal));
	}

}
