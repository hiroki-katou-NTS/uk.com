package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ActualStatusCheckResult {
	
	/**
	 * 実績状態
	 */
	public ActualStatus actualStatus;
	
	/**
	 * 実績
	 */
	public List<OvertimeColorCheck> actualLst;
}
