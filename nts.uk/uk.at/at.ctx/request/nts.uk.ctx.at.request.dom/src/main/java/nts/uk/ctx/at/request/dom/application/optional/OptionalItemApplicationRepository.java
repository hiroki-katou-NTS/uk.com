package nts.uk.ctx.at.request.dom.application.optional;

import java.util.Optional;

public interface OptionalItemApplicationRepository {

    void save(OptionalItemApplication optItemApp);

    void update(OptionalItemApplication optItemApp);

    Optional<OptionalItemApplication> getByAppId(String companyId, String appId);
}
