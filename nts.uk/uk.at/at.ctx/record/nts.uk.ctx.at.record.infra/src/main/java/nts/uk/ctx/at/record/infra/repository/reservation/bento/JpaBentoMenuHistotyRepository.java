package nts.uk.ctx.at.record.infra.repository.reservation.bento;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bento.IBentoMenuHistoryRepository;
import nts.uk.ctx.at.record.infra.entity.reservation.bentomenu.KrcmtBentoMenuHist;
import nts.uk.ctx.at.record.infra.entity.reservation.bentomenu.KrcmtBentoMenuHistPK;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaBentoMenuHistotyRepository extends JpaRepository implements IBentoMenuHistoryRepository {
    private static final String QUERY_GET_BYCID = "SELECT  hist FROM  KrcmtBentoMenuHist hist "
            + "WHERE hist.pk.companyID = :cid ";
    private static final  String QUERY_GET_BYCID_AND_HISTID =  "DELETE  hist FROM  KrcmtBentoMenuHist hist "
            + "WHERE hist.pk.companyID = :cid AND hist.pk.histID = histID";

    @Override

    public Optional<BentoMenuHistory> findByCompanyId(String contractCD) {
        val cid = AppContexts.user().companyId();
        List<BentoMenuHistory> listEntity = this.queryProxy().query(QUERY_GET_BYCID, BentoMenuHistory.class)
                .setParameter("cid", cid)
                .getList();
        if (!listEntity.isEmpty()) {
            return Optional.of((BentoMenuHistory) listEntity);
        }
        return Optional.empty();
    }

    @Override
    public void add(BentoMenuHistory item) {

        this.commandProxy().insertAll(KrcmtBentoMenuHist.toEntity(item));
    }

    @Override
    public void update(BentoMenuHistory item) {

        this.commandProxy().updateAll(KrcmtBentoMenuHist.toEntity(item));
    }

    @Override
    public void delete(String companyId, String historyId) {

      val item =   this.queryProxy().find(new KrcmtBentoMenuHistPK(companyId,historyId),KrcmtBentoMenuHist.class);
        if(!item.isPresent()){
            throw new BusinessException("invalid BentoMenuHistory!");

        }else {
            this.commandProxy().remove(item);
        }

    }
}
