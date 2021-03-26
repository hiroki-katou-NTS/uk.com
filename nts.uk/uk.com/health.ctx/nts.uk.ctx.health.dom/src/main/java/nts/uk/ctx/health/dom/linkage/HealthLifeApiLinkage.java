package nts.uk.ctx.health.dom.linkage;

import java.net.URI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * ヘルスライフAPI連携
 */
@AllArgsConstructor
public class HealthLifeApiLinkage implements DomainAggregate {

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
