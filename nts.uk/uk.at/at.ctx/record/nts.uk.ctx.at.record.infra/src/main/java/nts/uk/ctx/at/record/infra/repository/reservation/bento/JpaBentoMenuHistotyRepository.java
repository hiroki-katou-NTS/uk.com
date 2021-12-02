package nts.uk.ctx.at.record.infra.repository.reservation.bento;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bento.IBentoMenuHistoryRepository;
import nts.uk.ctx.at.record.infra.entity.reservation.bentomenu.KrcmtBentoMenuHist;
import nts.uk.ctx.at.record.infra.entity.reservation.bentomenu.KrcmtBentoMenuHistPK;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
public class JpaBentoMenuHistotyRepository extends JpaRepository implements IBentoMenuHistoryRepository {
    private static final String QUERY_GET_BYCID = "SELECT  hist FROM  KrcmtBentoMenuHist hist "
            + "WHERE hist.pk.companyID = :cid ORDER BY hist.startDate ASC ";
    @Override

    public Optional<BentoMenuHistory> findByCompanyId(String cid) {
        List<KrcmtBentoMenuHist> listEntity = this.queryProxy().query(QUERY_GET_BYCID, KrcmtBentoMenuHist.class)
                .setParameter("cid", cid)
                .getList();
        if (!listEntity.isEmpty()) {
            List<DateHistoryItem> historyItems = new ArrayList<>();
            listEntity.forEach((item) -> {
                historyItems.add(new DateHistoryItem(item.pk.histID, new DatePeriod(item.startDate,item.endDate)));
            });
            BentoMenuHistory result = new BentoMenuHistory(cid,historyItems);
            return Optional.of( result);
        }
        return Optional.empty();
    }

    @Override
    public void add(BentoMenuHistory item) {

        this.commandProxy().insertAll(KrcmtBentoMenuHist.toEntity(item));
    }

    @Override
    public void update(BentoMenuHistory item) {
        List<KrcmtBentoMenuHist> updateList = new ArrayList<>();
        item.getHistoryItems().stream().forEach(i->{
            val old = this.queryProxy().find(new KrcmtBentoMenuHistPK(item.companyId,i.identifier()),KrcmtBentoMenuHist.class);
            if (old.isPresent()){
               updateList.add(old.get().update(i));
            }
        });
        this.commandProxy().updateAll(updateList);
    }

    @Override
    public void delete(String companyId, String historyId) {
         val entity = this.queryProxy().find(new KrcmtBentoMenuHistPK(companyId,historyId),KrcmtBentoMenuHist.class);
         if(entity.isPresent()){
             this.commandProxy().remove(KrcmtBentoMenuHist.class,new KrcmtBentoMenuHistPK(companyId,historyId));
             new NtsStatement("delete from KRCMT_BENTO where CID = @companyID and HIST_ID = @histID", this.jdbcProxy())
             .paramString("companyID", companyId)
             .paramString("histID", historyId)
             .execute();
         }
    }

}
