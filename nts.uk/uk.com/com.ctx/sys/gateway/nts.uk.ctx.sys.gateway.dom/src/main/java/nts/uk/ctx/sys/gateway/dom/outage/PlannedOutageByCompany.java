package nts.uk.ctx.sys.gateway.dom.outage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 会社単位の利用停止の設定
 */
@AllArgsConstructor
public class PlannedOutageByCompany implements PlannedOutage, DomainAggregate {

	@Getter
	private final String companyId;
	
	@Getter
	private PlannedOutageState state;
	
	public void setState(@NonNull PlannedOutageState newState) {
		state = newState;
	}
}
