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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name="JFLMT_INTERVIEW_CONTENT")
@AllArgsConstructor
@NoArgsConstructor
public class JflmtInterviewContent extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	public JflmtInterviewContentPK pk;
	
	@Column (name = "CID")
	public String cid;
	
	@Column (name = "INTERVIEW_REC_ID")
	public String interviewRecID;
	
	@Column (name = "DISPLAY_NUM")
	public int displayNum;
	//QA ch
	@Column (name = "INTERVIEW_CONTENT_ID")
	public String interviewContentID;
	
	@Column (name = "INTERVIEW_CONTENT_CD")
	public String interviewContentCD;
	
	@Column (name = "INTERVIEW_CONTENT_NAME")
	public String interviewContentName;
	
	@Column (name = "RECORD_CONTENT")
	public String recordContent;
	
	@Column (name = "ANALYSIS_CATEGORY_ID")
	public int analysisCategoryID;
	
	@Column (name = "ANALYSIS_CATEGORY_CD")
	public String analysisCategoryCD;
	
	@Column (name = "ANALYSIS_CATEGORY_NAME")
	public String analysisCategoryNAME;
	
	@OneToMany(mappedBy = "jflmtInterviewAnalysis", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "JFLMT_INTERVIEW_ANALYSIS")
	public List<JflmtInterviewAnalysis> jflmtInterviewAnalysis;

	
	/*public InterviewRecordContent toDomain() {
		return new InterviewRecordContent(interviewContentCD, displayNum, analysisCategoryID, 
				jflmtInterviewAnalysis.stream()
										.map(c -> c.toDomain())
										.collect(Collectors.toList()), 
				recordContent);
	}*/
	

@Override
	protected Object getKey() {
		
		return this.pk;
	}
	

}
