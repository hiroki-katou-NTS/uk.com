package nts.uk.ctx.at.function.dom.processexecution.tasksetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.OneDayRepeatClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.OneDayRepeatIntervalDetail;

/**
 * 1日の繰り返し間隔
 */
@Getter
@AllArgsConstructor
public class OneDayRepeatInterval extends DomainObject {
	/* 1日の繰り返し間隔ありなし区分 */
	private OneDayRepeatIntervalDetail detail;
	
	/* 繰り返し間隔 */
	private OneDayRepeatClassification oneDayRepCls;
}
