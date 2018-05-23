package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 
 * @author Anh.bd
 *
 */

@AllArgsConstructor
@Value
public class WorkplaceInfor {
	// 職場ID
    String code;
    
    String name;
}
