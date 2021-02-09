package nts.uk.ctx.sys.gateway.dom.outage.tenant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.sys.gateway.dom.outage.PlannedOutage;
import nts.uk.ctx.sys.gateway.dom.outage.PlannedOutageState;

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
