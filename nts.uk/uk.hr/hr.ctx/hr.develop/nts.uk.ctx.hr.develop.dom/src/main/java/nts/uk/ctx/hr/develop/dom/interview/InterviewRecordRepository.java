package nts.uk.ctx.hr.develop.dom.interview;

import java.util.List;

public interface InterviewRecordRepository {
	
	//面談記録内容の取得
	List<InterviewRecord> getInterviewContents( String companyID , int interviewCate ,List<String> listEmployeeID );
	
}
