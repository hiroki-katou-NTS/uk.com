package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.validityperiodset;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset.SetValidityPeriodCycle;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 有効期間とサイクルの設定
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SET_PERIOD_CYCLE")
public class QpbmtSetPeriodCycle extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtSetPeriodCyclePk setPeriodCyclePk;

	/**
	 * サイクル設定区分
	 */
	@Basic(optional = false)
	@Column(name = "CYCLE_SETTING_ATR")
	public int cycleSettingAtr;

	/**
	 * 対象月リスト
	 */
	@Basic(optional = true)
	@Column(name = "month_1")
	public Integer january;

	/**
	 * 対象月リスト
	 */
	@Basic(optional = true)
	@Column(name = "month_2")
	public Integer february;

	/**
	 * 対象月リスト
	 */
	@Basic(optional = true)
	@Column(name = "month_3")
	public Integer march;

	/**
	 * 対象月リスト
	 */
	@Basic(optional = true)
	@Column(name = "month_4")
	public Integer april;

	/**
	 * 対象月リスト
	 */
	@Basic(optional = true)
	@Column(name = "month_5")
	public Integer may;

	/**
	 * 対象月リスト
	 */
	@Basic(optional = true)
	@Column(name = "month_6")
	public Integer june;

	/**
	 * 対象月リスト
	 */
	@Basic(optional = true)
	@Column(name = "month_7")
	public Integer july;

	/**
	 * 対象月リスト
	 */
	@Basic(optional = true)
	@Column(name = "month_8")
	public Integer august;

	/**
	 * 対象月リスト
	 */
	@Basic(optional = true)
	@Column(name = "month_9")
	public Integer september;

	/**
	 * 対象月リスト
	 */
	@Basic(optional = true)
	@Column(name = "month_10")
	public Integer october;

	/**
	 * 対象月リスト
	 */
	@Basic(optional = true)
	@Column(name = "month_11")
	public Integer november;

	/**
	 * 対象月リスト
	 */
	@Basic(optional = true)
	@Column(name = "month_12")
	public Integer december;

	/**
	 * 有効期間設定区分
	 */
	@Basic(optional = false)
	@Column(name = "PERIOD_ATR")
	public int periodAtr;

	/**
	 * 開始年
	 */
	@Basic(optional = true)
	@Column(name = "START_YEAR")
	public Integer startYear;

	/**
	 * 終了年
	 */
	@Basic(optional = true)
	@Column(name = "END_YEAR")
	public Integer endYear;

	@Override
	protected Object getKey() {
		return setPeriodCyclePk;
	}

	public SetValidityPeriodCycle toDomain() {
		return new SetValidityPeriodCycle(this.setPeriodCyclePk.cid, this.setPeriodCyclePk.categoryAtr, this.setPeriodCyclePk.itemNameCd, this.cycleSettingAtr, this.january,
				this.february, this.march, this.april, this.may, this.june, this.july, this.august, this.september,
				this.october, this.november, this.december, this.periodAtr, this.startYear, this.endYear);
	}

	public static QpbmtSetPeriodCycle toEntity(SetValidityPeriodCycle domain) {
		return new QpbmtSetPeriodCycle(new QpbmtSetPeriodCyclePk(domain.getCid(), domain.getCategoryAtr().value, domain.getItemNameCd().v()),
				domain.getCycleSetting().getCycleSettingAtr().value,
				domain.getCycleSetting().getMonthlyList().map(i -> i.getJanuary().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getFebruary().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getMarch().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getApril().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getMay().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getJune().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getJuly().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getAugust().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getSeptember().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getOctober().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getNovember().value).orElse(null),
				domain.getCycleSetting().getMonthlyList().map(i -> i.getDecember().value).orElse(null),
				domain.getValidityPeriodSetting().getPeriodAtr().value,
				domain.getValidityPeriodSetting().getYearPeriod().map(i -> i.start().v()).orElse(null),
				domain.getValidityPeriodSetting().getYearPeriod().map(i -> i.end().v()).orElse(null));
	}

}
