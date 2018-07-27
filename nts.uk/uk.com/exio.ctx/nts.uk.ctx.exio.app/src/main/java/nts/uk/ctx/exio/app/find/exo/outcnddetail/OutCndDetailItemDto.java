package nts.uk.ctx.exio.app.find.exo.outcnddetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;

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
	 * 会社ID
	 */
	private String cid;

	/**
	 * ユーザID
	 */
	private String userId;

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

	/**
	 * 検索コードリスト
	 */
	private List<SearchCodeListDto> listSearchCodeList;

	public static OutCndDetailItemDto fromDomain(OutCndDetailItem domain) {
		return new OutCndDetailItemDto(domain.getConditionSettingCd().v(), domain.getCategoryId().v().intValue(),
				domain.getCategoryItemNo().v().intValue(), domain.getSeriNum(), domain.getCid().get(),
				domain.getUserId().get(), domain.getConditionSymbol().value, 
				domain.getSearchNum().get().v(), domain.getSearchNumEndVal().get().v(), domain.getSearchNumStartVal().get().v(),
				domain.getSearchChar().get().v(), domain.getSearchCharEndVal().get().v(), domain.getSearchCharStartVal().get().v(), 
				domain.getSearchDate().get(), domain.getSearchDateEnd().get(), domain.getSearchDateStart().get(), 
				domain.getSearchClock().get().v(), domain.getSearchClockEndVal().get().v(), domain.getSearchClockStartVal().get().v(),
				domain.getSearchTime().get().v(), domain.getSearchTimeEndVal().get().v(), domain.getSearchTimeStartVal().get().v(), 
				domain.getListSearchCodeList().stream()
						.map(x -> SearchCodeListDto.fromDomain(x)).collect(Collectors.toList()));
	}
}