package nts.uk.ctx.at.request.infra.repository.setting.request.businesstrip;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSet;
import nts.uk.ctx.at.request.dom.setting.request.application.businesstrip.AppTripRequestSetRepository;
import nts.uk.ctx.at.request.infra.entity.setting.request.businesstrip.KrqstAppTripRequestSet;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaAppTripRequestSetRepositoryImp extends JpaRepository implements AppTripRequestSetRepository {

    private static final String SELECT_ALL = "SELECT c FROM KrqstAppTripRequestSet c";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE c.companyId = :cid";

    @Override
    public Optional<AppTripRequestSet> findById(String cid) {
        Optional<AppTripRequestSet> appTripRequestSet = this.queryProxy().query(SELECT_BY_ID, KrqstAppTripRequestSet.class)
                .setParameter("cid", cid).getSingle(i -> i.toDomain());
        return appTripRequestSet;
    }

    @Override
    public void add(AppTripRequestSet domain) {

    }

    @Override
    public void update(AppTripRequestSet domain) {

    }

    @Override
    public void remove(AppTripRequestSet domain) {

    }

}
