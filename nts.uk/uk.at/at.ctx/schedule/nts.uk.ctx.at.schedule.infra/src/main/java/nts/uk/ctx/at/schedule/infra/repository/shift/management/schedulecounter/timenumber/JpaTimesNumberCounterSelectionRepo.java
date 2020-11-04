package nts.uk.ctx.at.schedule.infra.repository.shift.management.schedulecounter.timenumber;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterSelection;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterSelectionRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting.TimesNumberCounterType;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.timenumber.KscmtTimeNumber;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.schedulecounter.wkpcounterlaborcostandtime.KscmtWkpLaborCostAndTime;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaTimesNumberCounterSelectionRepo extends JpaRepository implements TimesNumberCounterSelectionRepo {


    private static final String SELECT;

    private static final String FIND_BY_CID;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a ");
        builderString.append(" FROM KscmtTimeNumber a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.companyId = :companyId ");
        FIND_BY_CID = builderString.toString();
    }

    @Override
    public void insert(String companyId, TimesNumberCounterSelection domain) {
        commandProxy().insertAll(KscmtTimeNumber.toEntity(companyId,domain));
    }

    @Override
    public void update(String companyId, TimesNumberCounterSelection domain) {
        List<KscmtTimeNumber> result = this.queryProxy().query(FIND_BY_CID, KscmtTimeNumber.class)
            .setParameter("companyId", companyId)
            .getList();
        commandProxy().removeAll(result);
        commandProxy().insertAll(KscmtTimeNumber.toEntity(companyId,domain));
    }

    @Override
    public Optional<TimesNumberCounterSelection> get(String companyId, TimesNumberCounterType type) {
        List<KscmtTimeNumber> result = this.queryProxy().query(FIND_BY_CID, KscmtTimeNumber.class)
            .setParameter("companyId", companyId)
            .getList();
        return result.size() > 0 ? Optional.ofNullable(KscmtTimeNumber.toDomain(result)) : Optional.empty();
    }

    @Override
    public boolean exists(String companyId, TimesNumberCounterType type) {
        List<KscmtWkpLaborCostAndTime> result = this.queryProxy().query(FIND_BY_CID, KscmtWkpLaborCostAndTime.class)
            .setParameter("companyId", companyId)
            .getList();
        return result.size() > 0;
    }
}
