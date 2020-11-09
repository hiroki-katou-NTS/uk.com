package nts.uk.shr.infra.data;

import lombok.val;
import nts.arc.scoped.session.SessionContextProvider;
import nts.tenantlocator.client.TenantLocatorClient;

public class TenantLocatorService {
	public static void connect(String tenantCode) {
		val datasource = TenantLocatorClient.getDataSource(tenantCode);
		if(datasource.isPresent()) {
			SessionContextProvider.get().put("datasource", datasource.get().getDatasourceName());
		}
		else {
			SessionContextProvider.get().put("datasource", "");
		}
	}
	
	public static void disconnect() {
		SessionContextProvider.get().put("datasource", "");
	}
}
