package nts.uk.ctx.hr.develop.infra.entity.interviewrecord;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.interview.InterviewRecordContent;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "JFLMT_INTERVIEW_CONTENT")
@NoArgsConstructor
public class JflmtInterviewContent extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JflmtInterviewContentPK pk;

	@Column(name = "CID")
	public String cid;

	/** 面談記録ID --- interviewRecordId **/
	@Column(name = "INTERVIEW_REC_ID")
	public String interviewRecID;

	/** 表示順 -- displayNum **/
	@Column(name = "DISPLAY_NUM")
	public int displayNum;
	/** ヒアリング項目ID --- interviewItemId **/
	@Column(name = "INTERVIEW_CONTENT_ID")
	public long interviewItemId;

	/** ヒアリング項目CD --- interviewItemCD **/
	@Column(name = "INTERVIEW_CONTENT_CD")
	public String interviewItemCD;

	/** ヒアリング項目名 -- interviewItemName **/
	@Column(name = "INTERVIEW_CONTENT_NAME")
	public String interviewItemName;

	/** 確認結果 --- recordContent **/
	@Column(name = "RECORD_CONTENT")
	public String recordContent;

	/** 分析区分カテゴリID --- analysisCategoryId **/
	@Column(name = "ANALYSIS_CATEGORY_ID")
	public long analysisCategoryId;

	/** 分析区分カテゴリCD --- analysisCategoryCD **/
	@Column(name = "ANALYSIS_CATEGORY_CD")
	public String analysisCategoryCD;

	/** 分析区分カテゴリName --- analysisCategoryName **/
	@Column(name = "ANALYSIS_CATEGORY_NAME")
	public String analysisCategoryName;

	public List<JflmtInterviewAnalysis> jflmtInterviewAnalysis;

	public JflmtInterviewContent(JflmtInterviewContentPK pk, String cid, String interviewRecID, int displayNum,
			long interviewItemId, String interviewItemCD, String interviewItemName, String recordContent,
			long analysisCategoryId, String analysisCategoryCD, String analysisCategoryName,
			List<JflmtInterviewAnalysis> jflmtInterviewAnalysis) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.interviewRecID = interviewRecID;
		this.displayNum = displayNum;
		this.interviewItemId = interviewItemId;
		this.interviewItemCD = interviewItemCD;
		this.interviewItemName = interviewItemName;
		this.recordContent = recordContent;
		this.analysisCategoryId = analysisCategoryId;
		this.analysisCategoryCD = analysisCategoryCD;
		this.analysisCategoryName = analysisCategoryName;
		this.jflmtInterviewAnalysis = jflmtInterviewAnalysis;
	}

	public InterviewRecordContent toDomain() {
		return new InterviewRecordContent(
				interviewItemId,
				displayNum,
				interviewItemCD,
				interviewItemName,
				analysisCategoryCD,
				analysisCategoryId,
				analysisCategoryName,
				jflmtInterviewAnalysis.stream().map(c -> c.toDomain()).collect(Collectors.toList()),
				recordContent);
	}

	public static JflmtInterviewContent toEntity(InterviewRecordContent domain , String companyID , String interviewRecID){
		return new JflmtInterviewContent(
				new JflmtInterviewContentPK(domain.getInterviewItemId()),
				companyID,
				interviewRecID,
				domain.getDisplayNumber(),
				//Đéo hiểu kiểu gì
				domain.getInterviewItemId(),
				domain.getInterviewItemCd().get(),
				domain.getInterviewItemName().get(),
				domain.getRecordContent().get(),
				domain.getAnalysisCategoryId().get(),
				domain.getAnalysisCategoryCd().get(),
				domain.getAnalysisCategoryName().get(),
				domain.getListAnalysisItemId().stream().map(c ->JflmtInterviewAnalysis.toEntity(c,domain.getInterviewItemId(), companyID)).collect(Collectors.toList()));
	}

	@Override
	protected Object getKey() {

		return this.pk;
	}

}
