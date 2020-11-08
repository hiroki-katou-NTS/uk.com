package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.applicationapproval;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.applicationapproval.FixedExtractionAppapvCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.applicationapproval.FixedExtractionAppapvConRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlist.applicationapproval.KrcmtWkpfxexAppapvCon;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaFixedExtractionAppapvConRepository extends JpaRepository implements FixedExtractionAppapvConRepository {

    private static final String SELECT_ALL_FXEX_APPAPV_CON = "SELECT a FROM KrcmtWkpfxexAppapvCon a ";

    private static final String SELECT_ALL_FXEX_APPAPV_CON_BY_ID = SELECT_ALL_FXEX_APPAPV_CON
            + " WHERE a.pk.errorAlarmWorkplaceId = :chkId ";

    @Override
    public List<FixedExtractionAppapvCon> get(String errorAlarmWorkplaceId) {
        return this.queryProxy().query(SELECT_ALL_FXEX_APPAPV_CON_BY_ID, KrcmtWkpfxexAppapvCon.class)
                .setParameter("chkId", errorAlarmWorkplaceId)
                .getList(KrcmtWkpfxexAppapvCon::toDomain);
    }
}
