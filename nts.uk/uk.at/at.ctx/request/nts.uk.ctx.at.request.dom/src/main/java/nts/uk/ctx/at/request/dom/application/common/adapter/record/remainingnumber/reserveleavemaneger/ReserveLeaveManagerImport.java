package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.reserveleavemaneger;

import lombok.Data;

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
