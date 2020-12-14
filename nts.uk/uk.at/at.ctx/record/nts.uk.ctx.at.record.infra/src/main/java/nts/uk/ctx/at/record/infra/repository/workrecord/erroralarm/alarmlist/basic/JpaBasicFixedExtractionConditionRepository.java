package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.basic;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
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

    private static final String SELECT;

    private static final String GET_BY_IDS;

    private static final String FIND_BY_IDS_AND_USEATR;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a FROM KrcmtWkpBasicFxexCon a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.id in :ids ");
        GET_BY_IDS = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.pk.id in :ids AND a.useAtr = :useAtr ");
        FIND_BY_IDS_AND_USEATR = builderString.toString();
    }

    @Override
    public List<BasicFixedExtractionCondition> getByID(String id) {
        return null;
    }

    @Override
    public List<BasicFixedExtractionCondition> getByIDs(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) return new ArrayList<>();
        return this.queryProxy()
                .query(GET_BY_IDS, KrcmtWkpBasicFxexCon.class).setParameter("ids", ids)
                .getList(KrcmtWkpBasicFxexCon::toDomain);
    }

    @Override
    public List<BasicFixedExtractionCondition> getBy(List<String> ids, boolean useAtr) {
        if (CollectionUtil.isEmpty(ids)) return new ArrayList<>();
        return this.queryProxy().query(FIND_BY_IDS_AND_USEATR, KrcmtWkpBasicFxexCon.class)
            .setParameter("ids", ids)
            .setParameter("useAtr", useAtr)
            .getList(KrcmtWkpBasicFxexCon::toDomain);
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
