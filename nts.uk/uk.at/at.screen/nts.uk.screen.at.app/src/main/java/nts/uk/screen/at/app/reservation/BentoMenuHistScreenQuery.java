package nts.uk.screen.at.app.reservation;

import lombok.val;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bento.IBentoMenuHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class BentoMenuHistScreenQuery {

    @Inject
    IBentoMenuHistoryRepository bentoMenuHistoryRepository;

    public Optional<BentoMenuHistory> getListBentoMenuHist(){
        val cid = AppContexts.user().companyId();
        val rs = bentoMenuHistoryRepository.findByCompanyId(cid);
        if(!rs.isPresent()){
            return Optional.empty();
        }
        return rs;
    }
    public Optional<DateHistoryItem> getBentoMenuHist(String hisId){
        val cid = AppContexts.user().companyId();
        val listItem = bentoMenuHistoryRepository.findByCompanyId(cid);
        val rs = listItem.get().items().stream().filter(e->e.identifier().equals(hisId)).findFirst();
        if(!rs.isPresent()){
            return Optional.empty();
        }
        return rs;
    }
}

