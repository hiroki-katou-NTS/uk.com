/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.wageledger.data;

import java.util.List;

import nts.uk.file.pr.app.export.wageledger.data.newlayout.TotalData;
import nts.uk.file.pr.app.export.wageledger.data.share.HeaderReportData;
import nts.uk.file.pr.app.export.wageledger.data.share.ReportItemDto;

/**
 * The Class WLNewLayoutReportData.
 */
public class WLNewLayoutReportData {
	
	/** The header data. */
	public HeaderReportData headerData;
	
	/** The salary total data. */
	public TotalData salaryTotalData;
	
	/** The bonus total data. */
	public TotalData bonusTotalData;
	
	/** The position money before year end. */
	public Long positionMoneyBeforeYearEnd;
	
	/** The other money before year end. */
	public Long otherMoneyBeforeYearEnd;
	
	/** The salary payment items. */
	public List<ReportItemDto> salaryPaymentItems;
	
	/** The salary deduction items. */
	public List<ReportItemDto> salaryDeductionItems;
	
	/** The salary attendance items. */
	public List<ReportItemDto> salaryAttendanceItems;
	
	/** The bonus payment items. */
	public List<ReportItemDto> bonusPaymentItems;
	
	/** The bonus deduction items. */
	public List<ReportItemDto> bonusDeductionItems;
	
	/** The bonus attendance items. */
	public List<ReportItemDto> bonusAttendanceItems;
}
