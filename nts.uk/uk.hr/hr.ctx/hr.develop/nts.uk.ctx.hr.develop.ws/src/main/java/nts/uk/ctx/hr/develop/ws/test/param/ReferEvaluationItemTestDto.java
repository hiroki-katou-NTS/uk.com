package nts.uk.ctx.hr.develop.ws.test.param;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.ReferEvaluationItem;

@NoArgsConstructor
@Getter
@Setter
public class ReferEvaluationItemTestDto {

	private Integer evaluationItem;
	
	private Boolean usageFlg;
	
	private Integer displayNum;
	
	private String passValue;
	
	public ReferEvaluationItem toDomain() {
		return ReferEvaluationItem.createFromJavaType(this.evaluationItem, this.usageFlg, this.displayNum, this.passValue);
	}

	public ReferEvaluationItemTestDto(ReferEvaluationItem domain) {
		super();
		this.evaluationItem = domain.getEvaluationItem().value;
		this.usageFlg = domain.isUsageFlg();
		this.displayNum = domain.getDisplayNum();
		this.passValue = domain.getPassValue().orElse(null);
	}
	
}
