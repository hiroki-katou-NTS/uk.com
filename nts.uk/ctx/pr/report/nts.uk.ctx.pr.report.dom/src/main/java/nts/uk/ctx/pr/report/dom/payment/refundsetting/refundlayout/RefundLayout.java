package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@Getter
public class RefundLayout extends AggregateRoot {

	private CompanyCode companyCode;

	private PrintType printType;

	private UsingZeroSettingCatalog usingZeroSettingCtg;

	private PrintYearMonth printYearMonth;

	private ShowRefundLayout showRefundLayout;

	private PaymentCellNameCatalog paymentCellNameCtg;

	private Shaded isShaded;

	private BordWidth bordWidth;

	public static RefundLayout createFromJavaType(String companyCode, int printType, int usingZeroSettingCtg,
			int printYearMonth, ShowRefundLayout showRefundLayout, int paymentCellNameCtg, int isShaded,
			int bordWidth) {
		return new RefundLayout(new CompanyCode(companyCode), EnumAdaptor.valueOf(printType, PrintType.class),
				EnumAdaptor.valueOf(usingZeroSettingCtg, UsingZeroSettingCatalog.class),
				EnumAdaptor.valueOf(printYearMonth, PrintYearMonth.class), showRefundLayout,
				EnumAdaptor.valueOf(paymentCellNameCtg, PaymentCellNameCatalog.class),
				EnumAdaptor.valueOf(isShaded, Shaded.class), EnumAdaptor.valueOf(bordWidth, BordWidth.class));
	}

}
