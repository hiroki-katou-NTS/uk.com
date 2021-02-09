package nts.uk.ctx.sys.gateway.dom.outage.tenant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.sys.gateway.dom.outage.PlannedOutage;
import nts.uk.ctx.sys.gateway.dom.outage.PlannedOutageState;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopMessage;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.StopModeType;
import nts.uk.ctx.sys.gateway.dom.stopbycompany.SystemStatusType;

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
