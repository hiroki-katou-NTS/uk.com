package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.daily;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.daily.FixedCheckDayItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.daily.FixedExtractionDayItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.daily.FixedExtractionDayItemsRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.daily.KrcmtWkpFxexDayItm;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaFixedExtractionDayItemsRepository extends JpaRepository implements FixedExtractionDayItemsRepository {

    private static final String SELECT_ALL_FXEX_DAY_ITM = "SELECT a FROM KrcmtWkpFxexDayItm a ";

    private static final String SELECT_RANGE_FXEX_DAY_ITM_BY_NO = SELECT_ALL_FXEX_DAY_ITM
            + " WHERE a.fixedCheckDayItems IN :no ";

    @Override
    public List<FixedExtractionDayItems> get(List<FixedCheckDayItems> fixedCheckDayItems) {
        if (fixedCheckDayItems.isEmpty()) {
            return new ArrayList<>();
        }

        return this.queryProxy().query(SELECT_RANGE_FXEX_DAY_ITM_BY_NO, KrcmtWkpFxexDayItm.class)
                .setParameter("no", fixedCheckDayItems.stream().map(i -> {
                    return i.value;
                }).collect(Collectors.toList()))
                .getList(KrcmtWkpFxexDayItm::toDomain);
    }
}
