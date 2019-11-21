package nts.uk.ctx.hr.develop.dom.interview;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

@Getter
@Setter
public class AnalysisItem  extends DomainObject {
	
	/** 分析情報ID **/
	private long analysisItemId;
	/** 分析情報ID **/
	private String analysisInfoId;
	/** 分析区分項目CD **/
	private Optional<String> analysisItemCd;
	/** 分析区分項目名  **/
	private Optional<String> analysisItemName;

	public AnalysisItem(long analysisItemId, String analysisInfoId, Optional<String> analysisItemCd,
			Optional<String> analysisItemName) {
		super();
		this.analysisItemId = analysisItemId;
		this.analysisInfoId = analysisInfoId;
		this.analysisItemCd = analysisItemCd;
		this.analysisItemName = analysisItemName;
	}
	
}
