package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.basic;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedCheckItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedExtractionItem;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedExtractionItemRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.basic.KrcmtWkpBasicFxexItm;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
import java.util.Optional;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaBasicFixedExtractionItemRepository extends JpaRepository implements BasicFixedExtractionItemRepository {

    private static final String SELECT;

    private static final String FIND_BY_NO;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a FROM KrcmtWkpBasicFxexItm a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.no = :no ");
        FIND_BY_NO = builderString.toString();
    }

    @Override
    public Optional<BasicFixedExtractionItem> getByID(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<BasicFixedExtractionItem> getBy(BasicFixedCheckItem no) {
        return this.queryProxy().query(FIND_BY_NO, KrcmtWkpBasicFxexItm.class)
            .setParameter("no", no.value)
            .getSingle(KrcmtWkpBasicFxexItm::toDomain);
    }

    @Override
    public List<BasicFixedExtractionItem> getAll() {
        return this.queryProxy().query(SELECT, KrcmtWkpBasicFxexItm.class).getList(KrcmtWkpBasicFxexItm::toDomain);
    }

    @Override
    public void register(BasicFixedExtractionCondition domain) {

    }

    @Override
    public void update(BasicFixedExtractionCondition domain) {

    }

    @Override
    public void delete(String id) {

    }
}
