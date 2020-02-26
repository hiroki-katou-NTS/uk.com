package nts.uk.ctx.hr.develop.dom.interview;

import java.util.List;

public interface InterviewRecordRepository {

	// ドメインモデル [面談記録] を取得する
	List<InterviewRecord> getInterviewRecords(String companyID, int interviewCate, List<String> listEmployeeID);

}
