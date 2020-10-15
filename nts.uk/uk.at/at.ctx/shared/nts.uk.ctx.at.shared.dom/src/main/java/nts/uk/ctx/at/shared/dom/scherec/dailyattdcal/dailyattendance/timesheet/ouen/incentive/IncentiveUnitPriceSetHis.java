package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.dailyattdcal.premiumitem.PriceUnit;

@Getter
/** インセンティブ単価の設定履歴 */
public class IncentiveUnitPriceSetHis implements DomainObject {

	/** 使用開始日: 年月日 */
	private GeneralDate startUseDate;
	
	/** 単価: 単価 */
	private PriceUnit priceUnit;
	
	private IncentiveUnitPriceSetHis(GeneralDate startUseDate, PriceUnit priceUnit) {
		this.startUseDate = startUseDate;
		this.priceUnit = priceUnit;
	}

	public static IncentiveUnitPriceSetHis create(GeneralDate startUseDate, PriceUnit priceUnit) {
		
		return new IncentiveUnitPriceSetHis(startUseDate, priceUnit);
	}

	public static IncentiveUnitPriceSetHis create(GeneralDate startUseDate, int priceUnit) {
		
		return new IncentiveUnitPriceSetHis(startUseDate, new PriceUnit(priceUnit));
	}
}
