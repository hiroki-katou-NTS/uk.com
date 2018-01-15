package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class WorkTypeOutput {
	
	/** The work type code. */
	/* 勤務種類コード */
	private String workTypeCode;

	/** The name. */
	/* 勤務種類名称 */
	private String name;
}
