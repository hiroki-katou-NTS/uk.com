package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;

@Getter
/** 場所別インセンティブ単価の設定 */
public class IncentiveUnitPriceSetByWlc extends IncentiveUnitPriceSetCommon {

	/** 会社ID: 会社ID */
	private WorkLocationCD workLocationCD;
	
	private IncentiveUnitPriceSetByWlc(String companyId, WorkLocationCD workLocationCD) {
		super(companyId);
		this.workLocationCD = workLocationCD;
	}

	public static IncentiveUnitPriceSetByWlc create(String companyId, WorkLocationCD workLocationCD) {
		
		return new IncentiveUnitPriceSetByWlc(companyId, workLocationCD);
	}
}
