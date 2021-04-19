package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.applicationapproval;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.CheckItemAppapv;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvItemsRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.applicationapproval.KrcmtWkpfxexAppapvItm;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaFixedExtractionAppapvItemsRepository extends JpaRepository implements FixedExtractionAppapvItemsRepository {

    private static final String SELECT;
    private static final String FIND_BY_NOS;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT a FROM KrcmtWkpfxexAppapvItm a ");
        SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE a.checkItemAppapv in :nos ");
        FIND_BY_NOS = builderString.toString();
    }

    @Override
    public List<FixedExtractionAppapvItems> getAll() {
        return this.queryProxy().query(SELECT, KrcmtWkpfxexAppapvItm.class)
                .getList(KrcmtWkpfxexAppapvItm::toDomain);
    }

    @Override
    public List<FixedExtractionAppapvItems> getBy(List<CheckItemAppapv> nos) {
        if (CollectionUtil.isEmpty(nos)) return new ArrayList<>();
        List<Integer> sidList = nos.stream().map(x -> x.value).collect(Collectors.toList());
        return this.queryProxy().query(FIND_BY_NOS, KrcmtWkpfxexAppapvItm.class)
            .setParameter("nos", sidList)
            .getList(KrcmtWkpfxexAppapvItm::toDomain);
    }
}
