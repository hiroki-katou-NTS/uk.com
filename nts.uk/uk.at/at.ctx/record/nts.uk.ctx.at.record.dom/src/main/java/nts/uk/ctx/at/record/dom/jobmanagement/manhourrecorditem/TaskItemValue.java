package nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author tutt
 *
 */
@Getter
@AllArgsConstructor
public class TaskItemValue {
	
	/** 値*/
	private String value;
	
	/** 工数実績項目ID*/
	private int itemId;
}
