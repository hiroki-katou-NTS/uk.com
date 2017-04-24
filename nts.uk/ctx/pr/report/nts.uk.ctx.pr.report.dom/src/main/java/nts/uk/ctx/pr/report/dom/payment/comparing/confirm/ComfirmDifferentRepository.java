package nts.uk.ctx.pr.report.dom.payment.comparing.confirm;

import java.util.List;

public interface ComfirmDifferentRepository {

	List<DetailDifferential> getDetailDifferentialWithEarlyYM(String companyCode, int processingYMEarlier);

	List<DetailDifferential> getDetailDifferentialWithLaterYM(String companyCode, int processingYMLater);

	PaycompConfirm getPayCompComfirm(String companyCode, String personId, int processYMEarly, int processYMLater,
			int categoryAtr, String itemCD);

	void insertComparingPrintSet(PaycompConfirm paycompConfirm);

	void updateComparingPrintSet(PaycompConfirm paycompConfirm);
}
