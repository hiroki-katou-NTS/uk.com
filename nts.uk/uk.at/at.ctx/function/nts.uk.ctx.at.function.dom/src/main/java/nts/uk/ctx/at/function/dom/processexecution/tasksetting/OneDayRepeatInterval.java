package nts.uk.ctx.at.function.dom.processexecution.tasksetting;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.enums.OneDayRepeatClassification;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue.OneDayRepeatIntervalDetail;

/**
 * 1日の繰り返し間隔
 */
@Getter
public class OneDayRepeatInterval extends DomainObject {
	/* 繰り返し間隔 */
	private Optional<OneDayRepeatIntervalDetail> detail;
	
	/* 指定区分 */
	private  OneDayRepeatClassification oneDayRepCls;

	public OneDayRepeatInterval(OneDayRepeatIntervalDetail detail, OneDayRepeatClassification oneDayRepCls) {
		this.detail = Optional.ofNullable(detail);
		this.oneDayRepCls = oneDayRepCls;
	}
	
}
