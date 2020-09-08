package nts.uk.ctx.at.record.infra.repository.manageworkplaceagreedhours;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.manageworkplaceagreedhours.Workplace36AgreedHours;
import nts.uk.ctx.at.record.dom.manageworkplaceagreedhours.Workplace36AgreedHoursRepository;
import nts.uk.ctx.at.record.infra.entity.manageworkplaceagreedhours.Ksrmt36AgrMgtWkp;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * 職場３６協定時間Repository
 */
@Stateless
public class JpaWorkplace36AgreedHoursRepository extends JpaRepository implements Workplace36AgreedHoursRepository {
    private static String FIND_BY_WKP;

    private static String FIND_BY_LIST_WKP;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT");
        builderString.append("FROM Ksrmt36AgrMgtWkp a");
        builderString.append("WHERE a.ksrmt36AgrMgtWkpPk.companyID IN :listWorkplaceId ");
        FIND_BY_LIST_WKP = builderString.toString();

        builderString = new StringBuilder();
        builderString.append("SELECT");
        builderString.append("FROM Ksrmt36AgrMgtWkp a");
        builderString.append("WHERE a.ksrmt36AgrMgtWkpPk.companyID = :workplaceId ");
        FIND_BY_WKP = builderString.toString();
    }
    @Override
    public void insert(Workplace36AgreedHours domain) {

    }

    @Override
    public void update(Workplace36AgreedHours domain) {

    }

    @Override
    public void delete(Workplace36AgreedHours domain) {

    }

    @Override
    public List<Workplace36AgreedHours> getByListWorkplaceId(List<String> listWorkplaceId) {

        return this.queryProxy().query(FIND_BY_LIST_WKP, Ksrmt36AgrMgtWkp.class)
                .getList(d -> convertToDomain(d));
    }

    @Override
    public Optional<Workplace36AgreedHours> getByWorkplaceId(String workplaceId) {

        return this.queryProxy().query(FIND_BY_WKP, Ksrmt36AgrMgtWkp.class)
                .getSingle(d -> convertToDomain(d));
    }
    private Workplace36AgreedHours convertToDomain(Ksrmt36AgrMgtWkp entity) {
        return new Workplace36AgreedHours();
    }
}
