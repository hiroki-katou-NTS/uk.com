package nts.uk.shr.com.system.config.internal;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.shr.com.system.config.SystemConfigurationRepository;
import nts.uk.shr.com.system.config.SystemConfigurationValue;

@Stateless
public class JpaSystemConfigurationRepository extends JpaRepository
		implements SystemConfigurationRepository {

	@Override
	public SystemConfigurationValue get(String key) {
		return this.forDefaultDataSource(em -> {
			return this.queryProxy(em).find(key, CisctSystemConfig.class)
					.map(e -> new SystemConfigurationValue(e.value))
					.orElse(SystemConfigurationValue.none());
		});
	}
}
