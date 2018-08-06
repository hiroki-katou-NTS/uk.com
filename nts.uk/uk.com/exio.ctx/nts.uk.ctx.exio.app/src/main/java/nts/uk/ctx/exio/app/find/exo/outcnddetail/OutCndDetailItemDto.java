package nts.uk.ctx.exio.app.find.exo.outcnddetail;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.OutCndDetailItemCustom;

/**
 * 出力条件詳細項目
 */
@AllArgsConstructor
@Value
public class OutCndDetailItemDto {
	/**
	 * 条件設定コード
	 */
	private String conditionSettingCd;

	/**
	 * カテゴリID
	 */
	private int categoryId;

	/**
	 * カテゴリ項目NO
	 */
	private int categoryItemNo;

	/**
	 * 連番
	 */
	private int seriNum;

	/**
	 * 条件記号
	 */
	private int conditionSymbol;

	/**
	 * 検索数値
	 */
	private BigDecimal searchNum;

	/**
	 * 検索数値終了値
	 */
	private BigDecimal searchNumEndVal;

	/**
	 * 検索数値開始値
	 */
	private BigDecimal searchNumStartVal;

	/**
	 * 検索文字
	 */
	private String searchChar;

	/**
	 * 検索文字終了値
	 */
	private String searchCharEndVal;

	/**
	 * 検索文字開始値
	 */
	private String searchCharStartVal;

	/**
	 * 検索日付
	 */
	private GeneralDate searchDate;

	/**
	 * 検索日付終了値
	 */
	private GeneralDate searchDateEnd;

	/**
	 * 検索日付開始値
	 */
	private GeneralDate searchDateStart;

	/**
	 * 検索時刻
	 */
	private Integer searchClock;

	/**
	 * 検索時刻終了値
	 */
	private Integer searchClockEndVal;

	/**
	 * 検索時刻開始値
	 */
	private Integer searchClockStartVal;

	/**
	 * 検索時間
	 */
	private Integer searchTime;

	/**
	 * 検索時間終了値
	 */
	private Integer searchTimeEndVal;

	/**
	 * 検索時間開始値
	 */
	private Integer searchTimeStartVal;

	private String joinedSearchCodeList;

	public static OutCndDetailItemDto fromDomain(OutCndDetailItemCustom domain) {
		return new OutCndDetailItemDto(domain.getConditionSettingCd().v(), domain.getCategoryId().v().intValue(),
				domain.getCategoryItemNo().v().intValue(), domain.getSeriNum(), domain.getConditionSymbol().value,
				domain.getSearchNum().isPresent() ? domain.getSearchNum().get().v() : null,
				domain.getSearchNumEndVal().isPresent() ? domain.getSearchNumEndVal().get().v() : null,
				domain.getSearchNumStartVal().isPresent() ? domain.getSearchNumStartVal().get().v() : null,
				domain.getSearchChar().isPresent() ? domain.getSearchChar().get().v() : null,
				domain.getSearchCharEndVal().isPresent() ? domain.getSearchCharEndVal().get().v() : null,
				domain.getSearchCharStartVal().isPresent() ? domain.getSearchCharStartVal().get().v() : null,
				domain.getSearchDate().isPresent() ? domain.getSearchDate().get() : null,
				domain.getSearchDateEnd().isPresent() ? domain.getSearchDateEnd().get() : null,
				domain.getSearchDateStart().isPresent() ? domain.getSearchDateStart().get() : null,
				domain.getSearchClock().isPresent() ? domain.getSearchClock().get().v() : null,
				domain.getSearchClockEndVal().isPresent() ? domain.getSearchClockEndVal().get().v() : null,
				domain.getSearchClockStartVal().isPresent() ? domain.getSearchClockStartVal().get().v() : null,
				domain.getSearchTime().isPresent() ? domain.getSearchTime().get().v() : null,
				domain.getSearchTimeEndVal().isPresent() ? domain.getSearchTimeEndVal().get().v() : null,
				domain.getSearchTimeStartVal().isPresent() ? domain.getSearchTimeStartVal().get().v() : null,
				domain.getJoinedSearchCodeList());
	}
}