package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive;

import lombok.Getter;

@Getter
/** 職場別インセンティブ単価の設定 */
public class IncentiveUnitPriceSetByWkp extends IncentiveUnitPriceSetCommon {
	
	/** 職場ID: 職場ID */
	private String workplaceId;
	
	private IncentiveUnitPriceSetByWkp(String companyId, String workplaceId) {
		super(companyId);
		this.workplaceId = workplaceId;
	}

	public static IncentiveUnitPriceSetByWkp create(String companyId, String workplaceId) {
		
		return new IncentiveUnitPriceSetByWkp(companyId, workplaceId);
	}
}
