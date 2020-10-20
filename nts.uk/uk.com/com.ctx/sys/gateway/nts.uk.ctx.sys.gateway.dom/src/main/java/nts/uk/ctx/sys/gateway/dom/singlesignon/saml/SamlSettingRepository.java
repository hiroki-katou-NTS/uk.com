package nts.uk.ctx.sys.gateway.dom.singlesignon.saml;

import java.util.Optional;

import nts.gul.security.saml.SamlSetting;

public interface SamlSettingRepository {
	
	Optional<SamlSetting> find(String tenantCode);

}
