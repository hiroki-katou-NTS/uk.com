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

    private static final String GET_ALL = "select f from KrcmtWkpBasicFxexItm f";

    @Override
    public Optional<BasicFixedExtractionItem> getByID(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<BasicFixedExtractionItem> getBy(BasicFixedCheckItem no) {
        return Optional.empty();
    }

    @Override
    public List<BasicFixedExtractionItem> getAll() {
        return this.queryProxy().query(GET_ALL, KrcmtWkpBasicFxexItm.class).getList(KrcmtWkpBasicFxexItm::toDomain);
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
