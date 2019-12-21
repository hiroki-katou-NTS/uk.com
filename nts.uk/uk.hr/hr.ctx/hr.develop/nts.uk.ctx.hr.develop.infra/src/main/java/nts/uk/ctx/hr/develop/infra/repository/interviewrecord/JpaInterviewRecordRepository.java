package nts.uk.ctx.hr.develop.infra.repository.interviewrecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.develop.dom.interview.InterviewRecord;
import nts.uk.ctx.hr.develop.dom.interview.InterviewRecordContent;
import nts.uk.ctx.hr.develop.dom.interview.InterviewRecordRepository;
import nts.uk.ctx.hr.develop.infra.entity.interviewrecord.JflmtInterviewRecord;

@Stateless
public class JpaInterviewRecordRepository extends JpaRepository implements InterviewRecordRepository{
	 	
	private static final String GET_INTERVIEW_CONTENTS = "SELECT a FROM JflmtInterviewRecord a "
			+ "WHERE a.companyID = :companyID "
			+ " AND a.interviewCategory = :interviewCategory"
			+ " AND a.intervieweeSid IN :listEmployeeID";
	
	@Override
	public List<InterviewRecord> getInterviewRecords(String companyID, int interviewCate, List<String> listEmployeeID) {
		
		if(CollectionUtil.isEmpty(listEmployeeID)) {
			return Collections.emptyList();
		}
		
		List<InterviewRecordContent> listInterviewRecordContent = new ArrayList<>();
		
		List<InterviewRecord> result = this.queryProxy().query(GET_INTERVIEW_CONTENTS, JflmtInterviewRecord.class)
				.setParameter("companyID", companyID)
				.setParameter("interviewCategory", interviewCate)
				.setParameter("listEmployeeID", listEmployeeID)
				.getList(x -> x.toDomain(listInterviewRecordContent));
		return result;
	}

}
