package nts.uk.ctx.at.request.dom.application.optional;

import nts.uk.ctx.at.request.dom.application.Application;

import java.util.Optional;

public interface OptionalItemApplicationRepository {

    void save(OptionalItemApplication optItemApp);

    void update(OptionalItemApplication optItemApp, Application application);

    Optional<OptionalItemApplication> getByAppId(String companyId, String appId);
}
