package nts.uk.ctx.sys.gateway.dom.outage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * テナント単位の利用停止の設定
 */
@AllArgsConstructor
public class PlannedOutageByTenant implements PlannedOutage, DomainAggregate {

	@Getter
	private final String tenantCode;
	
	@Getter
	private PlannedOutageState state;
	
	public void setState(@NonNull PlannedOutageState newState) {
		state = newState;
	}
}
