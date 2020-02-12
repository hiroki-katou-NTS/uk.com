package nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.ReferEvaluationItem;

@Data
@AllArgsConstructor
public class ReferEvaluationItemDto {
	

	/** 評価項目 */
	private Integer evaluationItem;

	/** 参照する */
	private boolean usageFlg;

	/** 評価表示数 */
	private Integer displayNum;

	/** 判断基準値 */
	private String passValue;
	
	
	public ReferEvaluationItemDto(ReferEvaluationItem domain) {
		this.evaluationItem = domain.getEvaluationItem().value;
		this.usageFlg = domain.isUsageFlg();
		this.displayNum = domain.getDisplayNum();
		this.passValue = domain.getPassValue().isPresent() ? domain.getPassValue().get() : null;
	}
}
