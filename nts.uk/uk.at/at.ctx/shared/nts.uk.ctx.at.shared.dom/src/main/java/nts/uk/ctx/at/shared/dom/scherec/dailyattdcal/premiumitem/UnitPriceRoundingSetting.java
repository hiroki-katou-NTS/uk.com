package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.premiumitem;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRounding;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.amountrounding.AmountUnit;

/**
 * 人件費単価丸め
 * @author daiki_ichioka
 *
 */
@Getter
@AllArgsConstructor
public class UnitPriceRoundingSetting {
	
	private UnitPriceRounding rounding;
	
	public BigDecimal round(BigDecimal timeAsMinutes) {
		AmountRoundingSetting amount = new AmountRoundingSetting(AmountUnit.ONE_YEN, AmountRounding.valueOf(this.rounding.nameId));
		return amount.round(timeAsMinutes);
	}
}
