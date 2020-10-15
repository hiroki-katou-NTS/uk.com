package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive;

import lombok.Getter;

@Getter
/** 会社のインセンティブ単価の設定 */
public class IncentiveUnitPriceSetByCom extends IncentiveUnitPriceSetCommon {
	
	private IncentiveUnitPriceSetByCom(String companyId) {
		super(companyId);
	}

	public static IncentiveUnitPriceSetByCom create(String companyId) {
		
		return new IncentiveUnitPriceSetByCom(companyId);
	}
}
