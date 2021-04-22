package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.workplace;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionItemRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.FixedCheckItem;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.workplace.KrcmtWkpFxexItm;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaAlarmFixedExtractionItemRepository extends JpaRepository implements AlarmFixedExtractionItemRepository {

    private static final String SELECT;

    private static final String FIND_BY_IDS_AND_USEATR;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a FROM KrcmtWkpFxexItm a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.no = :no ");
        FIND_BY_IDS_AND_USEATR = builderString.toString();

    }

    @Override
    public List<AlarmFixedExtractionItem> getAll() {
        return this.queryProxy().query(SELECT, KrcmtWkpFxexItm.class)
            .getList(KrcmtWkpFxexItm::toDomain);
    }

    @Override
    public Optional<AlarmFixedExtractionItem> getBy(FixedCheckItem no) {
        return this.queryProxy().query(FIND_BY_IDS_AND_USEATR, KrcmtWkpFxexItm.class)
            .setParameter("no", no.value)
            .getSingle(KrcmtWkpFxexItm::toDomain);
    }

}
