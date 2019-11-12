package nts.uk.ctx.hr.develop.dom.interview;

import java.util.List;

public interface InterviewRecordRepository {
	
	//面談記録内容の取得
	List<InterviewRecord> getInterviewContents( String companyID , int interviewCate ,List<String> listEmployeeID );
	
	List<InterviewRecord> getByCategoryCIDAndListEmpID (String companyID , int interviewCategory , List<String> listEmployeeID , boolean department, boolean jobtitle , boolean employment);

}
