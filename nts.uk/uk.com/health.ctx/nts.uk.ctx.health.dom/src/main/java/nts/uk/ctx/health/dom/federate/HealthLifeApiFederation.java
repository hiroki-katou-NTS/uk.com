package nts.uk.ctx.health.dom.federate;

import java.net.URI;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * ヘルスライフAPI連携
 */
@RequiredArgsConstructor
public class HealthLifeApiFederation implements DomainAggregate {

	@Getter
	private final String tenantCode;

	@Getter
	private String rootUrl;

	@Getter
	private String targetContractCode;

	@Getter
	private String password;
	
	public URI getUriOf(String path) {
		return URI.create(rootUrl).resolve(path);
	}
}
