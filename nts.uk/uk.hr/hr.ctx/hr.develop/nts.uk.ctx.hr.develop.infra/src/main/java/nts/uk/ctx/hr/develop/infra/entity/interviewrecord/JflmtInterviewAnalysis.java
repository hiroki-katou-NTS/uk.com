package nts.uk.ctx.hr.develop.infra.entity.interviewrecord;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
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
	
	/** 分析情報ID --- analysisInfoId **/
	@Column(name = "ANALYSIS_INFO_ID")
	public String analysisInfoId;

	/** 分析区分項目ID --- analysisItemID **/
	@Column(name = "ANALYSIS_ITEM_ID")
	public long analysisItemId;

	/** 分析区分項目CD --- analysisItemCd **/
	@Column(name = "ANALYSIS_ITEM_CD")
	public String analysisItemCd;

	/** 分析区分項目名 --- analysisItemName **/
	@Column(name = "ANALYSIS_ITEM_NAME")
	public String analysisItemName;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumns({
	 * 
	 * @JoinColumn(name = "ANALYSIS_INFO_ID", referencedColumnName =
	 * "ANALYSIS_INFO_ID", insertable = false, updatable = false) }) public
	 * JflmtInterviewContent jflmtInterviewContent;
	 */
	public JflmtInterviewAnalysis(JflmtInterviewAnalysisPK pk, String companyId, String analysisInfoId,
			long analysisItemId, String analysisItemCd, String analysisItemName) {
		super();
		this.pk = pk;
		this.companyId = companyId;
		this.analysisInfoId = analysisInfoId;
		this.analysisItemId = analysisItemId;
		this.analysisItemCd = analysisItemCd;
		this.analysisItemName = analysisItemName;
	}
	
	public AnalysisItem toDomain() {
		return new AnalysisItem(analysisItemId, analysisInfoId, Optional.of(analysisItemCd),
				Optional.of(analysisItemName));
	}
	
	public static JflmtInterviewAnalysis toEntity(AnalysisItem domain, long interviewContentId , String companyID){
		return new JflmtInterviewAnalysis(
				new JflmtInterviewAnalysisPK(interviewContentId),
				companyID,
				domain.getAnalysisInfoId(),
				domain.getAnalysisItemId(),
				domain.getAnalysisItemCd().isPresent() ? domain.getAnalysisItemCd().get() : null,
				domain.getAnalysisName().isPresent() ? domain.getAnalysisName().get() : null);
	}
	@Override
	protected Object getKey() {
		return this.pk;
	}

	

}
