package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

public interface RefundLayoutRepository {
	
	RefundLayout getRefundLayout(String companyCode, String printType);
	
	void insertRefundLayout(RefundLayout refundLayout);
	void updateRefundLayout(RefundLayout refundLayout);	
}
