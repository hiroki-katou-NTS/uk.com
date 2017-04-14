package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout;

import java.util.Optional;

public interface RefundLayoutRepository {

	Optional<RefundLayout> getRefundLayout(String companyCode, int printType);

	void insertRefundLayout(RefundLayout refundLayout);

	void updateRefundLayout(RefundLayout refundLayout);
}
