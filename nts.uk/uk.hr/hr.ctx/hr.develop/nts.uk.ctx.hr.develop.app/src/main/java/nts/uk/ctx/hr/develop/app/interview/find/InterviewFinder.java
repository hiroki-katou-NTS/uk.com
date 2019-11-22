package nts.uk.ctx.hr.develop.app.interview.find;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.interview.InterviewRecord;
import nts.uk.ctx.hr.develop.dom.interview.InterviewRecordRepository;
import nts.uk.ctx.hr.develop.dom.interview.InterviewService;
import nts.uk.ctx.hr.develop.dom.interview.dto.InterviewSummary;

@Stateless
public class InterviewFinder {

	@Inject
	private InterviewService interviewService;
	@Inject
	private InterviewRecordRepository interviewRecordRepository ;
	
	public InterviewSummary getInterviewSummarry(String companyID, int interviewCate, List<String> listEmployeeID,
			boolean getSubInterview, boolean getDepartment, boolean getPosition, boolean getEmployment)
	{
		return interviewService.getInterviewContents(companyID, interviewCate, listEmployeeID, getSubInterview, getDepartment, getPosition, getEmployment);
	}
	
	public List<InterviewRecord> getInterviewContents( String companyID , int interviewCate ,List<String> listEmployeeID ){
		return interviewRecordRepository.getInterviewContents(companyID, interviewCate, listEmployeeID);
	}
}
