package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.ReferEvaluationItem;

@NoArgsConstructor
@Getter
@Setter
public class ReferEvaluationItemDto {

	private Integer evaluationItem;
	
	private Boolean usageFlg;
	
	private Integer displayNum;
	
	private String passValue;
	
	public ReferEvaluationItem toDomain() {
		return ReferEvaluationItem.createFromJavaType(this.evaluationItem, this.usageFlg, this.displayNum, this.passValue);
	}

	public ReferEvaluationItemDto(ReferEvaluationItem domain) {
		super();
		this.evaluationItem = domain.getEvaluationItem().value;
		this.usageFlg = domain.isUsageFlg();
		this.displayNum = domain.getDisplayNum();
		this.passValue = domain.getPassValue().orElse(null);
	}
	
}
