package nts.uk.screen.at.app.reservation;

import lombok.val;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bento.IBentoMenuHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

@Stateless
public class BentoMenuHistScreenQuery {

    @Inject
    private IBentoMenuHistoryRepository bentoMenuHistoryRepository;

    public BentoMenuHistDto getListBentoMenuHist() {
        val rs = new BentoMenuHistDto();
        val cid = AppContexts.user().companyId();
        val bento = bentoMenuHistoryRepository.findByCompanyId(cid);

        val dateItem = new ArrayList<DateHistoryItemDto>();
        if (bento.isPresent()) {

            bento.get().getHistoryItems().stream().forEach(e ->
                    dateItem.add(new DateHistoryItemDto(e.identifier(), e.start().toString(), e.end().toString()))
            );
            rs.setCompanyId(cid);
            rs.setHistoryItems(dateItem);
        }
        return rs;

    }

    public DateHistoryItemDto getBentoMenuHist(String hisId) {
        val cid = AppContexts.user().companyId();
        val bento = bentoMenuHistoryRepository.findByCompanyId(cid);
        val rs = new DateHistoryItemDto();
        if (bento.isPresent()) {
            val item = bento.get().items().stream().filter(e -> e.identifier().equals(hisId)).findFirst();

            if (item.isPresent()) {
                rs.historyId = item.get().identifier();
                rs.endDate = item.get().end().toString();
                rs.startDate = item.get().start().toString();
            }
        }
        return rs;
    }
}

