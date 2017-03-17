package nts.uk.ctx.pr.report.dom.payment.comparing;

import java.util.List;

public interface ComparingFormRepository {
	
	List<ComparingFormHeader> getListComparingFormHeader(String companyCode);
	
	void InsertComparingFormHeader(ComparingFormHeader comparingFormHeader);
	
	void UpdateComparingFormHeader(ComparingFormHeader comparingFormHeader);
	
	void DeleteComparingFormHeader(String companyCode, String formCode);
		
}
