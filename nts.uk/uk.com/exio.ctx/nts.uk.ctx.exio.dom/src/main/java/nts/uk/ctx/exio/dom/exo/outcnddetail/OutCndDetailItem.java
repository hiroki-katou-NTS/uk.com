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
	private String cid;

	/**
	 * 条件設定コード
	 */
	private CompanyCndSetCd companyCndSetCd;

	/**
	 * ユーザID
	 */
	private String userId;

	/**
	 * 条件設定コード
	 */
	private CompanyCndSetCd conditionSettingCd;

	/**
	 * 条件記号
	 */
	private ConditionSymbol conditionSymbol;

	/**
	 * 検索数値
	 */
	private Optional<SearchNum> searchNum;

	/**
	 * 検索数値終了値
	 */
	private Optional<SearchNum> searchNumEndVal;

	/**
	 * 検索数値開始値
	 */
	private Optional<SearchNum> searchNumStartVal;

	/**
	 * 検索文字
	 */
	private Optional<SearchChar> searchChar;

	/**
	 * 検索文字終了値
	 */
	private Optional<SearchChar> searchCharEndVal;

	/**
	 * 検索文字開始値
	 */
	private Optional<SearchChar> searchCharStartVal;

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
	private Optional<SearchClock> searchClock;

	/**
	 * 検索時刻終了値
	 */
	private Optional<SearchClock> searchClockEndVal;

	/**
	 * 検索時刻開始値
	 */
	private Optional<SearchClock> searchClockStartVal;

	/**
	 * 検索時間
	 */
	private Optional<SearchTime> searchTime;

	/**
	 * 検索時間終了値
	 */
	private Optional<SearchTime> searchTimeEndVal;

	/**
	 * 検索時間開始値
	 */
	private Optional<SearchTime> searchTimeStartVal;

	public OutCndDetailItem(String categoryId,
			int categoryItemNo,
			String cid,
			String companyCndSetCd,
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
			int searchClock,
			int searchClockEndVal,
			int searchClockStartVal,
			int searchTime,
			int searchTimeEndVal,
			int searchTimeStartVal) {

		this.categoryId = categoryId;
		this.categoryItemNo = new CategoryItemNo(categoryItemNo);
		this.cid = cid;
		this.companyCndSetCd = new CompanyCndSetCd(companyCndSetCd);
		this.userId = userId;
		this.conditionSettingCd = new CompanyCndSetCd(conditionSettingCd);
		this.conditionSymbol = EnumAdaptor.valueOf(conditionSymbol, ConditionSymbol.class);
		this.searchNum = Optional.of(new SearchNum(searchNum));
		this.searchNumEndVal = Optional.of(new SearchNum(searchNumEndVal));
		this.searchNumStartVal = Optional.of(new SearchNum(searchNumStartVal)) ;
		this.searchChar = Optional.of(new SearchChar(searchChar));
		this.searchCharEndVal = Optional.of(new SearchChar(searchCharEndVal));
		this.searchCharStartVal = Optional.of(new SearchChar(searchCharStartVal));
		this.searchDate = Optional.of(searchDate);
		this.searchDateEnd = Optional.of(searchDateEnd);
		this.searchDateStart = Optional.of(searchDateStart);
		this.searchClock = Optional.of(new SearchClock(searchClock));
		this.searchClockEndVal = Optional.of(new SearchClock(searchClockEndVal));
		this.searchClockStartVal = Optional.of(new SearchClock(searchClockStartVal));
		this.searchTime = Optional.of(new SearchTime(searchTime));
		this.searchTimeEndVal = Optional.of(new SearchTime(searchTimeEndVal));
		this.searchTimeStartVal = Optional.of(new SearchTime(searchTimeStartVal));
	}

}
