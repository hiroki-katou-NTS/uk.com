
package nts.uk.ctx.hr.develop.dom.interview;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author hieult
 *
 */
/** 面談記録  **/

@Getter

public class InterviewRecord extends AggregateRoot {
	
	/** メイン面談者社員ID **/
	private EmployeeId mainInterviewerSid;
	/** 会社ID **/
	private CompanyId  cid;
	/** 被面談者社員ID **/
	private EmployeeId intervieweeSid;
	/** 面談区分 **/
	private InterviewCategory interviewCategory;
	/** 面談日 **/
	private GeneralDate interviewDate;
	/** 面談記録ID **/
	private String interviewRecordId;
	/** 面談記録内容 **/
	private List<InterviewRecordContent> listInterviewRecordContent;
	/** Optional サブ面談者 **/
	private List<SubInterviewer> listSubInterviewer;

	public InterviewRecord(EmployeeId mainInterviewerSid, CompanyId cid, EmployeeId intervieweeSid,
			InterviewCategory interviewCategory, GeneralDate interviewDate, String interviewRecordId,
			List<InterviewRecordContent> listInterviewRecordContent,
			List<SubInterviewer> listSubInterviewer) {
		super();
		this.mainInterviewerSid = mainInterviewerSid;
		this.cid = cid;
		this.intervieweeSid = intervieweeSid;
		this.interviewCategory = interviewCategory;
		this.interviewDate = interviewDate;
		this.interviewRecordId = interviewRecordId;
		this.listInterviewRecordContent = listInterviewRecordContent;
		this.listSubInterviewer = listSubInterviewer;
	}

	
	
	
	

}
