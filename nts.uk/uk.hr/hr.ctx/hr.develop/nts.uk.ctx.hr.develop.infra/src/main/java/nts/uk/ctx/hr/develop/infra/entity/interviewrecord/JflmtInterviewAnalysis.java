package nts.uk.ctx.hr.develop.infra.entity.interviewrecord;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.interview.AnalysisItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "JFLMT_INTERVIEW_ANALYSIS")
@NoArgsConstructor
public class JflmtInterviewAnalysis extends UkJpaEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JflmtInterviewAnalysisPK pk;

	@Column(name = "CID")
	public String companyId;

	@Column(name = "INTERVIEW_CONTENT_ID")
	public long interviewContentId;

	/** 分析区分項目ID --- analysisItemID **/
	@Column(name = "ANALYSIS_ITEM_ID")
	public long analysisItemId;

	/** 分析区分項目CD --- analysisItemCd **/
	@Column(name = "ANALYSIS_ITEM_CD")
	public String analysisItemCd;

	/** 分析区分項目名 --- analysisItemName **/
	@Column(name = "ANALYSIS_ITEM_NAME")
	public String analysisItemName;

	public JflmtInterviewAnalysis(JflmtInterviewAnalysisPK pk, String companyId, long interviewContentId,
			long analysisItemId, String analysisItemCd, String analysisItemName) {
		super();
		this.pk = pk;
		this.companyId = companyId;
		this.interviewContentId = interviewContentId;
		this.analysisItemId = analysisItemId;
		this.analysisItemCd = analysisItemCd;
		this.analysisItemName = analysisItemName;
	}

	public AnalysisItem toDomain() {
		return new AnalysisItem(analysisItemId,
								pk.analysisInfoId,
								Optional.of(analysisItemCd),
								Optional.of(analysisItemName));
	}

	public static JflmtInterviewAnalysis toEntity(AnalysisItem domain, long interviewContentId, String companyID) {
		return new JflmtInterviewAnalysis(new JflmtInterviewAnalysisPK(domain.getAnalysisInfoId()), companyID,
				interviewContentId, domain.getAnalysisItemId(),
				domain.getAnalysisItemCd().isPresent() ? domain.getAnalysisItemCd().get() : null,
				domain.getAnalysisItemName().isPresent() ? domain.getAnalysisItemName().get() : null);
		
	}

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
