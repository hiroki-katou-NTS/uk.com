package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.ItemNameCode;

/**
 * 
 * @author thanh.tq 有効期間とサイクルの設定
 *
 */

@Getter
public class SetValidityPeriodCycle extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * カテゴリ区分
	 */
	private CategoryAtr categoryAtr;

	/**
	 * 項目名コード
	 */
	private ItemNameCode itemNameCd;

	/**
	 * サイクル設定
	 */
	private CycleSetting cycleSetting;

	/**
	 * 有効期間設定
	 */
	private ValidityPeriodSet validityPeriodSetting;

	public SetValidityPeriodCycle(String cid, int categoryAtr, String itemNameCd, int cycleSettingAtr, Integer january, Integer february, Integer march, Integer april,
			Integer may, Integer june, Integer july, Integer august, Integer september, Integer october, Integer november, Integer december,
			int periodAtr, Integer startYear, Integer endYear) {
		super();
		this.cid = cid;
		this.categoryAtr = EnumAdaptor.valueOf(categoryAtr, CategoryAtr.class);
		this.itemNameCd = new ItemNameCode(itemNameCd);
		this.cycleSetting = new CycleSetting(cycleSettingAtr, january, february, march, april, may, june, july, august,
				september, october, november, december);
		this.validityPeriodSetting = new ValidityPeriodSet(periodAtr, startYear, endYear);
	}

}
