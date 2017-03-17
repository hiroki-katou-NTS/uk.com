package nts.uk.ctx.pr.report.dom.payment.comparing;

import java.util.List;
import java.util.Optional;

public interface ComparingFormHeaderRepository {
	
	List<ComparingFormHeader> getListComparingFormHeader(String companyCode);
	
	Optional<ComparingFormHeader> getComparingFormHeader(String companyCode, String formCode);
	
	void InsertComparingFormHeader(ComparingFormHeader comparingFormHeader);
		
	void UpdateComparingFormHeader(ComparingFormHeader comparingFormHeader);
	
	void DeleteComparingFormHeader(String companyCode, String formCode);
		
}
