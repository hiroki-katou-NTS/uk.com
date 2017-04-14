package nts.uk.ctx.pr.report.app.payment.refundsetting.refundlayout.command;

import lombok.Data;

@Data
public class InsertUpdateRefundLayoutCommand {
	
	private int printType;

	private int usingZeroSettingCtg;

	private int printYearMonth;

	private int paymentCellNameCtg;

	private int isShaded;

	private int bordWidth;

	private int showCompName;

	private int showCompAddInSurface;

	private int showCompNameInSurface;

	private int showDependencePerNum;

	private int showInsuranceLevel;

	private int showMnyItemName;

	private int showPerAddInSurface;

	private int showPerNameInSurface;

	private int showRemainAnnualLeave;

	private int showTotalTaxMny;

	private int showZeroInAttend;

	private int showPerTaxCatalog;

	private int showDepartment;

	private int showZeroInMny;

	private int showProductsPayMny;

	private int showAttendItemName;
}
