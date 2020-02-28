package nts.uk.ctx.hr.develop.infra.entity.interviewrecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.hr.develop.dom.interview.CompanyId;
import nts.uk.ctx.hr.develop.dom.interview.EmployeeId;
import nts.uk.ctx.hr.develop.dom.interview.InterviewCategory;
import nts.uk.ctx.hr.develop.dom.interview.InterviewRecord;
import nts.uk.ctx.hr.develop.dom.interview.InterviewRecordContent;
import nts.uk.ctx.hr.develop.dom.interview.MainInterviewerName;
import nts.uk.ctx.hr.develop.dom.interview.SubInterviewer;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name="JFLMT_INTERVIEW_RECORD")
@NoArgsConstructor
public class JflmtInterviewRecord extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JflmtInterviewRecordPK jflmtInterviewRecordPK;
	/** **/
	@Column(name = "CID")
	public String companyID;
	
	/** 被面談者社員ID --- intervieweeSid**/
	@Column(name = "INTERVIEWEE_SID")
	public String intervieweeSid;
	
	/**被面談者社員SCD --- intervieweeScd **/
	@Column(name = "INTERVIEWEE_SCD")
	public String intervieweeScd;
	
	/**被面談者社員名 --- intervieweeName  **/
	@Column(name = "INTERVIEWEE_BNAME")
	public String intervieweeBName;
	
	/** 面談区分 --- interviewCategory **/
	@Column(name = "INTERVIEW_CATEGORY")
	public int interviewCategory;
	
	/** 面談日 --- interviewDate**/
	@Column(name = "INTERWIEW_DATE")
	public GeneralDate interviewDate;
	
	/** メイン面談者社員ID --- mainInterviewerSid**/
	@Column(name = "MAIN_INTERVIEWER_SID")
	public String mainInterviewerSid;
	
	/** メイン面談者社員SCD --- mainInterviewerScd **/
	@Column(name = "MAIN_INTERVIEWER_SCD")
	public String mainInterviewerScd;
	
	/** メイン面談者社員名 -- mainInterviewerName **/
	@Column(name = "MAIN_INTERVIEWER_BNAME")
	public String mainInterviewerBName;
	
	/** **/
	@Column(name = "SUB1_INTERVIEWER_SID")
	public String sub1InterviewSID;
	
	@Column(name = "SUB2_INTERVIEWER_SID")
	public String sub2InterviewSID;
	
	@Column(name = "SUB3_INTERVIEWER_SID")
	public String sub3InterviewSID;
	
	@Column(name = "SUB4_INTERVIEWER_SID")
	public String sub4InterviewSID;
	
	@Column(name = "SUB5_INTERVIEWER_SID")
	public String sub5InterviewSID;
	
	@Override
	protected Object getKey() {
		return this.jflmtInterviewRecordPK;
	}
	
	private void check_addSubInterview(String subInterviewSID, List<SubInterviewer> listSubInterviewID) {
		if (!StringUtil.isNullOrEmpty(subInterviewSID, false)) {
			listSubInterviewID.add(new SubInterviewer(new EmployeeId (subInterviewSID)));
		}
	}
	
	public InterviewRecord toDomain(List<InterviewRecordContent> listInterviewRecordContent){
		List<SubInterviewer> listSubInterviewID = new ArrayList<>();
		check_addSubInterview(sub1InterviewSID, listSubInterviewID);
		check_addSubInterview(sub2InterviewSID, listSubInterviewID);
		check_addSubInterview(sub3InterviewSID, listSubInterviewID);
		check_addSubInterview(sub4InterviewSID, listSubInterviewID);
		check_addSubInterview(sub5InterviewSID, listSubInterviewID);
		
		return new InterviewRecord (
				new EmployeeId (mainInterviewerSid),
				new CompanyId(companyID),
				new EmployeeId(intervieweeSid),
				EnumAdaptor.valueOf(interviewCategory , InterviewCategory.class),
				interviewDate,
				this.jflmtInterviewRecordPK.interviewRecordId,
				listInterviewRecordContent,
				listSubInterviewID,
				mainInterviewerScd,
				new MainInterviewerName(mainInterviewerBName),
				intervieweeScd,
				new MainInterviewerName(intervieweeBName));
	}
	
	public static JflmtInterviewRecord toEntity(InterviewRecord domain , String companyID , String interviewRecID){
		return new JflmtInterviewRecord(
				new JflmtInterviewRecordPK(interviewRecID) ,
				companyID,
				domain.getIntervieweeSid().v(),
				domain.getIntervieweeScd().get(),
				domain.getIntervieweeName().get().v(), 
				domain.getInterviewCategory().value,
				domain.getInterviewDate(), 
				domain.getMainInterviewerSid().v(), 
				domain.getMainInterviewerScd().get(),
				domain.getMainInterviewerName().get().v(),
				domain.getListSubInterviewer().get(0).getSubInterviewerSid().v(),
				domain.getListSubInterviewer().get(1).getSubInterviewerSid().v(),
				domain.getListSubInterviewer().get(2).getSubInterviewerSid().v(), 
				domain.getListSubInterviewer().get(3).getSubInterviewerSid().v(),
				domain.getListSubInterviewer().get(4).getSubInterviewerSid().v(), 
				domain.getListInterviewRecordContent()
						.stream().map(c -> JflmtInterviewContent.toEntity(c, interviewRecID, companyID)).collect(Collectors.toList())
				);
		//return null;
	}
	
	public JflmtInterviewRecord(JflmtInterviewRecordPK jflmtInterviewRecordPK, String companyID, String intervieweeSid,
			String intervieweeScd, String intervieweeBName, int interviewCategory, GeneralDate interviewDate,
			String mainInterviewerSid, String mainInterviewerScd, String mainInterviewerBName, String sub1InterviewSID,
			String sub2InterviewSID, String sub3InterviewSID, String sub4InterviewSID, String sub5InterviewSID,
			List<JflmtInterviewContent> jflmtInterviewContent) {
		super();
		this.jflmtInterviewRecordPK = jflmtInterviewRecordPK;
		this.companyID = companyID;
		this.intervieweeSid = intervieweeSid;
		this.intervieweeScd = intervieweeScd;
		this.intervieweeBName = intervieweeBName;
		this.interviewCategory = interviewCategory;
		this.interviewDate = interviewDate;
		this.mainInterviewerSid = mainInterviewerSid;
		this.mainInterviewerScd = mainInterviewerScd;
		this.mainInterviewerBName = mainInterviewerBName;
		this.sub1InterviewSID = sub1InterviewSID;
		this.sub2InterviewSID = sub2InterviewSID;
		this.sub3InterviewSID = sub3InterviewSID;
		this.sub4InterviewSID = sub4InterviewSID;
		this.sub5InterviewSID = sub5InterviewSID;
	}

}
