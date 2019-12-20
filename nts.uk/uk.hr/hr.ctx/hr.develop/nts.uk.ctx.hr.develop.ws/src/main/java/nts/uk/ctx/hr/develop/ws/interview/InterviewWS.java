package nts.uk.ctx.hr.develop.ws.interview;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.hr.develop.dom.interview.service.IInterviewRecordSummary;
import nts.uk.ctx.hr.develop.dom.interview.service.InterviewSummary;


@Path("interview")
@Produces(MediaType.APPLICATION_JSON)
public class InterviewWS {
	
	
//	@Inject
//	private InterviewFinder interviewFinder;

//	@Inject
//	private EmployeeInforAdapter employeeInforAdapter;

	@Inject
	private IInterviewRecordSummary iInterviewRecordSummary;

	@POST
	@Path("/testInterviewSummary")
	public InterviewSummary testInterviewSummarry(ParamEmployee param){
		
		InterviewSummary data = iInterviewRecordSummary.getInterviewInfo(
				param.companyID, param.interviewCate, 
				param.listEmployeeID, param.getSubInterview, 
				param.getDepartment, param.getPosition, param.getEmployment);
		
		return data;
	}
	
//	@POST
//	@Path("/interviewSummary")
//	public InterviewSummary getInterviewSummarry(String companyID, int interviewCate, List<String> listEmployeeID,
//			boolean getSubInterview, boolean getDepartment, boolean getPosition, boolean getEmployment){
//		InterviewSummary data = interviewFinder.getInterviewSummarry(companyID, interviewCate, listEmployeeID, getSubInterview, getDepartment, getPosition, getEmployment);
//		return data;
//	}
//	@POST
//	@Path("/interviewContent")
//	public List<InterviewRecord> getInterviewContents( String companyID , int interviewCate ,List<String> listEmployeeID ){
//		return interviewFinder.getInterviewContents(companyID, interviewCate, listEmployeeID);
//	}
	
//	@POST
//	@Path("/testDepartment")
//	public List<EmployeeInformationImport> testQueryDepartment (ParamEmployee data){
//		EmployeeInfoQueryImport paramInterview = EmployeeInfoQueryImport.builder()
//				.employeeIds(null)
//				.referenceDate(data.getDate()) // Xem lai date
//				.toGetWorkplace(false)
//				.toGetDepartment(true)
//				.toGetPosition(false)
//				.toGetEmployment(false)
//				.toGetClassification(false)
//				.toGetEmploymentCls(false).build();
//				List<EmployeeInformationImport> listInterviewEmployeeImport = employeeInforAdapter.find(paramInterview);
//		return listInterviewEmployeeImport;
//	}
	
}
