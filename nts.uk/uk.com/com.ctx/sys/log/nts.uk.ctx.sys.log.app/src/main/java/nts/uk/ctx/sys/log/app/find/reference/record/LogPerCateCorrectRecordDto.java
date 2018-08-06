package nts.uk.ctx.sys.log.app.find.reference.record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author Thuongtv
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogPerCateCorrectRecordDto {
	private String operationId;
	private String categoryName; //23
	private GeneralDate targetDate; //25/26/27/28
	private String itemName;//29
	private String valueBefore;//31
	private String valueAfter;//33
	private String infoOperateAttr;//24
}
