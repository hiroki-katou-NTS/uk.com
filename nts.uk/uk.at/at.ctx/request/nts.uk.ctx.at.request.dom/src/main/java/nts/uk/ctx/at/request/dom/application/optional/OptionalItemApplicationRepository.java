package nts.uk.ctx.at.request.dom.application.optional;

import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.Application;

public interface OptionalItemApplicationRepository {

	void save(OptionalItemApplication optItemApp);

	void update(OptionalItemApplication optItemApp, Application application);

	Optional<OptionalItemApplication> getByAppId(String companyId, String appId);

	void remove(OptionalItemApplication optItemApp);

}
