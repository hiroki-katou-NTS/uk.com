package nts.uk.ctx.at.function.infra.repository.alarm.workplace.checkcondition;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.alarm.workplace.checkcondition.AlarmCheckCdtWkpCtgRepository;
import nts.uk.ctx.at.function.dom.alarm.workplace.checkcondition.AlarmCheckCdtWorkplaceCategory;
import nts.uk.ctx.at.function.infra.entity.alarm.workplace.checkcondition.KfnmtWkpAlstchkConcat;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaAlarmWkpAlstchkConcat extends JpaRepository implements AlarmCheckCdtWkpCtgRepository {

    private static final String GET_ALL = "select f from KfnmtWkpAlstchkConcat f";
    private static final String GET_BY_ID = GET_ALL + " where f.id = :id";
    private static final String GET_BY_CATEGORY_ID = GET_ALL + " where f.category = :categoryID and f.cid = :cid";

    @Override
    public Optional<AlarmCheckCdtWorkplaceCategory> getByID(String id) {
        Optional<KfnmtWkpAlstchkConcat> entity = this.queryProxy().query(GET_BY_ID, KfnmtWkpAlstchkConcat.class).setParameter("id", id).getSingle();
        return entity.map(KfnmtWkpAlstchkConcat::toDomain);
    }

    @Override
    public Optional<AlarmCheckCdtWorkplaceCategory> getByCategoryID(int categoryID) {
        Optional<KfnmtWkpAlstchkConcat> entity = this.queryProxy().query(GET_BY_CATEGORY_ID, KfnmtWkpAlstchkConcat.class)
                .setParameter("categoryID", categoryID)
                .setParameter("cid", AppContexts.user().companyId())
                .getSingle();
        return entity.map(KfnmtWkpAlstchkConcat::toDomain);
    }

    @Override
    public void register(AlarmCheckCdtWorkplaceCategory domain) {
        this.commandProxy().insert(KfnmtWkpAlstchkConcat.toEntity(domain));
    }

    @Override
    public void update(AlarmCheckCdtWorkplaceCategory domain) {

    }

    @Override
    public void delete(String id) {
        this.commandProxy().remove(KfnmtWkpAlstchkConcat.class, id);
    }
}
