package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRounding;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountUnit;

/**
 * 人件費丸め設定
 * @author daiki_ichioka
 *
 */
@Getter
@AllArgsConstructor
public class PersonCostRoundingSetting {
	
	/** 単価＊割増率の丸め */
	private UnitPriceRoundingSetting unitPrice;
	
	/** 単価＊時間の丸め */
	private AmountRoundingSetting cost;
	
	/**
	 * デフォルト値で作成する
	 * @return 人件費丸め設定
	 */
	public static PersonCostRoundingSetting defaultValue() {
		return new PersonCostRoundingSetting(
				new UnitPriceRoundingSetting(UnitPriceRounding.TRUNCATION),
				new AmountRoundingSetting(AmountUnit.ONE_YEN, AmountRounding.TRUNCATION));
	}
}
