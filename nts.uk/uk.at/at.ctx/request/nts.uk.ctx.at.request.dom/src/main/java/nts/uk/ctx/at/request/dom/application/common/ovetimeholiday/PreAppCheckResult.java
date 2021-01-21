package nts.uk.ctx.at.request.dom.application.common.ovetimeholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.Application;

@AllArgsConstructor
@NoArgsConstructor
public class PreAppCheckResult {
	
	/**
	 * 事前申請：申請
	 */
	public Optional<Application> opAppBefore;
	
	/**
	 * 事前申請状態
	 */
	public boolean beforeAppStatus;
	
}
