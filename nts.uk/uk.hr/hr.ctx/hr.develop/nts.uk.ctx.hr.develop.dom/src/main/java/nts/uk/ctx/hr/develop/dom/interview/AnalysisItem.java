package nts.uk.ctx.hr.develop.dom.interview;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

@Getter
@Setter
public class AnalysisItem  extends DomainObject {

	private String analysisItemId;
	
	private String analysisInfoId;
	
	private Optional<String> analysisItemCd;
	
	private Optional<String> analysisName;

	public AnalysisItem(String analysisItemId, String analysisInfoId, Optional<String> analysisItemCd,
			Optional<String> analysisName) {
		super();
		this.analysisItemId = analysisItemId;
		this.analysisInfoId = analysisInfoId;
		this.analysisItemCd = analysisItemCd;
		this.analysisName = analysisName;
	}
	
}
