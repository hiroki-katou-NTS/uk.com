package nts.uk.ctx.hr.develop.dom.interview;

import java.util.List;

public interface InterviewRecordRepository {
	
	//面談記録内容の取得
	List<String> getInterviewContents( String companyID , int interviewCate ,List<String> listEmployeeID, String subInterviewer   );
	
	List<InterviewRecord> getByCategoryCIDAndListEmpID (String companyID , int interviewCategory , List<String> listEmployeeID);

}
