package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * @author Anh.Bd
 *
 */
@NoArgsConstructor
@Getter
@Setter
public class AppCompltSyncOutput {
	// don xin nghi
	private String absId;
	// don lam bu
	private String recId;
	// 0 - xin nghi
	// 1 - lam bu
	private int type;
}
