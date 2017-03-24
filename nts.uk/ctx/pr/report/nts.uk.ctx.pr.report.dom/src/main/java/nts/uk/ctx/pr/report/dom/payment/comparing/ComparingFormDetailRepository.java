package nts.uk.ctx.pr.report.dom.payment.comparing;

import java.util.List;
import java.util.Optional;

public interface ComparingFormDetailRepository {
	
	List<ComparingFormDetail> getComparingFormDetailByCategory_Atr(String companyCode, String formCode,
			int categoryAtr);

	Optional<ComparingFormDetail> getComparingFormDetail(String companyCode, String formCode, String itemCode,
			int categoryAtr);

	void insertComparingFormDetail(List<ComparingFormDetail> comparingFormDetailList);

	//void updateComparingFormDetail(List<ComparingFormDetail> comparingFormDetailList);

	void deleteComparingFormDetail(String companyCode, String formCode);
}
