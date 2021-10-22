package nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author tutt
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TaskItemValue {
	
	/** 値*/
	private String value;
	
	/** 工数実績項目ID*/
	private int itemId;
}
