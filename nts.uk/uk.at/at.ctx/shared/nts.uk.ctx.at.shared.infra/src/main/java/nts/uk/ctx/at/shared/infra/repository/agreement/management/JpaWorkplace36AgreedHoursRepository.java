package nts.uk.ctx.at.shared.infra.repository.agreement.management;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Workplace36AgreedHoursRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.infra.entity.agreement.management.Ksrmt36AgrMgtWkp;
import nts.uk.ctx.at.shared.infra.entity.agreement.management.Ksrmt36AgrMgtWkpPk;

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

    private static String FIND_WORKPLACE_SETTING;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a ");
        builderString.append(" FROM Ksrmt36AgrMgtWkp a ");
        builderString.append(" WHERE a.ksrmt36AgrMgtWkpPk.workplaceId IN :listWorkplaceId ");
        FIND_BY_LIST_WKP = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" SELECT a");
        builderString.append(" FROM Ksrmt36AgrMgtWkp a");
        builderString.append(" WHERE a.ksrmt36AgrMgtWkpPk.workplaceId = :workplaceId ");
        builderString.append(" AND a.ksrmt36AgrMgtWkpPk.laborSystemAtr = :laborSystemAtr ");
        FIND_BY_WKP = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(" SELECT a ");
        builderString.append(" FROM Ksrmt36AgrMgtWkp a ");
        builderString.append(" AND a.ksrmt36AgrMgtWkpPk.laborSystemAtr = :laborSystemAtr ");
        FIND_WORKPLACE_SETTING = builderString.toString();
    }

    @Override
    public void insert(AgreementTimeOfWorkPlace domain) {

        this.commandProxy().insert(Ksrmt36AgrMgtWkp.toEntity(domain));
    }

    @Override
    public void update(AgreementTimeOfWorkPlace domain) {
        this.commandProxy().update(Ksrmt36AgrMgtWkp.toEntity(domain));
    }

    @Override
    public void delete(AgreementTimeOfWorkPlace domain) {
        val entity = this.queryProxy().find(new Ksrmt36AgrMgtWkpPk(domain.getWorkplaceId()
                , domain.getLaborSystemAtr().value), Ksrmt36AgrMgtWkp.class);
        if (entity.isPresent()) {
            this.commandProxy().remove(Ksrmt36AgrMgtWkp.class, new Ksrmt36AgrMgtWkpPk(domain.getWorkplaceId()
                    , domain.getLaborSystemAtr().value));
        }
    }

    @Override
    public List<AgreementTimeOfWorkPlace> getByListWorkplaceId(List<String> listWorkplaceId) {
        return this.queryProxy().query(FIND_BY_LIST_WKP, Ksrmt36AgrMgtWkp.class)
                .setParameter("listWorkplaceId", listWorkplaceId)
                .getList(Ksrmt36AgrMgtWkp::toDomain);
    }

    @Override
    public List<String> findWorkPlaceSetting(LaborSystemtAtr laborSystemAtr) {
        return this.queryProxy().query(FIND_WORKPLACE_SETTING, Ksrmt36AgrMgtWkp.class)
                .setParameter("laborSystemAtr", laborSystemAtr.value)
                .getList(f -> f.ksrmt36AgrMgtWkpPk.workplaceId);
    }

    @Override
    public Optional<AgreementTimeOfWorkPlace> getByWorkplaceId(String workplaceId,LaborSystemtAtr laborSystemAtr) {

        return this.queryProxy().query(FIND_BY_WKP, Ksrmt36AgrMgtWkp.class)
                .setParameter("workplaceId", workplaceId)
                .setParameter("laborSystemAtr", laborSystemAtr.value)
                .getSingle(Ksrmt36AgrMgtWkp::toDomain);
    }
}
