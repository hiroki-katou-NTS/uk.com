package nts.uk.ctx.at.record.infra.repository.workrecord.erroralarm.alarmlist.applicationapproval;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvConRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.alarmlistworkplace.applicationapproval.KrcmtWkpfxexAppapvCon;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaFixedExtractionAppapvConRepository extends JpaRepository implements FixedExtractionAppapvConRepository {

    private static final String SELECT_ALL_FXEX_APPAPV_CON = "SELECT a FROM KrcmtWkpfxexAppapvCon a ";

    private static final String SELECT_ALL_FXEX_APPAPV_CON_BY_ID = SELECT_ALL_FXEX_APPAPV_CON
            + " WHERE a.errorAlarmWorkplaceId = :chkId ";

    private static final String SELECT_BY_IDS = " where  a.errorAlarmWorkplaceId in :ids";

    private static final String SELECT_BY_IDS_AND_USEATR = SELECT_ALL_FXEX_APPAPV_CON
        + " WHERE a.errorAlarmWorkplaceId in :ids AND a.useAtr = :useAtr ";
    @Override
    public List<FixedExtractionAppapvCon> get(String errorAlarmWorkplaceId) {
        return this.queryProxy().query(SELECT_ALL_FXEX_APPAPV_CON_BY_ID, KrcmtWkpfxexAppapvCon.class)
                .setParameter("chkId", errorAlarmWorkplaceId)
                .getList(KrcmtWkpfxexAppapvCon::toDomain);
    }

    @Override
    public List<FixedExtractionAppapvCon> getByIds(List<String> ids) {
        return this.queryProxy().query(SELECT_BY_IDS, KrcmtWkpfxexAppapvCon.class)
                .setParameter("ids", ids)
                .getList(KrcmtWkpfxexAppapvCon::toDomain);
    }

    @Override
    public List<FixedExtractionAppapvCon> getBy(List<String> ids, boolean useAtr) {
        return this.queryProxy().query(SELECT_BY_IDS_AND_USEATR, KrcmtWkpfxexAppapvCon.class)
            .setParameter("ids", ids)
            .setParameter("useAtr", useAtr)
            .getList(KrcmtWkpfxexAppapvCon::toDomain);
    }
}
