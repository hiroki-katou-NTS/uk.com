package nts.uk.ctx.health.dom.federate;

import java.util.Optional;

public interface HealthLifeApiFederationRepository {

	Optional<HealthLifeApiFederation> find(String tenantCode);
}
