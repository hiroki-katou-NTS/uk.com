package nts.uk.ctx.hr.develop.ws.interview;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.app.interview.find.InterviewFinder;
import nts.uk.ctx.hr.develop.dom.interview.InterviewRecord;
import nts.uk.ctx.hr.develop.dom.interview.dto.InterviewSummary;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInfoQueryImport;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInforAdapter;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;


@Path("interview")
@Produces(MediaType.APPLICATION_JSON)
public class InterviewWS {
	
	
	@Inject
	private InterviewFinder interviewFinder;
	

	@Inject
	private EmployeeInforAdapter employeeInforAdapter;
	
	@POST
	@Path("/interviewSummary")
	public InterviewSummary getInterviewSummarry(String companyID, int interviewCate, List<String> listEmployeeID,
			boolean getSubInterview, boolean getDepartment, boolean getPosition, boolean getEmployment){
		InterviewSummary data = interviewFinder.getInterviewSummarry(companyID, interviewCate, listEmployeeID, getSubInterview, getDepartment, getPosition, getEmployment);
		return data;
	}
	@POST
	@Path("/interviewContent")
	public List<InterviewRecord> getInterviewContents( String companyID , int interviewCate ,List<String> listEmployeeID ){
		return interviewFinder.getInterviewContents(companyID, interviewCate, listEmployeeID);
	}
	@POST
	@Path("/testDepartment")
	public List<EmployeeInformationImport> testQueryDepartment (ParamEmployee data){
		EmployeeInfoQueryImport paramInterview = EmployeeInfoQueryImport.builder()
				.employeeIds(null)
				.referenceDate(data.getDate()) // Xem lai date
				.toGetWorkplace(false)
				.toGetDepartment(true)
				.toGetPosition(false)
				.toGetEmployment(false)
				.toGetClassification(false)
				.toGetEmploymentCls(false).build();
				List<EmployeeInformationImport> listInterviewEmployeeImport = employeeInforAdapter.find(paramInterview);
		return listInterviewEmployeeImport;
	}
	
}
