package nts.uk.ctx.hr.develop.infra.repository.interviewrecord;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.hr.develop.dom.interview.InterviewRecord;
import nts.uk.ctx.hr.develop.dom.interview.InterviewRecordRepository;
@Stateless
public class JpaInterviewRecordRepository extends JpaRepository implements InterviewRecordRepository{

	@Override
	public List<String> getInterviewContents(String companyID, int interviewCate, List<String> listEmployeeID,
			String subInterviewer) {
		
			
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InterviewRecord> getByCategoryCIDAndListEmpID(String companyID, int interviewCategory,
			List<String> listEmployeeID) {
		// TODO Auto-generated method stub
		
		
		
		return null;
	}

	

}
