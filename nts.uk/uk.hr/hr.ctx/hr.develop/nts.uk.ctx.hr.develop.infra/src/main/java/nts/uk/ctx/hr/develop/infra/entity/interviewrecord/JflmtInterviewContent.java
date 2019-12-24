package nts.uk.ctx.hr.develop.infra.entity.interviewrecord;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.interview.AnalysisItem;
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

	/**--- interviewContentId **/
	@Column(name = "INTERVIEW_CONTENT_ID")
	public long interviewContentID;

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

	public JflmtInterviewContent(JflmtInterviewContentPK pk, String cid, long interviewContentID, String interviewItemCD,
			String interviewItemName, String recordContent, long analysisCategoryId, String analysisCategoryCD,
			String analysisCategoryName, List<JflmtInterviewAnalysis> jflmtInterviewAnalysis) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.interviewContentID = interviewContentID;
		this.interviewItemCD = interviewItemCD;
		this.interviewItemName = interviewItemName;
		this.recordContent = recordContent;
		this.analysisCategoryId = analysisCategoryId;
		this.analysisCategoryCD = analysisCategoryCD;
		this.analysisCategoryName = analysisCategoryName;
	}

	public InterviewRecordContent toDomain(List<AnalysisItem> listAnalysisItemId) {
		return new InterviewRecordContent(interviewContentID,
				pk.displayNum,
				interviewItemCD,
				interviewItemName,
				analysisCategoryCD,
				analysisCategoryId,
				analysisCategoryName,
				listAnalysisItemId,
				recordContent);
	}
	
	public static JflmtInterviewContent toEntity(InterviewRecordContent domain , String companyID , String interviewRecID){
		return new JflmtInterviewContent(
				new JflmtInterviewContentPK(interviewRecID, domain.getDisplayNumber()) ,
				companyID,
				domain.getInterviewItemId(),
				domain.getInterviewItemCd().isPresent() ? domain.getInterviewItemCd().get() : null,
				domain.getInterviewItemName().isPresent() ? domain.getInterviewItemName().get() : null,
				domain.getRecordContent().isPresent() ? domain.getRecordContent().get() : null,
				domain.getAnalysisCategoryId(),
				domain.getAnalysisCategoryCd().isPresent() ? domain.getAnalysisCategoryCd().get() : null,
				domain.getAnalysisCategoryName().isPresent() ? domain.getAnalysisCategoryName().get() :null,
			    domain.getListAnalysisItemId().stream().map(c ->JflmtInterviewAnalysis.toEntity(c,domain.getInterviewItemId(), companyID)).collect(Collectors.toList()));
		
	}

	@Override
	protected Object getKey() {

		return this.pk;
	}
	
}
