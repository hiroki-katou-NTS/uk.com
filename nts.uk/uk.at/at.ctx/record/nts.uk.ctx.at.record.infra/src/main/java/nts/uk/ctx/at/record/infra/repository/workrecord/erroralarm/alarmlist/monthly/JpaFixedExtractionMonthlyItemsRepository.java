package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.monthly;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.FixedCheckMonthlyItemName;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.monthly.KrcmtWkpMonFxexItm;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaFixedExtractionMonthlyItemsRepository extends JpaRepository implements FixedExtractionMonthlyItemsRepository {

    private static final String SELECT;

    private static final String FIND_BY_NOS;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a FROM KrcmtWkpMonFxexItm a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.no in :nos ");
        FIND_BY_NOS = builderString.toString();

    }

    @Override
    public List<FixedExtractionMonthlyItems> getBy(List<FixedCheckMonthlyItemName> nos) {
        if (CollectionUtil.isEmpty(nos)) return new ArrayList<>();
        List<Integer> noList = nos.stream().map(x -> x.value).collect(Collectors.toList());
        return this.queryProxy().query(FIND_BY_NOS, KrcmtWkpMonFxexItm.class)
            .setParameter("nos", noList)
            .getList(KrcmtWkpMonFxexItm::toDomain);
    }

    @Override
    public List<FixedExtractionMonthlyItems> getAll() {
        return this.queryProxy().query(SELECT, KrcmtWkpMonFxexItm.class)
                .getList(KrcmtWkpMonFxexItm::toDomain);
    }
}
