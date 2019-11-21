package nts.uk.ctx.hr.develop.app.interview.find;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.dom.interview.InterviewService;
import nts.uk.ctx.hr.develop.dom.interview.dto.InterviewSummary;

@Stateless
public class InterviewFinder {

	@Inject
	private InterviewService interviewService;
	
	public InterviewSummary getInterviewContents(String companyID, int interviewCate, List<String> listEmployeeID,
			boolean getSubInterview, boolean getDepartment, boolean getPosition, boolean getEmployment)
	{
		return interviewService.getInterviewContents(companyID, interviewCate, listEmployeeID, getSubInterview, getDepartment, getPosition, getEmployment);
	}
}
