package nts.uk.ctx.exio.infra.entity.exo.outcnddetail;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailItem;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeList;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 出力条件詳細項目
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_OUT_CND_DETAIL_ITEM")
public class OiomtOutCndDetailItem extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtOutCndDetailItemPk outCndDetailItemPk;

	/**
	 * ユーザID
	 */
	@Basic(optional = true)
	@Column(name = "USER_ID")
	public String userId;

	/**
	 * 条件記号
	 */
	@Basic(optional = false)
	@Column(name = "CONDITION_SYMBOL")
	public int conditionSymbol;

	/**
	 * 検索数値
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_NUM")
	public BigDecimal searchNum;

	/**
	 * 検索数値終了値
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_NUM_END_VAL")
	public BigDecimal searchNumEndVal;

	/**
	 * 検索数値開始値
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_NUM_START_VAL")
	public BigDecimal searchNumStartVal;

	/**
	 * 検索文字
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_CHAR")
	public String searchChar;

	/**
	 * 検索文字終了値
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_CHAR_END_VAL")
	public String searchCharEndVal;

	/**
	 * 検索文字開始値
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_CHAR_START_VAL")
	public String searchCharStartVal;

	/**
	 * 検索日付
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_DATE")
	public GeneralDate searchDate;

	/**
	 * 検索日付終了値
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_DATE_END")
	public GeneralDate searchDateEnd;

	/**
	 * 検索日付開始値
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_DATE_START")
	public GeneralDate searchDateStart;

	/**
	 * 検索時刻
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_CLOCK")
	public Integer searchClock;

	/**
	 * 検索時刻終了値
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_CLOCK_END_VAL")
	public Integer searchClockEndVal;

	/**
	 * 検索時刻開始値
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_CLOCK_START_VAL")
	public Integer searchClockStartVal;

	/**
	 * 検索時間
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_TIME")
	public Integer searchTime;

	/**
	 * 検索時間終了値
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_TIME_END_VAL")
	public Integer searchTimeEndVal;

	/**
	 * 検索時間開始値
	 */
	@Basic(optional = true)
	@Column(name = "SEARCH_TIME_START_VAL")
	public Integer searchTimeStartVal;

	@Override
	protected Object getKey() {
		return outCndDetailItemPk;
	}
	
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "CONDITION_SETTING_CD", referencedColumnName = "CONDITION_SETTING_CD", insertable = false, updatable = false)
	})
	private OiomtOutCndDetail oiomtOutCndDetail;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "oiomtOutCndDetailItem", orphanRemoval = true, fetch = FetchType.LAZY)
	public List<OiomtSearchCodeList> listOiomtSearchCodeList;

	public OiomtOutCndDetailItem(int categoryId, int categoryItemNo, int seriNum, String cid, String userId,
			String conditionSettingCd, int conditionSymbol, BigDecimal searchNum, BigDecimal searchNumEndVal,
			BigDecimal searchNumStartVal, String searchChar, String searchCharEndVal, String searchCharStartVal,
			GeneralDate searchDate, GeneralDate searchDateEnd, GeneralDate searchDateStart, Integer searchClock,
			Integer searchClockEndVal, Integer searchClockStartVal, Integer searchTime, Integer searchTimeEndVal,
			Integer searchTimeStartVal) {
		this.outCndDetailItemPk = new OiomtOutCndDetailItemPk(cid, conditionSettingCd, categoryId, categoryItemNo,
				seriNum);
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
	}

	public OutCndDetailItem toDomain() {
		return new OutCndDetailItem(this.outCndDetailItemPk.conditionSettingCd, this.outCndDetailItemPk.categoryId,
				this.outCndDetailItemPk.categoryItemNo, this.outCndDetailItemPk.seriNum, this.outCndDetailItemPk.cid,
				this.userId, this.conditionSymbol, this.searchNum, this.searchNumEndVal, this.searchNumStartVal,
				this.searchChar, this.searchCharEndVal, this.searchCharStartVal, this.searchDate, this.searchDateEnd,
				this.searchDateStart, this.searchClock, this.searchClockEndVal, this.searchClockStartVal,
				this.searchTime, this.searchTimeEndVal, this.searchTimeStartVal, this.getListSearchCodeList());
	}

	public List<SearchCodeList> getListSearchCodeList() {
		return this.listOiomtSearchCodeList.stream().map(x -> x.toDomain()).collect(Collectors.toList());
	}
}