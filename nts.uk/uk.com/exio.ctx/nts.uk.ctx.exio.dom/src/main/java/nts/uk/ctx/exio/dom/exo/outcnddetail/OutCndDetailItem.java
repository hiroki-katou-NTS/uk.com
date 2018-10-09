package nts.uk.ctx.exio.dom.exo.outcnddetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exo.category.CategoryCd;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.ItemNo;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * 出力条件詳細項目
 */
@NoArgsConstructor
@Getter
@Setter
public class OutCndDetailItem extends AggregateRoot {

	/**
	 * 条件設定コード
	 */
	private ConditionSettingCd conditionSettingCd;

	/**
	 * カテゴリID
	 */
	private CategoryCd categoryId;

	/**
	 * カテゴリ項目NO
	 */
	private ItemNo categoryItemNo;

	/**
	 * 連番
	 */
	private int seriNum;

	/**
	 * 会社ID
	 */
	private Optional<String> cid;

	/**
	 * ユーザID
	 */
	private Optional<String> userId;

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

	/**
	 * 検索コードリスト
	 */
	private List<SearchCodeList> listSearchCodeList;

	public OutCndDetailItem(String conditionSettingCd, int categoryId, int categoryItemNo, int seriNum, String cid,
			String userId, int conditionSymbol, BigDecimal searchNum, BigDecimal searchNumEndVal,
			BigDecimal searchNumStartVal, String searchChar, String searchCharEndVal, String searchCharStartVal,
			GeneralDate searchDate, GeneralDate searchDateEnd, GeneralDate searchDateStart, Integer searchClock,
			Integer searchClockEndVal, Integer searchClockStartVal, Integer searchTime, Integer searchTimeEndVal,
			Integer searchTimeStartVal, List<SearchCodeList> listSearchCodeList) {
		this.conditionSettingCd = new ConditionSettingCd(conditionSettingCd);
		this.categoryId = new CategoryCd(categoryId);
		this.categoryItemNo = new ItemNo(categoryItemNo);
		this.seriNum = seriNum;
		this.cid = Objects.isNull(cid) ? Optional.empty() : Optional.of(cid);
		this.userId = Objects.isNull(userId) ? Optional.empty() :Optional.of(userId);
		this.conditionSymbol = EnumAdaptor.valueOf(conditionSymbol, ConditionSymbol.class);
		this.searchNum = Objects.isNull(searchNum) ? Optional.empty() :Optional.of(new OutCndNumVal(searchNum));
		this.searchNumEndVal = Objects.isNull(searchNumEndVal) ? Optional.empty() :Optional.of(new OutCndNumVal(searchNumEndVal));
		this.searchNumStartVal = Objects.isNull(searchNumStartVal) ? Optional.empty() :Optional.of(new OutCndNumVal(searchNumStartVal));
		this.searchChar = Objects.isNull(searchChar) ? Optional.empty() :Optional.of(new OutCndCharVal(searchChar));
		this.searchCharEndVal = Objects.isNull(searchCharEndVal) ? Optional.empty() :Optional.of(new OutCndCharVal(searchCharEndVal));
		this.searchCharStartVal = Objects.isNull(searchCharStartVal) ? Optional.empty() :Optional.of(new OutCndCharVal(searchCharStartVal));
		this.searchDate = Objects.isNull(searchDate) ? Optional.empty() :Optional.of(searchDate);
		this.searchDateEnd = Objects.isNull(searchDateEnd) ? Optional.empty() :Optional.of(searchDateEnd);
		this.searchDateStart = Objects.isNull(searchDateStart) ? Optional.empty() :Optional.of(searchDateStart);
		this.searchClock = Objects.isNull(searchClock) ? Optional.empty() :Optional.of(new AttendanceClock(searchClock));
		this.searchClockEndVal = Objects.isNull(searchClockEndVal) ? Optional.empty() :Optional.of(new AttendanceClock(searchClockEndVal));
		this.searchClockStartVal = Objects.isNull(searchClockStartVal) ? Optional.empty() :Optional.of(new AttendanceClock(searchClockStartVal));
		this.searchTime = Objects.isNull(searchTime) ? Optional.empty() :Optional.of(new AttendanceTime(searchTime));
		this.searchTimeEndVal = Objects.isNull(searchTimeEndVal) ? Optional.empty() :Optional.of(new AttendanceTime(searchTimeEndVal));
		this.searchTimeStartVal = Objects.isNull(searchTimeStartVal) ? Optional.empty() :Optional.of(new AttendanceTime(searchTimeStartVal));
		this.listSearchCodeList = listSearchCodeList;
	}

	public OutCndDetailItem(ConditionSettingCd conditionSettingCd, CategoryCd categoryId, ItemNo categoryItemNo,
			int seriNum, Optional<String> cid, Optional<String> userId, ConditionSymbol conditionSymbol,
			Optional<OutCndNumVal> searchNum, Optional<OutCndNumVal> searchNumEndVal,
			Optional<OutCndNumVal> searchNumStartVal, Optional<OutCndCharVal> searchChar,
			Optional<OutCndCharVal> searchCharEndVal, Optional<OutCndCharVal> searchCharStartVal,
			Optional<GeneralDate> searchDate, Optional<GeneralDate> searchDateEnd,
			Optional<GeneralDate> searchDateStart, Optional<AttendanceClock> searchClock,
			Optional<AttendanceClock> searchClockEndVal, Optional<AttendanceClock> searchClockStartVal,
			Optional<AttendanceTime> searchTime, Optional<AttendanceTime> searchTimeEndVal,
			Optional<AttendanceTime> searchTimeStartVal, List<SearchCodeList> listSearchCodeList) {
		super();
		this.conditionSettingCd = conditionSettingCd;
		this.categoryId = categoryId;
		this.categoryItemNo = categoryItemNo;
		this.seriNum = seriNum;
		this.cid = cid;
		this.userId = userId;
		this.conditionSymbol = conditionSymbol;
		this.searchNum = searchNum;
		this.searchNumEndVal = searchNumEndVal;
		this.searchNumStartVal = searchNumStartVal;
		this.searchChar = searchChar;
		this.searchCharEndVal = searchCharEndVal;
		this.searchCharStartVal = searchCharStartVal;
		this.searchDate = searchDate;
		this.searchDateEnd = searchDateEnd;
		this.searchDateStart = searchDateStart;
		this.searchClock = searchClock;
		this.searchClockEndVal = searchClockEndVal;
		this.searchClockStartVal = searchClockStartVal;
		this.searchTime = searchTime;
		this.searchTimeEndVal = searchTimeEndVal;
		this.searchTimeStartVal = searchTimeStartVal;
		this.listSearchCodeList = listSearchCodeList;
	}

}
