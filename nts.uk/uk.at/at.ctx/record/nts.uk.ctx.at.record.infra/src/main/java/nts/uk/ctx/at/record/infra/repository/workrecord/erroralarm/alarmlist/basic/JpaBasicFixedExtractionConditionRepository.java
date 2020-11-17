package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.basic;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedExtractionConditionRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.basic.KrcmtWkpBasicFxexCon;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaBasicFixedExtractionConditionRepository extends JpaRepository implements BasicFixedExtractionConditionRepository {

    private static final String GET_ALL = "select f from KrcmtWkpBasicFxexCon f";

    private static final String GET_BY_IDS = GET_ALL + " where f.pk.id in :ids";

    @Override
    public List<BasicFixedExtractionCondition> getByID(String id) {
        return null;
    }

    @Override
    public List<BasicFixedExtractionCondition> getByIDs(List<String> ids) {
        return this.queryProxy()
                .query(GET_BY_IDS, KrcmtWkpBasicFxexCon.class).setParameter("ids", ids)
                .getList(KrcmtWkpBasicFxexCon::toDomain);
    }

    @Override
    public List<BasicFixedExtractionCondition> getBy(List<String> ids, boolean useAtr) {
        return new ArrayList<>();
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
