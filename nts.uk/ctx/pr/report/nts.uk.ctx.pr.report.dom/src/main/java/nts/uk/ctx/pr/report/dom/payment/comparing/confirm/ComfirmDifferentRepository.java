package nts.uk.ctx.pr.report.dom.payment.comparing.confirm;

import java.util.List;

public interface ComfirmDifferentRepository {

	List<DetailDifferential> getDetailDifferentialWithEarlyYM(String companyCode, int processingYMEarlier, List<String> personIDs);

	List<DetailDifferential> getDetailDifferentialWithLaterYM(String companyCode, int processingYMLater, List<String> personIDs);

	List<PaycompConfirm> getPayCompComfirm(String companyCode, List<String> personIDs, int processYMEarly, int processYMLater);

	void insertComparingPrintSet(List<PaycompConfirm> paycompConfirmList);

	void updateComparingPrintSet(List<PaycompConfirm> paycompConfirmList);
}
