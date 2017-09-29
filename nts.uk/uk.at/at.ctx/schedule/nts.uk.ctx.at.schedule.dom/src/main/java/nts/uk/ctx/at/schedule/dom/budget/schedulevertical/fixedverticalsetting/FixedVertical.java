package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@Getter
public class FixedVertical extends AggregateRoot {

	/* 会社ID */
	private String companyId;
	
	/*利用区分*/
	private UseAtr useClassification;
	
	/*固定項目区分*/
	private FixedItemAtr fixedItemClassification;
	
	/*縦計詳細設定*/
	private VerticalDetailedSettings verticalDetailedSettings;
}
