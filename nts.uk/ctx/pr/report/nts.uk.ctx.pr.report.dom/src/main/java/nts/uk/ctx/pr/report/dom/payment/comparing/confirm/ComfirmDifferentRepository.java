package nts.uk.ctx.pr.report.dom.payment.comparing.confirm;

import java.util.List;

public interface ComfirmDifferentRepository {
	List<DetailDifferential> getDetailDifferential(String companyCode, int processingYMEarlier, int processingYMLater);

	void insertComparingPrintSet(PaycompConfirm paycompConfirm);

	void updateComparingPrintSet(PaycompConfirm paycompConfirm);
}
