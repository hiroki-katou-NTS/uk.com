package nts.uk.ctx.health.dom.linkage;

import java.util.Optional;

public interface HealthLifeApiLinkageRepository {

	Optional<HealthLifeApiLinkage> find(String tenantCode);
}
