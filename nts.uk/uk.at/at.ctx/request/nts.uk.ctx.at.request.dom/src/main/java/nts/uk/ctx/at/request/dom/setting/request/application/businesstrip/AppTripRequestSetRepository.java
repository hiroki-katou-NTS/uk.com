package nts.uk.ctx.at.request.dom.setting.request.application.businesstrip;

import java.util.Optional;

public interface AppTripRequestSetRepository {

    Optional<AppTripRequestSet> findById(String cid);

    void add(AppTripRequestSet domain);

    void update(AppTripRequestSet domain);

    void remove(AppTripRequestSet domain);

}
