package nts.uk.ctx.hr.develop.infra.entity.interviewrecord;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.hr.develop.dom.interview.AnalysisItem;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careertype.JhcmtCareerType;
import nts.uk.ctx.hr.develop.infra.entity.careermgmt.careertype.JhcmtCareerTypeMp;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name="JFLMT_INTERVIEW_ANALYSIS")
@AllArgsConstructor
@NoArgsConstructor
public class JflmtInterviewAnalysis extends UkJpaEntity implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public JflmtInterviewAnalysisPK pk;
	
	
	@Column (name = "ANALYSIS_INFO_ID")
	public String analysisInfoID;
	
	@Column (name = "ANALYSIS_ITEM_ID")
	public String analysisItemID;
		
	@Column (name = "ANALYSIS_ITEM_CD")
	public String analysisItemCd;
	
	@Column (name = "ANALYSIS_ITEM_NAME")
	public String analysisItemName;
	
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "ANALYSIS_INFO_ID", referencedColumnName = "ANALYSIS_INFO_ID", insertable = false, updatable = false)
	})
	public JflmtInterviewContent jflmtInterviewContent;
	
	public AnalysisItem toDomain() {
		return new AnalysisItem(analysisItemID, analysisInfoID, Optional.of(analysisItemCd), Optional.of(analysisItemName));
	}
	
@Override
	protected Object getKey() {
		return this.pk;
	}
	
}
