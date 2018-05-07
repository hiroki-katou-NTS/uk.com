/**
 * 
 */
package nts.uk.ctx.at.record.app.find.workrecord.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danpv
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationSettingDto {
	
	private String cid;
	
	private Integer settingUnit;
	
	private String comment;

}
