package nts.uk.ctx.pr.report.app.payment.refundsetting.refundlayout.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout.RefundLayout;

@Getter
@AllArgsConstructor
public class RefundLayoutDto {

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

	public static RefundLayoutDto fromDomain(RefundLayout domain) {
		return new RefundLayoutDto(domain.getPrintType().value,
				domain.getUsingZeroSettingCtg().value, domain.getPrintYearMonth().value,
				domain.getPaymentCellNameCtg().value, domain.getIsShaded().value, domain.getBordWidth().value,
				domain.getShowRefundLayout().getShowCompName().value,
				domain.getShowRefundLayout().getShowCompAddInSurface().value,
				domain.getShowRefundLayout().getShowCompNameInSurface().value,
				domain.getShowRefundLayout().getShowDependencePerNum().value,
				domain.getShowRefundLayout().getShowInsuranceLevel().value,
				domain.getShowRefundLayout().getShowMnyItemName().value,
				domain.getShowRefundLayout().getShowPerAddInSurface().value,
				domain.getShowRefundLayout().getShowPerNameInSurface().value,
				domain.getShowRefundLayout().getShowRemainAnnualLeave().value,
				domain.getShowRefundLayout().getShowTotalTaxMny().value,
				domain.getShowRefundLayout().getShowZeroInAttend().value,
				domain.getShowRefundLayout().getShowPerTaxCatalog().value,
				domain.getShowRefundLayout().getShowDepartment().value,
				domain.getShowRefundLayout().getShowZeroInMny().value,
				domain.getShowRefundLayout().getShowProductsPayMny().value,
				domain.getShowRefundLayout().getShowAttendItemName().value);
	}
}
