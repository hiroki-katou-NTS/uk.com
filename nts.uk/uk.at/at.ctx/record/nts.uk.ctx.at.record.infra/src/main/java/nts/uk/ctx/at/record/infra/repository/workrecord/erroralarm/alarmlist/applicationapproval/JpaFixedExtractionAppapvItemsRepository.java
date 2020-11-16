package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.applicationapproval;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvItemsRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.applicationapproval.KrcmtWkpfxexAppapvItm;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaFixedExtractionAppapvItemsRepository extends JpaRepository implements FixedExtractionAppapvItemsRepository {

    private static final String SELECT_ALL_FXEX_APPAPV_ITM = "SELECT a FROM KrcmtWkpfxexAppapvItm a ";

    @Override
    public List<FixedExtractionAppapvItems> getAll() {
        return this.queryProxy().query(SELECT_ALL_FXEX_APPAPV_ITM, KrcmtWkpfxexAppapvItm.class)
                .getList(KrcmtWkpfxexAppapvItm::toDomain);
    }
}
