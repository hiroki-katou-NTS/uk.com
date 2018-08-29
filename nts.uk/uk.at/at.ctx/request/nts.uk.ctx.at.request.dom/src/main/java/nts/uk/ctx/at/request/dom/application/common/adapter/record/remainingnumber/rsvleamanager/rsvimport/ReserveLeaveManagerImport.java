package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport;

import lombok.Data;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.FundingYearHolidayGrantInfor;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.NumberReserYearRemain;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.YearlySupensionManageInfor;

@Data
public class ReserveLeaveManagerImport {
	/**
	 * 積立年休残数(仮)
	 */
	private NumberReserYearRemain number;
	
	/**
	 * 積立年休付与情報(仮)
	 */
	private FundingYearHolidayGrantInfor fundingYearHolidayGrantInfor;
	
	/**
	 * 積立年休管理情報(仮)
	 */
	private YearlySupensionManageInfor yearlySupensionManageInfor;
}
