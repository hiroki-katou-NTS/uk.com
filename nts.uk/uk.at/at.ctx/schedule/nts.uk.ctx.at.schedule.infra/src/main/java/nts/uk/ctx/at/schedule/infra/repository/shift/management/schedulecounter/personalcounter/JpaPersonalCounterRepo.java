package nts.uk.ctx.at.schedule.infra.repository.shift.management.schedulecounter.personalcounter;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounter;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounterRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.WorkplaceCounterCategory;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.personalcounter.KscmtTallyByPerson;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaPersonalCounterRepo extends JpaRepository implements PersonalCounterRepo {

    @Inject
    I18NResourcesForUK ukResource;

    private static final String SELECT;

    private static final String FIND_BY_CID;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a ");
        builderString.append(" FROM KscmtTallyByPerson a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.companyId = :companyId ");
        FIND_BY_CID = builderString.toString();
    }

    @Override
    public void insert(String companyId, PersonalCounter domain) {
        List<EnumConstant> listEnum = EnumAdaptor.convertToValueNameList(WorkplaceCounterCategory.class, ukResource);
        commandProxy().insertAll(KscmtTallyByPerson.toEntity(companyId,domain,listEnum));
    }

    @Override
    public void update(String companyId, PersonalCounter domain) {
        List<KscmtTallyByPerson> result = this.queryProxy().query(FIND_BY_CID, KscmtTallyByPerson.class)
            .setParameter("companyId", companyId)
            .getList();
        commandProxy().removeAll(result);
        this.getEntityManager().flush();
        List<EnumConstant> listEnum = EnumAdaptor.convertToValueNameList(WorkplaceCounterCategory.class, ukResource);
        commandProxy().insertAll(KscmtTallyByPerson.toEntity(companyId,domain,listEnum));
    }

    @Override
    public Optional<PersonalCounter> get(String companyId) {
        List<KscmtTallyByPerson> result = this.queryProxy().query(FIND_BY_CID, KscmtTallyByPerson.class)
            .setParameter("companyId", companyId)
            .getList();
        return result.size() > 0 ? Optional.of(KscmtTallyByPerson.toDomain(result)) : Optional.empty();
    }

    @Override
    public boolean exists(String companyId) {
        List<KscmtTallyByPerson> result = this.queryProxy().query(FIND_BY_CID, KscmtTallyByPerson.class)
            .setParameter("companyId", companyId)
            .getList();
        return result.size() > 0;
    }
}
