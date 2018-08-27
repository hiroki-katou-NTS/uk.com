package nts.uk.ctx.sys.log.app.find.reference.record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private String parentKey;
	private String childrentKey;
	private String operationId;
	private String categoryName; //23
	private String targetDate; //25/26/27/28
	private String itemName;//29
	private String valueBefore;//31
	private String valueAfter;//33
	private String infoOperateAttr;//24
}
