package nts.uk.ctx.hr.develop.infra.entity.interviewrecord;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name="JFLMT_INTERVIEW_RECORD")
@AllArgsConstructor
@NoArgsConstructor
public class JflmtInterviewRecord extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JflmtInterviewRecordPK jflmtInterviewRecordPK;
	
	@Column(name = "CID")
	public String companyID;
	
	@Column(name = "INTERVIEWEE_SID")
	public String intervieweeSid;
	
	@Column(name = "INTERVIEWEE_SCD")
	public String intervieweeScd;
	
	@Column(name = "INTERVIEWEE_BNAME")
	public String intervieweeBName;
	
	@Column(name = "INTERVIEW_CATEGORY")
	public int interviewCategory;
	
	@Column(name = "INTERWIEW_DATE")
	public GeneralDate interviewDate;
	
	@Column(name = "MAIN_INTERVIEWER_SID")
	public String mainInterviewerSid;
	
	@Column(name = "MAIN_INTERVIEWER_SCD")
	public String mainInterviewerScd;
	
	@Column(name = "MAIN_INTERVIEWER_BNAME")
	public String mainInterviewerBName;
	
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
	
	@OneToMany(mappedBy = "jflmtInterviewContent", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "JFLMT_INTERVIEW_CONTENT")
	public List<JflmtInterviewContent> jflmtInterviewContent;


	
	@Override
		protected Object getKey() {
			// TODO Auto-generated method stub
			return this.jflmtInterviewRecordPK;
		}
	/*public InterviewRecord toDomain(){
		List<SubInterviewer> listSubInterviewID = new ArrayList<>();
		listSubInterviewID.add(new SubInterviewer(new EmployeeId (sub1InterviewSID)));
		listSubInterviewID.add(new SubInterviewer(new EmployeeId (sub2InterviewSID)));
		listSubInterviewID.add(new SubInterviewer(new EmployeeId (sub3InterviewSID)));
		listSubInterviewID.add(new SubInterviewer(new EmployeeId (sub4InterviewSID)));
		listSubInterviewID.add(new SubInterviewer(new EmployeeId (sub5InterviewSID)));
		
		return new InterviewRecord
				(
						new EmployeeId (mainInterviewerSid),
						new CompanyId(companyID),
						new EmployeeId(intervieweeSid),
						EnumAdaptor.valueOf(interviewCategory , InterviewCategory.class),
						interviewDate,
						this.jflmtInterviewRecordPK.interviewRecordId,
						listInterviewRecordContent,
						listSubInterviewID.stream().filter(c->c != null).collect(Collectors.toList()),
						mainInterviewerScd,
						new MainInterviewerName(mainInterviewerBName),
						intervieweeScd,
						new MainInterviewerName(intervieweeBName));
	}*/

}
